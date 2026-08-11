package com.example.oneclickpay.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oneclickpay.R

@Composable
fun Cards() {
    var cardNumber by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.background))
            .padding(12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CardsHeader()

        Spacer(modifier = Modifier.height(4.dp))
        CardImage()
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Card Number",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )
        CardEditText(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            hint = "0000 0000 0000 0000",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Card Holder Name",
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
        )
        CardEditText(
            value = cardHolderName,
            onValueChange = { cardHolderName = it },
            hint = "Enter Full Name",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Expiry Date",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                CardEditText(
                    value = expiryDate,
                    onValueChange = { expiryDate = it },
                    hint = "MM/YY",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "CVV",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
                CardEditText(
                    value = cvv,
                    onValueChange = { cvv = it },
                    hint = "•••",
                    modifier = Modifier.fillMaxWidth()
                )
            }

        }
        InformationBox()
        AddCardButton(onClick = {})
    }
}

@Composable
fun AddCardButton(
    onClick: () -> Unit,

    ) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        enabled = true,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.button_color),
            contentColor = colorResource(R.color.button_text_color)
        ),
        elevation = ButtonDefaults.buttonElevation(12.dp)
    ) {
        Text(
            text = "Add Card",
            fontSize = 22.sp,
            color = colorResource(R.color.button_text_color),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
fun InformationBox() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.info_color))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(height = 40.dp, width = 38.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.lock_ic),
                    contentDescription = "Lock Icon",
                    tint = colorResource(R.color.button_color),
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Your payment information is encrypted\n" +
                        "and never stored on our servers.\n" +
                        "Transactions are processed through an\n" +
                        "industry-standard secure gateway.",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun CardsHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add New Card",
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = "Link a secure payment method to your digital vault.",
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
        )
    }
}

@Composable
fun CardEditText(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    label: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        singleLine = singleLine,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        label = label?.let { { Text(text = it) } },
        placeholder = { Text(text = hint, color = Color.Gray, fontSize = 14.sp) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        supportingText = {
            if (isError && !errorMessage.isNullOrEmpty()) {
                Text(text = errorMessage, color = Color.Red, fontSize = 12.sp)
            }
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.White,
            unfocusedBorderColor = Color.White,
            errorBorderColor = Color.Red,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            cursorColor = Color.Black,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
        )
    )
}

@Composable
fun CardImage() {
    val cardGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF11151F),
            Color(0xFF202738)
        )
    )
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = cardGradient, shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "NEXUS ELITE",
                    fontSize = 14.sp,
                    color = colorResource(R.color.white)
                )
                Icon(
                    painter = painterResource(R.drawable.wifi_ic),
                    contentDescription = "",
                    tint = colorResource(R.color.wifi_color)
                )
            }
            Icon(
                painter = painterResource(R.drawable.visa_ic),
                contentDescription = "",
                modifier = Modifier.padding(bottom = 4.dp),
                tint = Color.Unspecified
            )

            Text(
                text = "•••• •••• •••• ••••",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "CARD HOLDER",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
                Text(
                    text = "EXPIRES",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "YOUR NAME",
                    fontSize = 12.sp,
                    color = colorResource(R.color.white)
                )
                Text(
                    text = "MM/YY",
                    fontSize = 12.sp,
                    color = colorResource(R.color.white)
                )
            }
        }
    }
}