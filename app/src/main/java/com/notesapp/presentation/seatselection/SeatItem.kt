package com.notesapp.presentation.seatselection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.notesapp.domain.model.Seat
import com.notesapp.domain.model.SeatCategory
import com.notesapp.domain.model.SeatStatus


@Composable
fun SeatItem(seat: Seat, modifier: Modifier, onSeatClick: () -> Unit) {
    val seatColor = when (seat.status) {
        SeatStatus.CLOSED -> Color.Red
        SeatStatus.RESERVED -> Color.DarkGray
        SeatStatus.AVAILABLE -> when (seat.category) {
            SeatCategory.STANDARD -> Color.White
            SeatCategory.RECLINER -> Color.Cyan
            SeatCategory.BALCONY -> Color.Magenta
            SeatCategory.PRIME -> Color.Yellow
        }

        SeatStatus.SELECTED -> Color.Green
        SeatStatus.BOOKED -> Color.Blue
    }

    val enabled = seat.status == SeatStatus.AVAILABLE || seat.status == SeatStatus.SELECTED
    Icon(
        imageVector = Icons.Filled.EventSeat,
        contentDescription = "Seat",
        tint = seatColor,
        modifier = modifier
            .size(40.dp)
            .clickable(enabled = enabled) { onSeatClick() }
    )

    /*    Box(
            modifier = Modifier
                .size(28.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
                .clickable(enabled = seat.status == SeatStatus.AVAILABLE
                        || seat.status == SeatStatus.SELECTED) { onSeatClick() }
        )*/
}
