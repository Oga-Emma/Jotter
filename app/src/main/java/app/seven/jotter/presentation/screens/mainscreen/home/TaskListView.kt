package app.seven.jotter.presentation.screens.mainscreen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import app.seven.jotter.presentation.theme.Spacing
import app.seven.jotter.presentation.theme.pallet
import app.seven.jotter.presentation.theme.spacing
import app.seven.jotter.presentation.theme.textSize

@Composable
fun TasksList(modifier: Modifier = Modifier, onClick: () -> Unit) {
    LazyColumn(modifier = modifier) {
        item {
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
        }
        items(count = 6) { index: Int ->
            TasksListItem(modifier = Modifier.padding(vertical = 4.dp), onClick = onClick)
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun TasksListItem(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier,
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
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column {
                Text("9:00", fontWeight = FontWeight.SemiBold)
                Text("9:30", color = Color.Gray)
            }
            Spacer(modifier = Modifier.padding(spacing().lSmall))
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VerticalDivider(
                        modifier = Modifier.height(14.dp),
                        thickness = 2.dp,
                        color = pallet().primary.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(spacing().small))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Meeting",
                        fontWeight = FontWeight.SemiBold,
                        color = pallet().primary
                    )
                    Spacer(modifier = Modifier.width(spacing().small))

                    TaskTypeArea()
                }
                Column() {
                    Spacer(modifier = Modifier.height(spacing().small))
                    Text(
                        "Meeting with Boss",
                        fontSize = textSize().medium,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.height(spacing().small))
                    Text("Meeting to discuss future projects with boss", color = Color.Gray)
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = spacing().small),
                        color = Color.LightGray.copy(0.2f)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ReminderField(
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

            }

        }
    }
}

@Composable
fun ReminderField(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(spacing().medium),
            imageVector = Icons.Default.Alarm,
            tint = Color.LightGray,
            contentDescription = "",
        )
        Spacer(modifier = Modifier.width(spacing().xSmall))
        Text(
            text = "5pm",
            color = Color.LightGray,
            fontSize = textSize().lSmall,
        )
        VerticalDivider(
            modifier = Modifier
                .padding(spacing().small)
                .height(10.dp),
            thickness = 2.dp,
            color = Color.LightGray
        )
        Text(
            text = "+ 2 more",
            color = Color.LightGray,
            fontSize = textSize().lSmall,
        )
    }
}

@Composable
fun TaskTypeArea(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(spacing().medium),
            imageVector = Icons.Default.RepeatOne,
            tint = Color.LightGray,
            contentDescription = "",
        )
        Spacer(modifier = Modifier.width(spacing().xSmall))
        Text(
            text = "Once",
            color = Color.LightGray,
            fontSize = textSize().lSmall,
        )
    }
}

@Preview
@Composable
fun TasksListPreview() {
    TasksList(onClick = {})
}

@Preview
@Composable
fun TasksListItemPreview() {
    TasksListItem(onClick = {})
}
