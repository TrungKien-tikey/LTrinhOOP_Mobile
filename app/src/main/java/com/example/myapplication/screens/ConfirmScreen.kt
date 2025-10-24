package com.example.myapplication.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navigation.Screen
import com.example.myapplication.viewmodel.ForgotPasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel // nhận dữ liệu
) {
    // Màn hình này đọc data  được nhập từ Màn hình 1, 2, 3
    val uiState by viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Confirm") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.uthh),
                contentDescription = "UTH Logo",
                modifier = Modifier.size(100.dp)
            )
            Text(text = "SmartTasks", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(32.dp))

            Text(text = "Confirm", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "We are here to help you!",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(Modifier.height(16.dp))

            // --- Hiển thị Email (lấy từ Màn hình 1) ---
            OutlinedTextField(
                value = uiState.email,
                onValueChange = {},
                readOnly = true, // Chỉ đọc
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.verificationCode, // <-- Đọc từ ViewModel
                onValueChange = viewModel::onCodeChange, // <-- Ghi vào ViewModel
                label = { Text("Verification Code") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(16.dp))
            // --- Hiển thị Password (lấy từ Màn hình 3) ---
            OutlinedTextField(
                value = uiState.newPassword,
                onValueChange = {},
                readOnly = true, // Chỉ đọc
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation() // Ẩn pass
            )
            Spacer(Modifier.height(32.dp))

            // --- Nút điều hướng "Summit" ---
            Button(
                onClick = {
                    // **BẮT ĐẦU LOGIC QUAN TRỌNG**
                    // 1. Quay về màn hình đầu tiên
                    navController.popBackStack(
                        Screen.ForgotPassword.route,
                        inclusive = false
                    )
                    // 2. Xóa dữ liệu trong ViewModel
                    // Việc này đảm bảo lần sau khi người dùng
                    // vào luồng "Quên mật khẩu", các ô sẽ trống
                    viewModel.resetFlow()

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Summit")
            }
        }
    }
}

