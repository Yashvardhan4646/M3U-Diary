package com.example.drdiary.ui.editor

import android.Manifest
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccessTime
import androidx.compose.material.icons.rounded.AddPhotoAlternate
import androidx.compose.material.icons.rounded.Archive
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.GraphicEq
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Inbox
import androidx.compose.material.icons.rounded.Mic
import androidx.compose.material.icons.rounded.Mood
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PhotoCamera
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.drdiary.data.AudioNoteManager
import com.example.drdiary.data.DiaryEntry
import com.example.drdiary.data.DiaryRepository
import com.example.drdiary.data.EntryIcon
import com.example.drdiary.data.Mood
import com.example.drdiary.ui.components.pressScale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryEditorScreen(
    entryId: String?,
    repository: DiaryRepository,
    initialDateMillis: Long? = null,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit,
    onNavigateMood: () -> Unit,
    onNavigateCalendar: () -> Unit,
    onNavigateSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var currentEntryId by remember { mutableStateOf(entryId) }
    var isViewingMode by remember { mutableStateOf(entryId != null) }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedMood by remember { mutableStateOf(Mood.REFLECTIVE) }
    var selectedIcon by remember { mutableStateOf(EntryIcon.INBOX) }
    var isFavorite by remember { mutableStateOf(false) }

    val currentTime = remember {
        SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    }
    var selectedDateMillis by remember { mutableLongStateOf(initialDateMillis ?: System.currentTimeMillis()) }
    var selectedTimeFormatted by remember { mutableStateOf(currentTime) }

    val attachedImages = remember { mutableStateListOf<String>() }
    var attachedAudioUri by remember { mutableStateOf<String?>(null) }
    var attachedAudioDurationMs by remember { mutableLongStateOf(0L) }

    var titleError by remember { mutableStateOf<String?>(null) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }
    var showOptionsMenu by remember { mutableStateOf(false) }
    var showDeleteConfirmation by remember { mutableStateOf(false) }
    var previewImageDialogPath by remember { mutableStateOf<String?>(null) }

    // Audio recording state
    var isRecordingAudio by remember { mutableStateOf(false) }
    var recordingElapsedMs by remember { mutableLongStateOf(0L) }
    var activeRecorder by remember { mutableStateOf<MediaRecorder?>(null) }
    var activeRecordingFile by remember { mutableStateOf<File?>(null) }

    // Audio player state
    var isAudioPlaying by remember { mutableStateOf(false) }
    var currentAudioProgressMs by remember { mutableLongStateOf(0L) }
    var activeMediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    // Temporary camera capture Uri & File
    var tempCameraImageUri by remember { mutableStateOf<Uri?>(null) }
    var tempCameraFile by remember { mutableStateOf<File?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraFile != null && tempCameraFile!!.exists()) {
            scope.launch(Dispatchers.IO) {
                try {
                    val permanentDir = File(context.filesDir, "entry_images").apply { mkdirs() }
                    val targetFile = File(permanentDir, "cam_${System.currentTimeMillis()}.jpg")
                    tempCameraFile!!.copyTo(targetFile, overwrite = true)
                    withContext(Dispatchers.Main) {
                        attachedImages.add(targetFile.absolutePath)
                        snackbarHostState.showSnackbar("Photo attached")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun launchCameraSafe() {
        try {
            val cameraDir = File(context.filesDir, "entry_images").apply { mkdirs() }
            val tempFile = File(cameraDir, "capture_temp_${System.currentTimeMillis()}.jpg").apply {
                createNewFile()
            }
            tempCameraFile = tempFile
            val uri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                tempFile
            )
            tempCameraImageUri = uri
            cameraLauncher.launch(uri)
        } catch (e: Exception) {
            e.printStackTrace()
            scope.launch {
                snackbarHostState.showSnackbar("Camera unavailable: ${e.localizedMessage ?: "Unknown error"}")
            }
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            launchCameraSafe()
        } else {
            scope.launch {
                snackbarHostState.showSnackbar("Camera permission is required to take photos")
            }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            scope.launch(Dispatchers.IO) {
                try {
                    val permanentDir = File(context.filesDir, "entry_images").apply { mkdirs() }
                    val targetFile = File(permanentDir, "img_${System.currentTimeMillis()}.jpg")
                    context.contentResolver.openInputStream(uri)?.use { input ->
                        FileOutputStream(targetFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                    withContext(Dispatchers.Main) {
                        attachedImages.add(targetFile.absolutePath)
                        snackbarHostState.showSnackbar("Photo attached")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    // Audio recording timer loop
    LaunchedEffect(isRecordingAudio) {
        if (isRecordingAudio) {
            val start = System.currentTimeMillis()
            while (isRecordingAudio) {
                recordingElapsedMs = System.currentTimeMillis() - start
                delay(200)
            }
        } else {
            recordingElapsedMs = 0L
        }
    }

    // Audio playback progress loop
    LaunchedEffect(isAudioPlaying) {
        if (isAudioPlaying) {
            while (isAudioPlaying && activeMediaPlayer != null) {
                try {
                    currentAudioProgressMs = activeMediaPlayer?.currentPosition?.toLong() ?: 0L
                } catch (_: Exception) {}
                delay(150)
            }
        }
    }

    fun startAudioRecordingSafe() {
        try {
            // Stop playback if running
            activeMediaPlayer?.stop()
            activeMediaPlayer?.release()
            activeMediaPlayer = null
            isAudioPlaying = false

            val file = AudioNoteManager.createAudioFile(context)
            activeRecordingFile = file
            val recorder = AudioNoteManager.startRecording(context, file)
            activeRecorder = recorder
            isRecordingAudio = true
        } catch (e: Exception) {
            e.printStackTrace()
            isRecordingAudio = false
            activeRecorder = null
            scope.launch {
                snackbarHostState.showSnackbar("Microphone unavailable: ${e.localizedMessage ?: "Recording failed"}")
            }
        }
    }

    fun stopAudioRecordingSafe(save: Boolean) {
        try {
            val recorder = activeRecorder
            activeRecorder = null
            isRecordingAudio = false
            val success = AudioNoteManager.stopRecording(recorder)

            val file = activeRecordingFile
            activeRecordingFile = null

            if (save && success && file != null && file.exists() && file.length() > 0) {
                attachedAudioUri = file.absolutePath
                attachedAudioDurationMs = recordingElapsedMs.coerceAtLeast(1000L)
                scope.launch {
                    snackbarHostState.showSnackbar("Voice note saved (${AudioNoteManager.formatDuration(attachedAudioDurationMs)})")
                }
            } else if (!save) {
                file?.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val audioPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startAudioRecordingSafe()
        } else {
            scope.launch {
                snackbarHostState.showSnackbar("Audio recording permission is required")
            }
        }
    }

    fun toggleAudioPlayback() {
        val path = attachedAudioUri ?: return
        if (isAudioPlaying) {
            try {
                activeMediaPlayer?.pause()
                isAudioPlaying = false
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            try {
                if (activeMediaPlayer == null) {
                    val player = AudioNoteManager.createMediaPlayer(
                        filePath = path,
                        onPrepared = { dur ->
                            if (attachedAudioDurationMs <= 0L) {
                                attachedAudioDurationMs = dur
                            }
                        },
                        onCompletion = {
                            isAudioPlaying = false
                            currentAudioProgressMs = 0L
                        },
                        onError = { errMsg ->
                            isAudioPlaying = false
                            scope.launch { snackbarHostState.showSnackbar(errMsg) }
                        }
                    )
                    activeMediaPlayer = player
                }
                activeMediaPlayer?.start()
                isAudioPlaying = true
            } catch (e: Exception) {
                e.printStackTrace()
                isAudioPlaying = false
            }
        }
    }

    // Release player and recorder resources when leaving screen
    DisposableEffect(Unit) {
        onDispose {
            try {
                if (isRecordingAudio) {
                    AudioNoteManager.stopRecording(activeRecorder)
                    activeRecordingFile?.delete()
                }
                activeMediaPlayer?.release()
                activeMediaPlayer = null
            } catch (_: Exception) {}
        }
    }

    // Load entry if editing existing
    LaunchedEffect(currentEntryId) {
        val id = currentEntryId
        if (id != null) {
            val existing = repository.entries.value.find { it.id == id }
            if (existing != null) {
                title = existing.title
                content = existing.content
                selectedMood = existing.mood
                selectedIcon = existing.icon
                isFavorite = existing.isFavorite
                selectedDateMillis = existing.dateMillis
                selectedTimeFormatted = existing.timeFormatted
                attachedImages.clear()
                attachedImages.addAll(existing.imageUris)
                attachedAudioUri = existing.audioUri
                attachedAudioDurationMs = existing.audioDurationMs
            }
        }
    }

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedDateMillis
    )

    val calendar = remember { Calendar.getInstance() }
    val timePickerState = rememberTimePickerState(
        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = calendar.get(Calendar.MINUTE),
        is24Hour = false
    )

    BackHandler(enabled = !isViewingMode && currentEntryId != null) {
        isViewingMode = true
    }

    val onSave: () -> Unit = {
        if (title.isBlank()) {
            titleError = "Title cannot be empty"
        } else {
            titleError = null
            if (isRecordingAudio) {
                stopAudioRecordingSafe(save = true)
            }
            scope.launch {
                val formattedDate = DiaryRepository.formatDate(selectedDateMillis)
                val idToSave = currentEntryId ?: UUID.randomUUID().toString()
                val newEntry = DiaryEntry(
                    id = idToSave,
                    title = title.trim(),
                    content = content.trim(),
                    dateMillis = selectedDateMillis,
                    dateFormatted = formattedDate,
                    timeFormatted = selectedTimeFormatted,
                    mood = selectedMood,
                    isFavorite = isFavorite,
                    icon = selectedIcon,
                    imageUris = attachedImages.toList(),
                    audioUri = attachedAudioUri,
                    audioDurationMs = attachedAudioDurationMs,
                    createdAt = System.currentTimeMillis()
                )
                repository.saveEntry(newEntry)
                currentEntryId = idToSave
                isViewingMode = true
                snackbarHostState.showSnackbar("Entry saved successfully")
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.statusBars)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                            .padding(horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (!isViewingMode && currentEntryId != null) {
                                    isViewingMode = true
                                } else {
                                    onNavigateBack()
                                }
                            },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = if (!isViewingMode && currentEntryId != null) Icons.Rounded.Close else Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = if (!isViewingMode && currentEntryId != null) "Cancel Edit" else "Back",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Text(
                            text = if (isViewingMode) {
                                "Diary Reflection"
                            } else if (currentEntryId == null) {
                                "New Entry"
                            } else {
                                "Edit Entry"
                            },
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isViewingMode) {
                                IconButton(
                                    onClick = {
                                        currentEntryId?.let { id ->
                                            scope.launch {
                                                repository.toggleFavorite(id)
                                                isFavorite = !isFavorite
                                            }
                                        }
                                    },
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                                        contentDescription = "Favorite",
                                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { showDeleteConfirmation = true },
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.DeleteOutline,
                                        contentDescription = "Delete Entry",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                IconButton(
                                    onClick = { isViewingMode = false },
                                    modifier = Modifier
                                        .size(44.dp)
                                        .pressScale(onClick = { isViewingMode = false })
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Edit,
                                        contentDescription = "Edit Entry",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            } else {
                                IconButton(
                                    onClick = onSave,
                                    modifier = Modifier.size(44.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Check,
                                        contentDescription = "Save",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Box {
                                    IconButton(
                                        onClick = { showOptionsMenu = true },
                                        modifier = Modifier.size(44.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.MoreVert,
                                            contentDescription = "More options",
                                            tint = MaterialTheme.colorScheme.onSurface,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = showOptionsMenu,
                                        onDismissRequest = { showOptionsMenu = false },
                                        shape = RoundedCornerShape(16.dp),
                                        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                    ) {
                                        if (currentEntryId != null) {
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        "Delete Entry",
                                                        color = MaterialTheme.colorScheme.error
                                                    )
                                                },
                                                leadingIcon = {
                                                    Icon(
                                                        imageVector = Icons.Rounded.DeleteOutline,
                                                        contentDescription = null,
                                                        tint = MaterialTheme.colorScheme.error
                                                    )
                                                },
                                                onClick = {
                                                    showOptionsMenu = false
                                                    showDeleteConfirmation = true
                                                }
                                            )
                                        }
                                        DropdownMenuItem(
                                            text = { Text("Clear Inputs") },
                                            onClick = {
                                                title = ""
                                                content = ""
                                                attachedImages.clear()
                                                attachedAudioUri = null
                                                attachedAudioDurationMs = 0L
                                                showOptionsMenu = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surfaceContainer,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(WindowInsets.navigationBars)
                ) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        modifier = Modifier.height(80.dp)
                    ) {
                        NavigationBarItem(
                            selected = false,
                            onClick = onNavigateHome,
                            icon = { Icon(Icons.Rounded.Home, contentDescription = "Home") },
                            label = { Text("Home") },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = onNavigateMood,
                            icon = { Icon(Icons.Rounded.Mood, contentDescription = "Mood") },
                            label = { Text("Mood") },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = onNavigateCalendar,
                            icon = { Icon(Icons.Rounded.CalendarMonth, contentDescription = "Calendar") },
                            label = { Text("Calendar") },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                        NavigationBarItem(
                            selected = false,
                            onClick = onNavigateSettings,
                            icon = { Icon(Icons.Rounded.Settings, contentDescription = "Settings") },
                            label = { Text("Settings") },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            if (isViewingMode) {
                FloatingActionButton(
                    onClick = { isViewingMode = false },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = RoundedCornerShape(16.dp),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 10.dp
                    ),
                    modifier = Modifier
                        .size(56.dp)
                        .pressScale(onClick = { isViewingMode = false })
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = "Edit Entry",
                        modifier = Modifier.size(26.dp)
                    )
                }
            } else {
                FloatingActionButton(
                    onClick = onSave,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(16.dp),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 10.dp
                    ),
                    modifier = Modifier
                        .size(56.dp)
                        .pressScale(onClick = onSave)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Check,
                        contentDescription = "Save Entry",
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        if (isViewingMode) {
            // ==========================================
            // VIEW-ONLY READER MODE
            // ==========================================
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.surface)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                // 1. Mood Badge & Category Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = selectedMood.emoji, fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${selectedMood.displayName} • ${selectedMood.description}",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    val iconVec = when (selectedIcon) {
                        EntryIcon.INBOX -> Icons.Rounded.Inbox
                        EntryIcon.STAR -> Icons.Rounded.Star
                        EntryIcon.ARCHIVE -> Icons.Rounded.Archive
                        EntryIcon.HEART -> Icons.Rounded.Favorite
                        EntryIcon.BOOK -> Icons.Rounded.Bookmark
                    }
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surfaceContainerHigh
                    ) {
                        Box(
                            modifier = Modifier.size(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = iconVec,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 2. Large Entry Title
                Text(
                    text = title.ifBlank { "Untitled Reflection" },
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(12.dp))

                // 3. Metadata Chips Row: Date, Time, Word Count
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerLow
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.CalendarToday,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = DiaryRepository.formatDate(selectedDateMillis),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerLow
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AccessTime,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = selectedTimeFormatted,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    val words = if (content.isBlank()) 0 else content.trim().split(Regex("\\s+")).size
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.surfaceContainerLow
                    ) {
                        Text(
                            text = "$words words",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }

                // 4. Voice Audio Player in Reader Mode (if audio attached)
                if (attachedAudioUri != null) {
                    Spacer(modifier = Modifier.height(18.dp))
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .clickable { toggleAudioPlayback() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isAudioPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                                    contentDescription = if (isAudioPlaying) "Pause voice note" else "Play voice note",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(26.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Voice Note Reflection",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                    Text(
                                        text = AudioNoteManager.formatDuration(
                                            if (isAudioPlaying) currentAudioProgressMs else attachedAudioDurationMs
                                        ),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                val progressFraction = if (attachedAudioDurationMs > 0L) {
                                    (currentAudioProgressMs.toFloat() / attachedAudioDurationMs.toFloat()).coerceIn(0f, 1f)
                                } else 0f

                                LinearProgressIndicator(
                                    progress = { progressFraction },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp)),
                                    color = MaterialTheme.colorScheme.primary,
                                    trackColor = MaterialTheme.colorScheme.surface
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 5. Attached Photos Gallery (in reader view)
                if (attachedImages.isNotEmpty()) {
                    Text(
                        text = "Attached Photos (${attachedImages.size})",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(attachedImages) { _, path ->
                            val bmp = remember(path) {
                                try {
                                    val file = File(path)
                                    if (file.exists()) {
                                        BitmapFactory.decodeFile(file.absolutePath)?.asImageBitmap()
                                    } else null
                                } catch (_: Exception) { null }
                            }
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                modifier = Modifier
                                    .size(width = 170.dp, height = 130.dp)
                                    .clip(RoundedCornerShape(20.dp))
                                    .clickable { previewImageDialogPath = path }
                            ) {
                                if (bmp != null) {
                                    Image(
                                        bitmap = bmp,
                                        contentDescription = "Attached photo",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                // 6. Diary Content Body (Clean readable typography)
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = MaterialTheme.colorScheme.surfaceContainerLow,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        if (content.isNotBlank()) {
                            Text(
                                text = content,
                                style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 28.sp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        } else {
                            Text(
                                text = "No journal notes written for this reflection yet.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 7. Action buttons: Copy / Switch to Edit
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("Diary Entry", "$title\n\n$content")
                            clipboard.setPrimaryClip(clip)
                            scope.launch { snackbarHostState.showSnackbar("Copied entry to clipboard") }
                        },
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Rounded.ContentCopy, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Copy Text")
                    }

                    Button(
                        onClick = { isViewingMode = false },
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(Icons.Rounded.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Edit Entry")
                    }
                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        } else {
            // ==========================================
            // EDIT / CREATE MODE
            // ==========================================
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.surface)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Outlined text field labeled "Entry Title"
                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        if (titleError != null && it.isNotBlank()) titleError = null
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Entry Title") },
                    placeholder = { Text("e.g. Today's Reflections") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    supportingText = {
                        Text(
                            text = titleError ?: "What's on your mind today?",
                            color = if (titleError != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall
                        )
                    },
                    isError = titleError != null,
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Guided Journaling Templates
                Text(
                    text = "Guided Templates (Tap to insert)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val templates = listOf(
                        "🌅 Morning Gratitude" to "1. What 3 things am I grateful for today?\n• \n• \n• \n2. What would make today wonderful?\n3. Daily affirmation:",
                        "🌙 Evening Reflection" to "1. Highlight of my day:\n2. What did I learn today?\n3. How could I have made today even better?",
                        "🏛️ Daily Stoic" to "1. What was within my control today?\n2. What was outside my control?\n3. How did I handle challenges?",
                        "💡 Brain Dump" to "• Ideas & inspirations:\n• Top priorities:\n• Random reflections:",
                        "🌿 Mindful Check-in" to "• How does my mind & body feel right now?\n• What emotion is dominant?\n• What do I need today?"
                    )
                    items(templates) { (tmplName, tmplPrompt) ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .clickable {
                                    content = if (content.isBlank()) tmplPrompt else "$content\n\n$tmplPrompt"
                                }
                        ) {
                            Text(
                                text = tmplName,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Auto-Fetched Date & Time Section
                Text(
                    text = "Date & Time (Auto-fetched)",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp))
                            .pressScale(onClick = { showDatePickerDialog = true })
                            .padding(horizontal = 14.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.CalendarToday,
                                contentDescription = "Pick Date",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Date",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = DiaryRepository.formatDate(selectedDateMillis),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp))
                            .pressScale(onClick = { showTimePickerDialog = true })
                            .padding(horizontal = 14.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Rounded.AccessTime,
                                contentDescription = "Pick Time",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Time",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = selectedTimeFormatted,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Mood Selection: Responsive 2x2 Grid with Theme-Matched Expressive UI
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "How are you feeling?",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = selectedMood.displayName,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // 2x2 Mood Grid
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val moods = Mood.values()
                    for (row in 0 until 2) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            for (col in 0 until 2) {
                                val mood = moods[row * 2 + col]
                                val isSelected = selectedMood == mood

                                val containerColor = if (isSelected) {
                                    MaterialTheme.colorScheme.primaryContainer
                                } else {
                                    MaterialTheme.colorScheme.surfaceContainerLow
                                }
                                val contentColor = if (isSelected) {
                                    MaterialTheme.colorScheme.onPrimaryContainer
                                } else {
                                    MaterialTheme.colorScheme.onSurface
                                }
                                val borderModifier = if (isSelected) {
                                    Modifier.border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(16.dp))
                                } else {
                                    Modifier.border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(16.dp))
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(64.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(containerColor)
                                        .then(borderModifier)
                                        .pressScale(onClick = { selectedMood = mood })
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = mood.emoji,
                                                fontSize = 24.sp
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            Column {
                                                Text(
                                                    text = mood.displayName,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                    color = contentColor
                                                )
                                            }
                                        }

                                        if (isSelected) {
                                            Box(
                                                modifier = Modifier
                                                    .size(22.dp)
                                                    .clip(CircleShape)
                                                    .background(MaterialTheme.colorScheme.primary),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Rounded.Check,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.onPrimary,
                                                    modifier = Modifier.size(14.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Category Icon Selection
                Text(
                    text = "Category Icon",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf(
                        EntryIcon.INBOX to "Reflection",
                        EntryIcon.STAR to "Ideas",
                        EntryIcon.ARCHIVE to "Gratitude",
                        EntryIcon.HEART to "Personal",
                        EntryIcon.BOOK to "Journal"
                    ).forEach { (iconEnum, label) ->
                        val isChosen = selectedIcon == iconEnum
                        val iconVec = when (iconEnum) {
                            EntryIcon.INBOX -> Icons.Rounded.Inbox
                            EntryIcon.STAR -> Icons.Rounded.Star
                            EntryIcon.ARCHIVE -> Icons.Rounded.Archive
                            EntryIcon.HEART -> Icons.Rounded.Favorite
                            EntryIcon.BOOK -> Icons.Rounded.Bookmark
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (isChosen) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surfaceContainerLow
                                )
                                .border(
                                    width = if (isChosen) 2.dp else 1.dp,
                                    color = if (isChosen) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .pressScale(onClick = { selectedIcon = iconEnum }),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = iconVec,
                                contentDescription = label,
                                tint = if (isChosen) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ==========================================
                // VOICE AUDIO NOTE RECORDER (FEATURE 3)
                // ==========================================
                Text(
                    text = "Voice Audio Memo",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (isRecordingAudio) {
                    // Active Recording Card with timer and controls
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.errorContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(14.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.error)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Recording Voice Note...",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onErrorContainer
                                    )
                                    Text(
                                        text = AudioNoteManager.formatDuration(recordingElapsedMs),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.error
                                    )
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(
                                    onClick = { stopAudioRecordingSafe(save = false) },
                                    shape = RoundedCornerShape(16.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text("Discard", style = MaterialTheme.typography.labelMedium)
                                }

                                Button(
                                    onClick = { stopAudioRecordingSafe(save = true) },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(Icons.Rounded.Stop, contentDescription = null, modifier = Modifier.size(18.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Done", style = MaterialTheme.typography.labelMedium)
                                }
                            }
                        }
                    }
                } else if (attachedAudioUri != null) {
                    // Saved Audio Player Card in Edit Mode
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary)
                                    .clickable { toggleAudioPlayback() },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isAudioPlaying) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                                    contentDescription = if (isAudioPlaying) "Pause" else "Play",
                                    tint = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Recorded Memo",
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = FontWeight.SemiBold,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                    Text(
                                        text = AudioNoteManager.formatDuration(
                                            if (isAudioPlaying) currentAudioProgressMs else attachedAudioDurationMs
                                        ),
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                val fraction = if (attachedAudioDurationMs > 0L) {
                                    (currentAudioProgressMs.toFloat() / attachedAudioDurationMs.toFloat()).coerceIn(0f, 1f)
                                } else 0f

                                LinearProgressIndicator(
                                    progress = { fraction },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(6.dp)
                                        .clip(RoundedCornerShape(3.dp)),
                                    color = MaterialTheme.colorScheme.primary,
                                    trackColor = MaterialTheme.colorScheme.surface
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            IconButton(
                                onClick = {
                                    activeMediaPlayer?.release()
                                    activeMediaPlayer = null
                                    isAudioPlaying = false
                                    attachedAudioUri?.let { File(it).delete() }
                                    attachedAudioUri = null
                                    attachedAudioDurationMs = 0L
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.DeleteOutline,
                                    contentDescription = "Delete audio note",
                                    tint = MaterialTheme.colorScheme.error,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                } else {
                    // Record Button Trigger
                    OutlinedButton(
                        onClick = {
                            val hasRecordPermission = ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.RECORD_AUDIO
                            ) == PackageManager.PERMISSION_GRANTED

                            if (hasRecordPermission) {
                                startAudioRecordingSafe()
                            } else {
                                audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            }
                        },
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Mic,
                            contentDescription = "Record Audio",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Record Voice Audio Note",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Photos / Images Section (Overflow-proof)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Photos (${attachedImages.size})",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        // Safe Camera button with permission handling
                        OutlinedButton(
                            onClick = {
                                val hasCamPermission = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED

                                if (hasCamPermission) {
                                    launchCameraSafe()
                                } else {
                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            },
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.PhotoCamera,
                                contentDescription = "Camera",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Camera", style = MaterialTheme.typography.labelMedium)
                        }

                        // Gallery upload button
                        OutlinedButton(
                            onClick = { galleryLauncher.launch("image/*") },
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.AddPhotoAlternate,
                                contentDescription = "Upload",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Upload", style = MaterialTheme.typography.labelMedium)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (attachedImages.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsIndexed(attachedImages) { idx, path ->
                            val bitmap = remember(path) {
                                try {
                                    val file = File(path)
                                    if (file.exists()) {
                                        BitmapFactory.decodeFile(file.absolutePath)?.asImageBitmap()
                                    } else null
                                } catch (e: Exception) {
                                    null
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                    .clickable { previewImageDialogPath = path }
                            ) {
                                if (bitmap != null) {
                                    Image(
                                        bitmap = bitmap,
                                        contentDescription = "Attached photo $idx",
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(4.dp)
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.85f))
                                        .clickable { attachedImages.removeAt(idx) },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Rounded.Close,
                                        contentDescription = "Remove photo",
                                        tint = MaterialTheme.colorScheme.error,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                }

                // Entry Content Multiline text field
                Text(
                    text = "Diary Content",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    placeholder = {
                        Text(
                            "Write your thoughts, daily memories, insights, feelings, and gratitude here...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    )
                )

                val wordCount = remember(content) {
                    if (content.isBlank()) 0 else content.trim().split(Regex("\\s+")).size
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "$wordCount words • ${content.length} characters",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Press ✓ to save",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }

    // Modal DatePicker Dialog
    if (showDatePickerDialog) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val picked = datePickerState.selectedDateMillis
                        if (picked != null) {
                            selectedDateMillis = picked
                        }
                        showDatePickerDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerDialog = false }) {
                    Text("Cancel")
                }
            },
            shape = RoundedCornerShape(28.dp)
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Modal TimePicker Dialog
    if (showTimePickerDialog) {
        AlertDialog(
            onDismissRequest = { showTimePickerDialog = false },
            title = {
                Text(
                    text = "Select Time",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    TimePicker(state = timePickerState)
                }
            },
            shape = RoundedCornerShape(28.dp),
            confirmButton = {
                TextButton(
                    onClick = {
                        val hour = timePickerState.hour
                        val minute = timePickerState.minute
                        val amPm = if (hour >= 12) "PM" else "AM"
                        val displayHour = when {
                            hour == 0 -> 12
                            hour > 12 -> hour - 12
                            else -> hour
                        }
                        val formattedMinute = minute.toString().padStart(2, '0')
                        selectedTimeFormatted = "$displayHour:$formattedMinute $amPm"
                        showTimePickerDialog = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePickerDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Delete Confirmation Dialog
    if (showDeleteConfirmation && currentEntryId != null) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmation = false },
            title = { Text("Delete Entry?") },
            text = { Text("Are you sure you want to delete this diary entry? This action cannot be undone.") },
            shape = RoundedCornerShape(28.dp),
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            currentEntryId?.let { repository.deleteEntry(it) }
                            showDeleteConfirmation = false
                            onNavigateBack()
                        }
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirmation = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Image Zoom Dialog
    if (previewImageDialogPath != null) {
        val path = previewImageDialogPath!!
        val bmp = remember(path) {
            try {
                BitmapFactory.decodeFile(path)?.asImageBitmap()
            } catch (e: Exception) {
                null
            }
        }
        AlertDialog(
            onDismissRequest = { previewImageDialogPath = null },
            shape = RoundedCornerShape(20.dp),
            text = {
                if (bmp != null) {
                    Image(
                        bitmap = bmp,
                        contentDescription = "Zoomed photo",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { previewImageDialogPath = null }) {
                    Text("Close")
                }
            }
        )
    }
}
