package com.example.drdiary.data

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import java.io.File
import java.util.Locale

object AudioNoteManager {
    fun formatDuration(durationMs: Long): String {
        val totalSeconds = (durationMs / 1000).coerceAtLeast(0)
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
    }

    fun createAudioFile(context: Context): File {
        val audioDir = File(context.filesDir, "entry_audio").apply { mkdirs() }
        return File(audioDir, "audio_${System.currentTimeMillis()}.m4a")
    }

    @Suppress("DEPRECATION")
    fun startRecording(context: Context, outputFile: File): MediaRecorder {
        val recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }

        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(128000)
            setAudioSamplingRate(44100)
            setOutputFile(outputFile.absolutePath)
            prepare()
            start()
        }
        return recorder
    }

    fun stopRecording(recorder: MediaRecorder?): Boolean {
        return try {
            recorder?.apply {
                stop()
                reset()
                release()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                recorder?.release()
            } catch (_: Exception) {}
            false
        }
    }

    fun createMediaPlayer(
        filePath: String,
        onPrepared: (durationMs: Long) -> Unit = {},
        onCompletion: () -> Unit = {},
        onError: (String) -> Unit = {}
    ): MediaPlayer? {
        val file = File(filePath)
        if (!file.exists()) {
            onError("Audio file does not exist")
            return null
        }

        return try {
            val player = MediaPlayer()
            player.setDataSource(filePath)
            player.setOnPreparedListener { mp ->
                onPrepared(mp.duration.toLong())
            }
            player.setOnCompletionListener {
                onCompletion()
            }
            player.setOnErrorListener { _, what, extra ->
                onError("Playback error: $what, $extra")
                true
            }
            player.prepare()
            player
        } catch (e: Exception) {
            e.printStackTrace()
            onError(e.localizedMessage ?: "Failed to play audio")
            null
        }
    }
}
