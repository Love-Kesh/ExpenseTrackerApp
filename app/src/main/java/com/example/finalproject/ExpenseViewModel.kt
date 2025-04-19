package com.example.finalproject

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ExpenseViewModel : ViewModel() {
    var expenses by mutableStateOf(emptyList<Expense>())
        private set

    init {
        refreshExpenses()
    }

    fun addExpense(expense: Expense) {
        DummyDataStore.addExpense(expense)
        refreshExpenses()
    }

    fun deleteExpense(id: Int) {
        DummyDataStore.deleteExpense(id)
        refreshExpenses()
    }

    // Refactored editExpense method
    fun updateExpense(id: Int, newAmount: Double) {
        // Using the existing method in DummyDataStore to edit the expense
        DummyDataStore.editExpense(id, newAmount)
        refreshExpenses()
    }

    private fun refreshExpenses() {
        val currentUser = DummyDataStore.currentUser?.username
        expenses = DummyDataStore.expenses.filter { it.username == currentUser }
    }
}