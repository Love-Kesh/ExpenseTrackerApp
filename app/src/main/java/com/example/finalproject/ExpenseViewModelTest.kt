package com.example.finalproject

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ExpenseViewModelTest {

    private lateinit var viewModel: ExpenseViewModel

    @Before
    fun setup() {
        DummyDataStore.clearAll()
        DummyDataStore.currentUser = User("testUser", "password123")
        viewModel = ExpenseViewModel()
    }

    @Test
    fun addExpense_addsCorrectly() {
        val expense = Expense(
            id = 0,
            amount = 100.0,
            category = "Food",
            note = "Lunch",
            isRecurring = false,
            date = "2025-04-19",
            username = "testUser"
        )

        viewModel.addExpense(expense)

        assertEquals(1, viewModel.expenses.size)
        assertEquals("Food", viewModel.expenses[0].category)
    }

    @Test
    fun filterExpensesByUser_onlyReturnsForCurrentUser() {
        DummyDataStore.expenses.addAll(
            listOf(
                Expense(1, 20.0, "Food", "A", false, "2025-04-19", "testUser"),
                Expense(2, 50.0, "Travel", "B", false, "2025-04-19", "anotherUser")
            )
        )

        val filtered = viewModel.expenses
        assertEquals(1, filtered.size)
        assertEquals("testUser", filtered[0].username)
    }
}
