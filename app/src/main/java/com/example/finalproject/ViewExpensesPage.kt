package com.example.finalproject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ViewExpensesPage(viewModel: ExpenseViewModel, onBack: () -> Unit) {
    val currentUser = DummyDataStore.currentUser
    val expenses = if (currentUser != null) {
        viewModel.expenses
    } else {
        emptyList()
    }

    // Define primary colors for styling
    val titleBackgroundColor = Color(0xFF4CAF50) // Smooth green
    val buttonColor = Color(0xFF2196F3) // Blue

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState()) // Enable scrolling for the page
    ) {
        // Title Box with Background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(titleBackgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 24.dp), // Increased padding to move title down
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Your Expenses",
                style = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Check if there are no expenses to show
        if (expenses.isEmpty()) {
            Text("No expenses found.", fontSize = 16.sp)
        } else {
            // Lazy Column to display expenses
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(expenses) { exp ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp), // Padding for spacing between cards
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp) // Add shadow
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

        Spacer(modifier = Modifier.height(16.dp))

        // Back Button
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text("Back", color = Color.White)
        }
    }
}
