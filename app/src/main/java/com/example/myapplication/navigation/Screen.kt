package com.example.myapplication.navigation

// Sealed class để định nghĩa các route (đường dẫn) của ứng dụng, giúp
// Giúp điều hướng và xử lý logic an toàn hơn
sealed class Screen(val route: String) {
    object ForgotPassword : Screen("forgot_password_screen")
    object Verification : Screen("verification_screen")
    object ResetPassword : Screen("reset_password_screen")
    object Confirm : Screen("confirm_screen")
}