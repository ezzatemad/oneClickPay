package com.example.oneclickpay.card.addnewcard.cardui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


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
            errorContainerColor = Color.White
        )
    )
}
