package com.notesapp.presentation.seatselection

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun ScreenArc(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.height(40.dp).fillMaxWidth()) {
        drawArc(
            color = Color.Magenta,
            startAngle = 180f,
            sweepAngle = 180f,
            useCenter = false,
            style = Stroke(width = 6.dp.toPx(), cap = StrokeCap.Round),
            topLeft = Offset(0f, size.height / 4),
            size = Size(size.width, size.height)
        )
    }
}
