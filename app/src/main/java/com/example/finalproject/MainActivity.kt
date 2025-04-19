package com.example.finalproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject.ui.theme.FinalProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ExpenseApp()
            }
        }
    }
}

@Composable
fun ExpenseApp() {
    val navController = rememberNavController()
    val viewModel: ExpenseViewModel = viewModel()

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginPage(onLoginSuccess = { navController.navigate("home") },
                onNavigateToSignUp = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            SignUpPage(
                onSignUpSuccess = { navController.navigate("home") },
                onNavigateToLogin = { navController.popBackStack() } // or .navigate("login") if you prefer
            )
        }
        composable("home") { HomePage(viewModel, onNavigate = { navController.navigate(it) }) }
        composable("add") { AddExpensePage(viewModel) { navController.popBackStack() } }
        composable("view") { ViewExpensesPage(viewModel) { navController.popBackStack() } }
        composable("budgets") { SetBudgetsPage { navController.popBackStack() } }
        composable("reports") { ExpenseReportsPage(viewModel) { navController.popBackStack() } }
        composable("calendar") { RecurringPage(viewModel) { navController.popBackStack() } }
    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinalProjectTheme { }
}