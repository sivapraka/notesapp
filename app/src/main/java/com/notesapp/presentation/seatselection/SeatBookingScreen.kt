package com.notesapp.presentation.seatselection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun SeatBookingScreen() {
    val initialLayout = remember { sampleDynamicTheaterLayout() }
    var layoutState by remember { mutableStateOf(initialLayout) }

    Column(
        modifier = Modifier.fillMaxSize().padding(12.dp)
    ) {
        Text("Select Your Seats", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            layoutState.sections.forEach { section ->
                item {
                    Text(
                        text = "${section.name} - Rs.${section.price}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    section.rows.forEach { rowLayout ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(rowLayout.label, fontWeight = FontWeight.SemiBold, modifier = Modifier.width(20.dp))

                            var currentIndex = 0
                            rowLayout.aisles.sortedBy { it.position }.forEach { aisle ->
                                // Seats before this aisle
                                rowLayout.seats.subList(currentIndex, aisle.position).forEach { seat ->
                                    SeatItem(seat) {
                                        layoutState = toggleSeatDynamic(layoutState, section.name, rowLayout.label, seat)
                                    }
                                }
                                Spacer(modifier = Modifier.width(aisle.width)) // aisle gap
                                currentIndex = aisle.position
                            }

                            // Remaining seats after last aisle
                            rowLayout.seats.drop(currentIndex).forEach { seat ->
                                SeatItem(seat) {
                                    layoutState = toggleSeatDynamic(layoutState, section.name, rowLayout.label, seat)
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        SeatLegend()
    }
}



@Composable
fun SeatItem(seat: Seat, onSeatClick: () -> Unit) {
    val seatColor = when (seat.status) {
        SeatStatus.AVAILABLE -> Color.White
        SeatStatus.SELECTED -> Color.Green
        SeatStatus.SOLD -> Color.LightGray
        SeatStatus.BESTSELLER -> Color.Yellow
    }

    Box(
        modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(seatColor)
            .border(1.dp, Color.DarkGray, RoundedCornerShape(6.dp))
            .clickable(enabled = seat.status != SeatStatus.SOLD) { onSeatClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = seat.number.toString(),
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
    }
}



fun toggleSeatDynamic(
    layout: TheaterLayout,
    sectionName: String,
    rowLabel: String,
    clickedSeat: Seat
): TheaterLayout {
    return layout.copy(
        sections = layout.sections.map { section ->
            if (section.name == sectionName) {
                section.copy(
                    rows = section.rows.map { row ->
                        if (row.label == rowLabel) {
                            row.copy(
                                seats = row.seats.map { seat ->
                                    if (seat == clickedSeat) {
                                        seat.copy(
                                            status = if (seat.status == SeatStatus.SELECTED)
                                                SeatStatus.AVAILABLE else SeatStatus.SELECTED
                                        )
                                    } else seat
                                }
                            )
                        } else row
                    }
                )
            } else section
        }
    )
}



fun sampleDynamicTheaterLayout(): TheaterLayout {
    return TheaterLayout(
        sections = listOf(
            SectionLayout(
                name = "Platinum",
                price = 200,
                rows = listOf(
                    RowLayout(
                        label = "A",
                        seats = (1..12).map { Seat("A", it, SeatStatus.AVAILABLE) },
                        aisles = listOf(Aisle(6, 30.dp)) // 6 left, aisle, 6 right
                    ),
                    RowLayout(
                        label = "B",
                        seats = (1..10).map { Seat("B", it, SeatStatus.BESTSELLER) },
                        aisles = listOf(Aisle(4, 24.dp)) // 4 left, aisle, 6 right
                    )
                )
            ),
            SectionLayout(
                name = "Gold",
                price = 150,
                rows = listOf(
                    RowLayout(
                        label = "C",
                        seats = (1..14).map { Seat("C", it, SeatStatus.AVAILABLE) },
                        aisles = listOf(Aisle(7, 40.dp))
                    ),
                    RowLayout(
                        label = "D",
                        seats = (1..14).map { Seat("D", it, SeatStatus.SOLD) },
                        aisles = listOf(Aisle(7, 40.dp))
                    )
                )
            ),
            SectionLayout(
                name = "Balcony",
                price = 100,
                rows = listOf(
                    RowLayout(
                        label = "E",
                        seats = (1..8).map { Seat("E", it, SeatStatus.AVAILABLE) },
                        aisles = listOf() // no aisle
                    )
                )
            )
        )
    )
}



@Composable
fun SeatLegend() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        LegendItem(Color.White, "Available")
        LegendItem(Color.Green, "Selected")
        LegendItem(Color.Yellow, "Bestseller")
        LegendItem(Color.LightGray, "Sold")
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
                .border(1.dp, Color.DarkGray, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(label, fontSize = 12.sp)
    }
}



data class TheaterLayout(
    val sections: List<SectionLayout>
)

data class SectionLayout(
    val name: String,           // e.g., "Platinum", "Gold", "Balcony"
    val price: Int,             // e.g., 150
    val rows: List<RowLayout>
)

data class RowLayout(
    val label: String,          // e.g., "A", "B", "C"
    val seats: List<Seat>,
    val aisles: List<Aisle>     // allows multiple aisles per row
)

data class Seat(
    val row: String,
    val number: Int,
    val status: SeatStatus
)

data class Aisle(
    val position: Int,   // index after which aisle appears
    val width: Dp
)

enum class SeatStatus { AVAILABLE, SELECTED, SOLD, BESTSELLER }
