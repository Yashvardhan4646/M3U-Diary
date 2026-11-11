package com.example.drdiary.data

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.CancellationSignal
import androidx.core.hardware.fingerprint.FingerprintManagerCompat

object BiometricHelper {
    fun isBiometricAvailable(context: Context): Boolean {
        return try {
            val compat = FingerprintManagerCompat.from(context)
            compat.isHardwareDetected && compat.hasEnrolledFingerprints()
        } catch (_: Exception) {
            false
        }
    }

    fun promptBiometric(
        activity: Activity,
        title: String = "Unlock Diary",
        subtitle: String = "Verify your fingerprint or biometric ID to proceed",
        onSuccess: () -> Unit,
        onError: (String) -> Unit = {}
    ): CancellationSignal? {
        val cancellationSignal = CancellationSignal()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            try {
                val executor = activity.mainExecutor
                val prompt = android.hardware.biometrics.BiometricPrompt.Builder(activity)
                    .setTitle(title)
                    .setSubtitle(subtitle)
                    .setNegativeButton("Use PIN", executor) { _, _ ->
                        onError("Use PIN")
                    }
                    .build()

                prompt.authenticate(
                    cancellationSignal,
                    executor,
                    object : android.hardware.biometrics.BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: android.hardware.biometrics.BiometricPrompt.AuthenticationResult?) {
                            super.onAuthenticationSucceeded(result)
                            onSuccess()
                        }

                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                            super.onAuthenticationError(errorCode, errString)
                            onError(errString?.toString() ?: "Authentication cancelled")
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            onError("Biometric not recognized")
                        }
                    }
                )
                return cancellationSignal
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        // Fallback for API 24-27
        try {
            val compat = FingerprintManagerCompat.from(activity)
            if (compat.isHardwareDetected && compat.hasEnrolledFingerprints()) {
                val compatCancellationSignal = androidx.core.os.CancellationSignal()
                compat.authenticate(
                    null,
                    0,
                    compatCancellationSignal,
                    object : FingerprintManagerCompat.AuthenticationCallback() {
                        override fun onAuthenticationSucceeded(result: FingerprintManagerCompat.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            onSuccess()
                        }

                        override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
                            super.onAuthenticationError(errMsgId, errString)
                            onError(errString.toString())
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            onError("Fingerprint not recognized")
                        }
                    },
                    null
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return cancellationSignal
    }
}
