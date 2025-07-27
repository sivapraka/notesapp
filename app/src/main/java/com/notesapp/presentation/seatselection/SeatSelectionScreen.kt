package com.notesapp.presentation.seatselection

import android.R
import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.notesapp.data.local.entity.MovieTimes
import com.notesapp.domain.model.Seat
import com.notesapp.domain.model.SeatCategory
import com.notesapp.domain.model.SeatLayout1
import com.notesapp.domain.model.SeatStatus
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@SuppressLint("NewApi")
@Composable
fun SeatSelectionScreen(
    movieId: Int,
    theatreId: String,
    showId: String,
    viewModel: SeatSelectionViewModel = hiltViewModel(),
    onBuyTicketClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current
    val upcomingDates = remember { (0..3).map { LocalDate.now().plusDays(it.toLong()) } }
    val selectedTime = rememberSaveable { mutableStateOf<String?>(null) }
    val today = LocalDate.now()
    val maxDate = today.plusWeeks(1)
    // Initial load of seat layout
    LaunchedEffect(Unit) {
        viewModel.loadSeats(theatreId, showId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {

        ScreenArc()

        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(8),
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.seats) { seat ->
                SeatItem(
                    seat = seat, modifier = Modifier,
                    onSeatClick = { viewModel.onSeatClick(seat.id) }
                )
            }
        }

       // SeatBookingScreen()

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            SeatLegend("Closed", Color.Gray)
            SeatLegend("Available", Color.White)
            SeatLegend("Reserved", Color.LightGray)
            SeatLegend("Selected", Color.Green)
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            upcomingDates.forEach { date ->
                FilterChip(
                    selected = selectedDate == date,
                    onClick = { selectedDate = date },
                    label = {
                        Text(
                            if (date == LocalDate.now()) "Today"
                            else date.format(DateTimeFormatter.ofPattern("EEE, dd MMM"))
                        )
                    }
                )
            }
            // Add DatePicker icon to the right
            IconButton(
                onClick = {
                    val datePicker = android.app.DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                        },
                        selectedDate.year,
                        selectedDate.monthValue - 1,
                        selectedDate.dayOfMonth
                    )
                    datePicker.datePicker.minDate = System.currentTimeMillis()
                    val zone = ZoneId.systemDefault()
                    val maxMillis = maxDate.atStartOfDay(zone).toInstant().toEpochMilli()
                    datePicker.datePicker.maxDate = maxMillis
                    datePicker.show()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Pick a date"
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .horizontalScroll(rememberScrollState()),
        ) {
            val movieList = listOf(
                MovieTimes(time = "10:30 AM"),
                MovieTimes(time = "11:30 AM"),
                MovieTimes(time = "2:30 PM"),
                MovieTimes(time = "4:00 PM"),
                MovieTimes(time = "7:20 PM"),
                MovieTimes(time = "8:45 PM"),
                MovieTimes(time = "9:30 PM")
            )
            MovieTime(movieList,selectedTime)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Selected: ${uiState.selectedSeats.size} / ${viewModel.maxSeatSelection} Seats",
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyLarge, color = Color.Black
                )
                Text(
                    text = "$ ${"%.2f".format(uiState.totalPrice)}",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Button(
                onClick = onBuyTicketClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Buy Ticket", color = Color.White)
            }
        }
    }
}



@Composable
fun SeatBookingScreen() {
    var seatLayout by remember { mutableStateOf(generateSampleSeatLayout()) }

    DynamicCurvedSeatLayout(seatLayout = seatLayout) { clickedSeat ->
        seatLayout = seatLayout.copy(
            seats = seatLayout.seats.map { row ->
                row.map { seat ->
                    if (seat.id == clickedSeat.id) {
                        seat.copy(
                            status = if (seat.status == SeatStatus.SELECTED) SeatStatus.AVAILABLE else SeatStatus.SELECTED
                        )
                    } else seat
                }
            }
        )
    }
}


fun generateSampleSeatLayout(): SeatLayout1
{
    val rows = 7
    val seatLayout = mutableListOf<List<Seat>>()
    val random = Random(System.currentTimeMillis())

    for (row in 0 until rows) {
        val columns = 5 + row * 2 // More seats per lower row for curve effect
        val rowSeats = List(columns) { col ->
            Seat(
                id = "R$row-C$col",
                row = row,
                column = col,
                status = SeatStatus.AVAILABLE,
                category = SeatCategory.STANDARD,
                price = 150f
            )
        }
        seatLayout.add(rowSeats)
    }

    return SeatLayout1(
        rows = rows,
        columns = seatLayout.maxOf { it.size },
        seats = seatLayout
    )
}

