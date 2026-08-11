package com.example.oneclickpay.sendmoney

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.oneclickpay.R


@Composable
fun SendMoneyScreen(
    users: List<TransferUser> = sampleUsers,
    onConfirmTransfer: (selectedUser: TransferUser?, amount: String, note: String) -> Unit = { _, _, _ -> }
) {
    var rawInput by remember { mutableStateOf("0") }
    var noteText by remember { mutableStateOf("") }
    var selectedUser by remember { mutableStateOf(users.firstOrNull()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(12.dp))

            RecipientsHorizontalList(
                users = users,
                selectedUser = selectedUser,
                onUserSelect = { selectedUser = it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            AmountDisplaySection(
                amount = rawInput,
                noteText = noteText,
                onNoteChange = { noteText = it }
            )
        }

        CustomNumericKeypad(
            onKeyPress = { key ->
                when (key) {
                    "backspace" -> {
                        if (rawInput.isNotEmpty()) {
                            rawInput = rawInput.dropLast(1)
                        }
                    }
                    "." -> {
                        if (!rawInput.contains(".")) {
                            rawInput = if (rawInput.isEmpty()) "0." else "$rawInput."
                        }
                    }
                    else -> {
                        rawInput = if (rawInput == "0") key else rawInput + key
                    }
                }
            }
        )

        ConfirmTransferSection(
            onConfirmClick = { onConfirmTransfer(selectedUser, rawInput, noteText) }
        )
    }
}

@Composable
fun RecipientsHorizontalList(
    users: List<TransferUser>,
    selectedUser: TransferUser?,
    onUserSelect: (TransferUser) -> Unit,
    modifier: Modifier = Modifier
) {
    if (users.isEmpty()) return

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "RECIPIENTS",
            color = Color.Gray,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.2.sp,
            modifier = Modifier.padding(start = 6.dp, bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
        ) {
            items(items = users, key = { it.id }) { user ->
                val isSelected = selectedUser?.id == user.id

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .width(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { onUserSelect(user) }
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .then(
                                if (isSelected) {
                                    Modifier.border(
                                        width = 2.dp,
                                        color = colorResource(R.color.doral_color),
                                        shape = CircleShape
                                    )
                                } else Modifier
                            )
                            .padding(if (isSelected) 3.dp else 0.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = user.avatarUrl ?: R.drawable.trans_ic,
                            placeholder = painterResource(R.drawable.trans_ic),
                            error = painterResource(R.drawable.trans_ic),
                            contentDescription = user.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = user.name,
                        color = if (isSelected) Color.White else Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
@Composable
fun AmountDisplaySection(
    amount: String,
    noteText: String,
    onNoteChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "ENTER AMOUNT",
            color = Color.Gray,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$",
                color = colorResource(R.color.doral_color),
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = amount.ifEmpty { "0" },
                color = Color.White,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = noteText,
            onValueChange = onNoteChange,
            placeholder = {
                Text(
                    text = "Add a note (optional)",
                    color = Color(0xFF5F6775),
                    fontSize = 14.sp
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.note_ic),
                    contentDescription = "Note Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = colorResource(R.color.card_iamge_background),
                unfocusedContainerColor = colorResource(R.color.card_iamge_background),
                focusedBorderColor = Color(0xFF2A2E37),
                unfocusedBorderColor = Color(0xFF232730),
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                cursorColor = Color.White
            ),
            singleLine = true
        )
    }
}

@Composable
fun CustomNumericKeypad(
    onKeyPress: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keypadLayout = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf(".", "0", "backspace")
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        keypadLayout.forEach { rowKeys ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                rowKeys.forEach { key ->
                    KeypadButton(
                        key = key,
                        onClick = { onKeyPress(key) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun KeypadButton(
    key: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(
                    bounded = false,
                    radius = 32.dp,
                    color = Color.White.copy(alpha = 0.2f)
                ),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        when (key) {
            "backspace" -> {
                Icon(
                    painter = painterResource(R.drawable.backspace_ic),
                    contentDescription = "Backspace",
                    tint = Color(0xFFD1D5DB),
                    modifier = Modifier.size(26.dp)
                )
            }
            "." -> {
                Text(
                    text = "•",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            else -> {
                Text(
                    text = key,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ConfirmTransferSection(
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onConfirmClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.doral_color))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Confirm Transfer",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.size(10.dp))
                Icon(
                    painter = painterResource(R.drawable.send_ic),
                    contentDescription = "Send",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "No fee for NexusPay to NexusPay transfers.",
            color = Color(0xFF6B7280),
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}

data class TransferUser(
    val id: String,
    val name: String,
    val avatarUrl: String? = null
)
val sampleUsers = listOf(
    TransferUser("1", "Ahmed"),
    TransferUser("2", "Omar"),
    TransferUser("3", "Sara"),
    TransferUser("4", "Mona"),
    TransferUser("5", "Ali")
)