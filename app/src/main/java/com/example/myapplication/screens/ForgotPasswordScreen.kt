package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navigation.Screen
import com.example.myapplication.viewmodel.ForgotPasswordViewModel

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel // <-- Nhận Shared ViewModel
) {
    val uiState by viewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- Logo và Tiêu đề ---
        Image(
            painter = painterResource(id = R.drawable.uthh), //
            contentDescription = "UTH Logo",
            modifier = Modifier.size(100.dp)
        )
        Text(text = "SmartTasks", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(32.dp))

        Text(text = "Forget Password?", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Enter your Email, we will send you a verification code.",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(Modifier.height(16.dp))

        // --- Ô nhập Email ---
        OutlinedTextField(
            value = uiState.email, // <-- Đọc từ ViewModel
            onValueChange = viewModel::onEmailChange, // <-- Ghi vào ViewModel
            label = { Text("Your Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(Modifier.height(32.dp))

        // --- Nút điều hướng ---
        Button(
            onClick = {
                // Chỉ cần gọi navigate, không cần truyền data
                // vì data đã có trong Shared ViewModel
                navController.navigate(Screen.Verification.route)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Next")
        }
    }
}

