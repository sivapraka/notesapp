package com.notesapp.ui.notes

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeToDeleteContainer(
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {
    val swipeOffset = remember { Animatable(0f) }
    val maxOffset = 300f
    val scope = rememberCoroutineScope()
    val dismissed = remember { mutableStateOf(false) }

    if (!dismissed.value) {

            // Foreground swipable content
            Box(
                modifier = Modifier
                    .offset { IntOffset(swipeOffset.value.roundToInt(), 0) }
                    .fillMaxWidth()
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onHorizontalDrag = { _, dragAmount ->
                                scope.launch {
                                    val newOffset =
                                        (swipeOffset.value + dragAmount).coerceIn(0f, maxOffset)
                                    swipeOffset.snapTo(newOffset)
                                }
                            },
                            onDragEnd = {
                                scope.launch {
                                    if (swipeOffset.value > maxOffset * 0.4f) {
                                        swipeOffset.animateTo(maxOffset)
                                        dismissed.value = true
                                        onDelete()
                                    } else {
                                        swipeOffset.animateTo(0f)
                                    }
                                }
                            }
                        )
                    }
            ) {
                content()
            }
        }

}
