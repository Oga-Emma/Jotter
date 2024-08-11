package app.seven.jotter.presentation.screens.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.seven.jotter.presentation.theme.spacing
import app.seven.jotter.presentation.theme.textSize

@Composable
fun CalendarEvents() {
    LazyColumn {
        items((1..20).toList()) {
            CalendarEventItem()
        }
    }
}

@Composable
fun CalendarEventItem() {
    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = 0.dp
        ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(
            width = .3.dp,
            color = Color.LightGray
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.small, vertical = spacing.lSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = spacing.small),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Wed",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
                Text(
                    text = "11",
                    fontWeight = FontWeight.Bold,
                    fontSize = textSize.lMedium
                )
            }
            VerticalDivider(
                modifier = Modifier
                    .height(36.dp)
                    .padding(horizontal = spacing.small),
                color = Color.LightGray
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = spacing.small),
                verticalArrangement = Arrangement.Center,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Team Meetups",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(spacing.small))
                    Icon(
                        modifier = Modifier.size(spacing.lSmall),
                        imageVector = Icons.Default.Alarm,
                        contentDescription = "time",
                        tint = Color.Gray,
                    )
                    Spacer(modifier = Modifier.padding(end = spacing.xSmall))
                    Text(text = "8:00PM",
                        fontSize = 13.sp,
                        color = Color.Gray)
                }
                Text(
                    text = "Some random description",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun CalendarEventItemPreview() {
    CalendarEventItem()
}

@Preview
@Composable
fun CalendarEventsPreview() {
    CalendarEvents()
}