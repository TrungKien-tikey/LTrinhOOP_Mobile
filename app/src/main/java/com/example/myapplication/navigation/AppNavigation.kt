package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.*
import com.example.myapplication.viewmodel.ForgotPasswordViewModel

@Composable
fun AppNavigation() {
    // 1. Tạo NavController để quản lý việc điều hướng
    val navController = rememberNavController()

    // 2. Tạo Shared ViewModel
    // ViewModel này sẽ "sống" chừng nào AppNavigation còn "sống"
    // và được truyền cho TẤT CẢ các màn hình con.
    val sharedViewModel: ForgotPasswordViewModel = viewModel()

    // 3. Cài đặt NavHost (bộ điều phối)
    NavHost(
        navController = navController,
        startDestination = Screen.ForgotPassword.route // Màn hình bắt đầu
    ) {
        // --- Định nghĩa tất cả các màn hình ---

        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                navController = navController,
                viewModel = sharedViewModel // <-- Truyền ViewModel
            )
        }

        composable(route = Screen.Verification.route) {
            VerificationScreen(
                navController = navController,
                viewModel = sharedViewModel // <-- Truyền CÙNG ViewModel
            )
        }

        composable(route = Screen.ResetPassword.route) {
            ResetPasswordScreen(
                navController = navController,
                viewModel = sharedViewModel // <-- Truyền CÙNG ViewModel
            )
        }

        composable(route = Screen.Confirm.route) {
            ConfirmScreen(
                navController = navController,
                viewModel = sharedViewModel // <-- Truyền CÙNG ViewModel
            )
        }
    }
}

