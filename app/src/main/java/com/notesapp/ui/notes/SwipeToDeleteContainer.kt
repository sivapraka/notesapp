package com.notesapp.ui.notes

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
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
