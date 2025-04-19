package com.example.finalproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExpenseReportsPage(viewModel: ExpenseViewModel, onBack: () -> Unit) {
    val userExpenses = viewModel.expenses
    val categoryTotals = userExpenses
        .groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.amount } }

    val backgroundColor = Color(0xFFF1F8E9)
    val headerColor = Color(0xFF66BB6A)
    val cardColor = Color(0xFFFFFFFF)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(backgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(headerColor, shape = RoundedCornerShape(8.dp))
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Reports (Category Totals)",
                        style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Report Data
                if (categoryTotals.isEmpty()) {
                    Text(
                        "No data available.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                } else {
                    categoryTotals.forEach { (category, total) ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .background(cardColor, shape = RoundedCornerShape(8.dp))
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = category,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "$${"%.2f".format(total)}",
                                    style = MaterialTheme.typography.bodyLarge.copy(color = headerColor)
                                )
                            }
                        }
                    }
                }
            }

            // Back Button
            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = headerColor)
            ) {
                Text("Back", color = Color.White)
            }
        }
    }
}