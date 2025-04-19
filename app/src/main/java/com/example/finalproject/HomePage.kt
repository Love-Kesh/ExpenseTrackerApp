package com.example.finalproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun HomePage(viewModel: ExpenseViewModel, onNavigate: (String) -> Unit) {
    val scrollState = rememberScrollState()
    val allExpenses = viewModel.expenses

    val titleBackgroundColor = Color(0xFF4CAF50) // Smooth green
    val buttonColor = Color(0xFF2196F3) // Blue

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(titleBackgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Welcome ${DummyDataStore.currentUser?.username}",
                style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "All Past Expenses:",
            style = MaterialTheme.typography.titleMedium.copy(color = Color.Black),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                allExpenses.forEach { exp ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("${exp.date}", style = MaterialTheme.typography.bodyMedium)
                                Text("${exp.category}", style = MaterialTheme.typography.bodySmall)
                            }
                            Text("$${"%.2f".format(exp.amount)}", style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onNavigate("add") },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Add Expense", color = Color.White)
            }
            Button(
                onClick = { onNavigate("view") },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("View Expenses", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onNavigate("budgets") },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Set Budgets", color = Color.White)
            }
            Button(
                onClick = { onNavigate("reports") },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Reports", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { onNavigate("calendar") },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Recurring", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔒 Logout Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {
                    DummyDataStore.currentUser = null
                    DummyDataStore.expenses.clear()
                    DummyDataStore.budgets.clear()
                    onNavigate("login")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Logout", color = Color.White)
            }
        }
    }
}