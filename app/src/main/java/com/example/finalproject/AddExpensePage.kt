package com.example.finalproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@Composable
fun AddExpensePage(viewModel: ExpenseViewModel, onBack: () -> Unit) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var isRecurring by remember { mutableStateOf(false) }
    var date by remember { mutableStateOf(LocalDate.now().toString()) }
    var errorMessage by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFFE3F2FD) // Light blue
    val titleBackground = Color(0xFF81D4FA) // Sky blue
    val buttonColor = Color(0xFF4CAF50)     // Green

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Title Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(titleBackground, shape = RoundedCornerShape(12.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = "Add Expense",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // Input Fields
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Note") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date (YYYY-MM-DD)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Recurring Checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = isRecurring,
                    onCheckedChange = { isRecurring = it }
                )
                Text("Recurring Expense")
            }

            // Save Button with Validation
            Button(
                onClick = {
                    val amt = amount.toDoubleOrNull()
                    if (amount.isBlank() || category.isBlank() || note.isBlank() || date.isBlank()) {
                        errorMessage = "Please fill in all fields."
                    } else if (amt == null || amt <= 0.0) {
                        errorMessage = "Please enter a valid amount."
                    } else {
                        val currentUser = DummyDataStore.currentUser
                        if (currentUser != null) {
                            viewModel.addExpense(
                                Expense(
                                    id = 0,
                                    amount = amt,
                                    category = category,
                                    note = note,
                                    isRecurring = isRecurring,
                                    date = date,
                                    username = currentUser.username
                                )
                            )
                            errorMessage = ""
                            onBack()
                        } else {
                            errorMessage = "You must be logged in to add an expense."
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save", color = Color.White)
            }

            // Back Button
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }

            // Error Message
            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red, fontSize = 14.sp)
            }
        }
    }
}