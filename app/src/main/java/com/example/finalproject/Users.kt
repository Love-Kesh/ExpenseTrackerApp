package com.example.finalproject

data class User(val username: String, val password: String)

data class Expense(
    val id: Int,
    var amount: Double,
    var category: String,
    var note: String = "",
    var isRecurring: Boolean = false,
    var date: String,
    val username: String
)

data class Budget(
    val category: String,
    var limit: Double,
    val username: String
)

// -------------------- Dummy Data --------------------
object DummyDataStore {
    val users = mutableListOf(
        User("alice", "pass123"),
        User("bob", "secure456"),
        User("carol", "mypassword")
    )

    val expenses = mutableListOf(
        // Alice's expenses
        Expense(
            id = 0,
            amount = 50.0,
            category = "Groceries",
            note = "Weekly shopping",
            isRecurring = true,
            date = "2025-04-01",
            username = "alice"
        ),
        Expense(
            id = 1,
            amount = 20.0,
            category = "Transport",
            note = "Bus fare",
            isRecurring = false,
            date = "2025-04-02",
            username = "alice"
        ),
        Expense(
            id = 2,
            amount = 30.0,
            category = "Entertainment",
            note = "Movie night",
            isRecurring = false,
            date = "2025-04-05",
            username = "alice"
        ),
        Expense(
            id = 3,
            amount = 15.0,
            category = "Dining",
            note = "Coffee with friend",
            isRecurring = false,
            date = "2025-04-06",
            username = "alice"
        ),
        Expense(
            id = 4,
            amount = 75.0,
            category = "Groceries",
            note = "Monthly shopping",
            isRecurring = true,
            date = "2025-04-10",
            username = "alice"
        ),

        // Bob's expenses
        Expense(
            id = 5,
            amount = 100.0,
            category = "Entertainment",
            note = "Concert tickets",
            isRecurring = false,
            date = "2025-04-05",
            username = "bob"
        ),
        Expense(
            id = 6,
            amount = 50.0,
            category = "Transport",
            note = "Train fare",
            isRecurring = false,
            date = "2025-04-07",
            username = "bob"
        ),
        Expense(
            id = 7,
            amount = 20.0,
            category = "Dining",
            note = "Dinner with colleagues",
            isRecurring = false,
            date = "2025-04-08",
            username = "bob"
        ),
        Expense(
            id = 8,
            amount = 150.0,
            category = "Entertainment",
            note = "Weekend getaway",
            isRecurring = false,
            date = "2025-04-09",
            username = "bob"
        ),

        // Carol's expenses
        Expense(
            id = 9,
            amount = 35.0,
            category = "Dining",
            note = "Lunch with friends",
            isRecurring = false,
            date = "2025-04-07",
            username = "carol"
        ),
        Expense(
            id = 10,
            amount = 50.0,
            category = "Groceries",
            note = "Weekly grocery shopping",
            isRecurring = true,
            date = "2025-04-08",
            username = "carol"
        ),
        Expense(
            id = 11,
            amount = 25.0,
            category = "Entertainment",
            note = "Movie tickets",
            isRecurring = false,
            date = "2025-04-10",
            username = "carol"
        ),
        Expense(
            id = 12,
            amount = 40.0,
            category = "Dining",
            note = "Lunch with family",
            isRecurring = false,
            date = "2025-04-12",
            username = "carol"
        ),
        Expense(
            id = 13,
            amount = 30.0,
            category = "Transport",
            note = "Taxi fare",
            isRecurring = false,
            date = "2025-04-14",
            username = "carol"
        )
    )

    val budgets = mutableListOf(
        Budget(category = "Groceries", limit = 200.0, username = "alice"),
        Budget(category = "Entertainment", limit = 150.0, username = "bob"),
        Budget(category = "Dining", limit = 100.0, username = "carol")
    )

    var currentUser: User? = null
    var expenseIdCounter = expenses.size

    // Add Expense
    fun addExpense(expense: Expense) {
        expenses.add(expense.copy(id = expenseIdCounter++))
    }

    // Edit Expense (this function is for modifying an existing expense)
    fun editExpense(id: Int, newAmount: Double) {
        val index = expenses.indexOfFirst { it.id == id }
        if (index != -1) {
            val oldExpense = expenses[index]
            val updatedExpense = oldExpense.copy(amount = newAmount)
            expenses[index] = updatedExpense
        }
    }

    // Delete Expense
    fun deleteExpense(id: Int) {
        expenses.removeAll { it.id == id }
    }

    // Clear All Expenses and Budgets for the current user
    fun clearAll() {
        if (currentUser != null) {
            // Remove all expenses and budgets for the current user
            expenses.removeAll { it.username == currentUser!!.username }
            budgets.removeAll { it.username == currentUser!!.username }
        }
    }

    // Login
    fun login(username: String, password: String): Boolean {
        val user = users.find { it.username == username && it.password == password }
        return if (user != null) {
            currentUser = user
            true
        } else {
            false
        }
    }

    // Logout
    fun logout() {
        currentUser = null
    }

    // Get Expenses for Current User
    fun getUserExpenses(): List<Expense> {
        return currentUser?.let { user ->
            expenses.filter { it.username == user.username }
        } ?: emptyList()
    }

    // Get Budgets for Current User
    fun getUserBudgets(): List<Budget> {
        return currentUser?.let { user ->
            budgets.filter { it.username == user.username }
        } ?: emptyList()
    }
}