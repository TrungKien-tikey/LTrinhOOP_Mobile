package com.example.myapplication.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.*
sealed class OnboardingScreen(val index: Int) {
    object Welcome : OnboardingScreen(0)
    object Notification : OnboardingScreen(3)
}
class OnboardingViewModel : ViewModel() {
    var currentScreenIndex by mutableStateOf(OnboardingScreen.Welcome.index)
        private set

    // Khởi tạo các đối tượng OOP
    val sampleTasks = listOf(
        TimeBoundTask(
            baseTitle = "Sắp xếp lịch học",
            baseDescription = "Quản lý thời gian ưu tiên hàng ngày và hàng tuần.",
            deadline = "15/11/2025"
        ),

        )
    var finalTask by mutableStateOf(sampleTasks[0])

    fun navigateNext() {
        if (currentScreenIndex < OnboardingScreen.Notification.index) {
            currentScreenIndex++
        }
    }
    fun navigateBack() {
        if (currentScreenIndex > OnboardingScreen.Welcome.index) {
            currentScreenIndex--
        }
    }

    // Trả về đối tượng Model (Dùng cho Đa hình)
    fun getCurrentTaskToDisplay(): Task {
        return finalTask
    }
}

data class OnboardingData(
    val title: String,
    val description: String,
    val imageResId: Int,
    val isFinal: Boolean
)
