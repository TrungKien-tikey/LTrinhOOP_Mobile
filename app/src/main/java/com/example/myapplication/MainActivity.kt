package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.myapplication.navigation.AppNavigation
import com.example.myapplication.screens.LibraryManagementApp
import com.example.myapplication.screens.SmartTasksApp
import com.example.myapplication.ui.theme.MyApplicationTheme

// Giả định SmartTasksApp.kt nằm trong cùng package với MainActivity.
// Nếu file SmartTasksApp.kt nằm trong một package khác (ví dụ: .ui.main), bạn cần import nó.
// import com.example.myapplication.ui.main.SmartTasksApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Thiết lập giao diện người dùng bằng Jetpack Compose
        setContent {
            // Đặt tên Theme của bạn tại đây
            // Giả sử tên theme là 'MyApplicationTheme'
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Gọi hàm Composable chính chứa toàn bộ logic Onboarding và OOP
                    //SmartTasksApp()
                    //LibraryManagementApp()
                    AppNavigation()
                }
            }
        }
    }
}


