package com.example.finalproject

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ViewExpensesPage(viewModel: ExpenseViewModel, onBack: () -> Unit) {
    val allExpenses = viewModel.expenses // Get all expenses
    var expandedExpenseId by remember { mutableStateOf<Int?>(null) } // Track expanded expense card
    val buttonColor = Color(0xFF2196F3) // Blue

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Make the entire content scrollable
    ) {
        Text("All Expenses", style = MaterialTheme.typography.headlineMedium)

        // Display all expenses
        allExpenses.forEach { expense ->
            ExpenseItem(
                expense = expense,
                isExpanded = expandedExpenseId == expense.id,
                onClick = {
                    expandedExpenseId = if (expandedExpenseId == expense.id) null else expense.id
                }, // Toggle expansion
                onUpdate = { updatedAmount ->
                    viewModel.updateExpense(expense.id, updatedAmount.toDoubleOrNull() ?: 0.0)
                    expandedExpenseId = null // Close the expanded card after update
                },
                onDelete = {
                    viewModel.deleteExpense(expense.id)
                    expandedExpenseId = null // Close the expanded card after delete
                }
            )
        }
    }
}

@Composable
fun ExpenseItem(
    expense: Expense,
    isExpanded: Boolean,
    onClick: () -> Unit,
    onUpdate: (String) -> Unit,
    onDelete: () -> Unit
) {
    val buttonColor = Color(0xFF4CAF50) // Green
    var updatedAmount by remember { mutableStateOf(expense.amount.toString()) }

    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(bottom = if (isExpanded) 16.dp else 0.dp),
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
                    Text("${expense.date}", style = MaterialTheme.typography.bodyMedium)
                    Text("${expense.category}", style = MaterialTheme.typography.bodySmall)
                }
                Text("$${"%.2f".format(expense.amount)}", style = MaterialTheme.typography.bodyLarge)
            }
        }

        // Show editable fields and options if the card is expanded
        if (isExpanded) {
            Spacer(modifier = Modifier.height(8.dp))

            // Edit Amount
            OutlinedTextField(
                value = updatedAmount,
                onValueChange = { updatedAmount = it },
                label = { Text("Update Amount") },
                modifier = Modifier.fillMaxWidth()
            )

            // Save Button
            Button(
                onClick = { onUpdate(updatedAmount) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text("Save Changes", color = Color.White)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Delete Button
            Button(
                onClick = onDelete,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red) // Red for delete
            ) {
                Text("Delete", color = Color.White)
            }
        }
    }
}
