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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navigation.Screen
import com.example.myapplication.viewmodel.ForgotPasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerificationScreen(
    navController: NavController,
    viewModel: ForgotPasswordViewModel // <-- Dùng *cùng* ViewModel
) {
    val uiState by viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Verification") },
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
            // --- Logo và Tiêu đề ---
            Image(
                painter = painterResource(id = R.drawable.uthh),
                contentDescription = "UTH Logo",
                modifier = Modifier.size(100.dp)
            )
            Text(text = "SmartTasks", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(32.dp))

            Text(text = "Verify Code", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Enter the code we just sent you on your registered Email.",
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(Modifier.height(16.dp))

            // --- Ô nhập Code ---
            OutlinedTextField(
                value = uiState.verificationCode, // <-- Đọc từ ViewModel
                onValueChange = viewModel::onCodeChange, // <-- Ghi vào ViewModel
                label = { Text("Verification Code") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(32.dp))

            // --- Nút điều hướng ---
            Button(
                onClick = {
                    navController.navigate(Screen.ResetPassword.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text("Next")
            }
        }
    }
}

