package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.LoginScreen
import com.example.myapplication.screens.ProfileScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.AuthViewModel


class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme { // Bọc trong theme của bạn

                val navController = rememberNavController()
                val user by authViewModel.user.collectAsState()

                // Xác định màn hình bắt đầu
                // Nếu user != null (đã đăng nhập) -> vào "profile"
                // Nếu user == null (chưa đăng nhập) -> vào "login"
                val startDestination = if (user != null) "profile" else "login"

                // Thiết lập bộ điều hướng
                NavHost(navController = navController, startDestination = startDestination) {

                    // Màn hình 1: Login
                    composable("login") {
                        LoginScreen(
                            authViewModel = authViewModel,
                            onLoginSuccess = {
                                // Chuyển sang Profile và xóa Login khỏi back stack
                                navController.navigate("profile") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    // Màn hình 2: Profile
                    composable("profile") {
                        ProfileScreen(
                            authViewModel = authViewModel,
                            onLogoutSuccess = {
                                // Chuyển về Login và xóa Profile khỏi back stack
                                navController.navigate("login") {
                                    popUpTo("profile") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}