package com.example.oneclickpay.sendmoney

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.composereview.mainascreen.Constants
import com.example.domain.recenttranscations.model.AllUsers
import com.example.domain.recenttranscations.model.SendMoneyRequest
import com.example.oneclickpay.sendmoney.sendmoneyui.AmountDisplaySection
import com.example.oneclickpay.sendmoney.sendmoneyui.ConfirmTransferSection
import com.example.oneclickpay.sendmoney.sendmoneyui.CustomNumericKeypad
import com.example.oneclickpay.sendmoney.sendmoneyui.RecipientsHorizontalList
import com.example.oneclickpay.sendmoney.sendmoneyui.TransferUser
import com.example.oneclickpay.sendmoney.sendmoneyui.sampleUsers
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun SendMoneyScreen(
    viewModel: SendMoneyViewModel = koinViewModel(),
    onConfirmTransfer: (selectedUser: AllUsers?, amount: Double, note: String) -> Unit = { _, _, _ -> }
) {
    val context = LocalContext.current
    val State by viewModel.state.collectAsStateWithLifecycle()

    var moneyInput by remember { mutableStateOf("0") }
    var selectedUser by remember { mutableStateOf<AllUsers?>(null) }
    var noteText by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        viewModel.processIntent(SendMoneyIntent.getAllUser)
    }
    val recipientsList = (State as? SendMoneyState.getAllUser)?.users ?: emptyList()

    LaunchedEffect(recipientsList) {
        if (selectedUser == null && recipientsList.isNotEmpty()) {
            selectedUser = recipientsList.first()
        }
    }
    // by default note edit text = Sent to + username
    LaunchedEffect(selectedUser) {
        selectedUser?.let { user ->
            noteText = "Sent to ${user.name}"
        }
    }
    LaunchedEffect(State) {
        when (val state = State) {
            is SendMoneyState.Success -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                val parsedAmount = moneyInput.toDoubleOrNull() ?: 0.0
                onConfirmTransfer(selectedUser, parsedAmount, noteText)
            }

            is SendMoneyState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }

            is SendMoneyState.getAllUser -> {

                if (selectedUser == null && recipientsList.isNotEmpty()) {
                    selectedUser = recipientsList.first()
                }
            }

            else -> {}
        }
    }

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
                users = recipientsList,
                selectedUser = selectedUser,
                onUserSelect = { selectedUser = it }
            )

            Spacer(modifier = Modifier.height(18.dp))

            AmountDisplaySection(
                amountText = moneyInput,
                noteText = noteText,
                onNoteChange = { noteText = it }
            )
        }

        CustomNumericKeypad(
            onKeyPress = { key ->
                when (key) {
                    "backspace" -> {
                        moneyInput = if (moneyInput.length > 1) moneyInput.dropLast(1) else "0"
                    }

                    "." -> {
                        if (!moneyInput.contains(".")) {
                            moneyInput = if (moneyInput.isEmpty()) "0." else "$moneyInput."
                        }
                    }

                    else -> {
                        moneyInput = if (moneyInput == "0") key else moneyInput + key
                    }
                }
            }
        )

        ConfirmTransferSection(
            isLoading = State is SendMoneyState.Loading,
            onConfirmClick = {
                val parsedAmount = moneyInput.toDoubleOrNull() ?: 0.0
                if (parsedAmount <= 0.0) {
                    Toast.makeText(context, "برجاء إدخال مبلغ صحيح للتحويل", Toast.LENGTH_SHORT)
                        .show()
                    return@ConfirmTransferSection
                }

                val receiverId = selectedUser?.identifier
                viewModel.processIntent(
                    SendMoneyIntent.ConfirmTransfer(
                        SendMoneyRequest(
                            senderIdentifier = Constants.PHONE_IDENTIFIER,
                            receiverIdentifier = receiverId!!,
                            amount = parsedAmount,
                            currency = "EGP",
                            title = noteText
                        )
                    )
                )
            }
        )
    }
}

