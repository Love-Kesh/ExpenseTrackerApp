package com.example.finalproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecurringPage(viewModel: ExpenseViewModel, onBack: () -> Unit) {
    val recurring = viewModel.expenses.filter { it.isRecurring }
    val highSpending = viewModel.expenses.groupBy { it.date }.filterValues { dayList ->
        dayList.sumOf { it.amount } > 100.0
    }

    val backgroundColor = Color(0xFFF1F8E9) // Light green
    val cardColor = Color.White
    val headerColor = Color(0xFF81C784)     // Green
    val labelColor = Color(0xFF388E3C)      // Dark green

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
                        .background(headerColor, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Recurring Expenses",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (recurring.isNotEmpty()) {
                    Text("List of Recurring Expenses:", color = labelColor, fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(8.dp))

                    recurring.forEach {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(cardColor, shape = RoundedCornerShape(8.dp))
                                .padding(12.dp)
                                .padding(vertical = 4.dp)
                        ) {
                            Column {
                                Text("${it.date} - ${it.category}", style = MaterialTheme.typography.bodyLarge)
                                Text("Amount: \$${"%.2f".format(it.amount)}", fontSize = 14.sp, color = Color.DarkGray)
                            }
                        }
                    }
                } else {
                    Text("No recurring expenses found.", color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (highSpending.isNotEmpty()) {
                    Text("High Spending Days:", color = labelColor, fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(8.dp))

                    highSpending.forEach { (date, list) ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(cardColor, shape = RoundedCornerShape(8.dp))
                                .padding(12.dp)
                                .padding(vertical = 4.dp)
                        ) {
                            Text(
                                "$date: \$${"%.2f".format(list.sumOf { it.amount })}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                } else {
                    Text("No high-spending days detected.", color = Color.Gray)
                }
            }

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Back", color = Color.White)
            }
        }
    }
}