package com.example.finalproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SetBudgetsPage(onBack: () -> Unit) {
    val currentUser = DummyDataStore.currentUser
    if (currentUser == null) {
        Text(
            "Please log in to set budgets",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    var category by remember { mutableStateOf("") }
    var limit by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFFE3F2FD) // Light blue
    val titleBackground = Color(0xFF81D4FA) // Sky blue
    val buttonColor = Color(0xFF4CAF50)     // Green
    val cardColor = Color.White

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(titleBackground, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Set Budgets",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Logged in as: ${currentUser.username}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = limit,
                    onValueChange = { limit = it },
                    label = { Text("Limit (e.g. 200.00)") },
                    modifier = Modifier.fillMaxWidth()
                )

                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(top = 4.dp))
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val parsedLimit = limit.toDoubleOrNull()
                        if (category.isBlank() || limit.isBlank() || parsedLimit == null || parsedLimit < 0) {
                            errorMessage = "Please enter valid category and limit."
                        } else {
                            DummyDataStore.budgets.removeAll {
                                it.category.equals(category, ignoreCase = true) && it.username == currentUser.username
                            }
                            DummyDataStore.budgets.add(Budget(category.trim(), parsedLimit, currentUser.username))
                            errorMessage = ""
                            category = ""
                            limit = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
                ) {
                    Text("Set Budget", color = Color.White)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (DummyDataStore.budgets.any { it.username == currentUser.username }) {
                    Text("Your Budgets:", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(8.dp))

                    DummyDataStore.budgets.filter { it.username == currentUser.username }.forEach {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .background(cardColor, shape = RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(it.category, style = MaterialTheme.typography.bodyLarge)
                                Text("$${"%.2f".format(it.limit)}", style = MaterialTheme.typography.bodyLarge)
                            }
                        }
                    }
                } else {
                    Text("No budgets set yet.", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                }
            }

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Back", color = Color.White)
            }
        }
    }
}
