package com.notesapp.presentation.seatselection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.*
import com.notesapp.domain.model.Seat
import com.notesapp.domain.model.SeatLayout
import com.notesapp.domain.model.SeatLayout1

@Composable
fun DynamicSeatLayout1(seatLayout: SeatLayout, onSeatClick: (String) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(seatLayout.rows) { rowIndex ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                (0 until seatLayout.columns).forEach { colIndex ->
                    val seat = seatLayout.seats.find { it.row == rowIndex && it.column == colIndex }

                    if (seat != null) {
                        SeatItem(seat = seat, modifier = Modifier, onSeatClick = { onSeatClick(seat.id) })
                    } else if (seatLayout.aislePositions.contains(colIndex)) {
                        Spacer(modifier = Modifier.width(24.dp)) // Aisle gap
                    } else {
                        Spacer(modifier = Modifier.size(32.dp)) // Empty/no seat
                    }
                }
            }
        }
    }
}


@Composable
fun DynamicCurvedSeatLayout(
    seatLayout: SeatLayout1,
    onSeatClick: (Seat) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        ScreenArc()

        seatLayout.seats.forEachIndexed { rowIndex, rowSeats ->
            // Apply arc-like offset using padding
            val curveOffset = (seatLayout.seats.size - rowIndex) * 6.dp

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(start = curveOffset)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowSeats.forEach { seat ->
                    SeatItem(seat = seat,modifier= Modifier) {
                        onSeatClick(seat)
                    }
                }
            }
        }
    }
}


