package com.example.finalproject

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation

@Composable
fun SignUpPage(onSignUpSuccess: () -> Unit, onNavigateToLogin: () -> Unit) {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }

    val titleBackgroundColor = Color(0xFF4CAF50) // Smooth green
    val buttonColor = Color(0xFF2196F3) // Blue

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(titleBackgroundColor, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sign Up",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Username Input Field
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sign Up Button
        Button(
            onClick = {
                // Validate if fields are empty
                when {
                    username.isEmpty() -> error = "Username cannot be empty"
                    password.isEmpty() -> error = "Password cannot be empty"
                    DummyDataStore.users.any { it.username == username } -> error = "Username already exists"
                    else -> {
                        // Register new user
                        val newUser = User(username, password)
                        DummyDataStore.users.add(newUser)
                        DummyDataStore.currentUser = newUser
                        error = ""
                        onSignUpSuccess()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
        ) {
            Text("Sign Up", color = Color.White)
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Login Navigation Text
        Text(
            "Already have an account? Login",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF2196F3)),
            modifier = Modifier.clickable {
                onNavigateToLogin()
            }
        )

        // Display error message
        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(error, color = Color.Red, fontSize = 14.sp)
        }
    }
}