package com.example.myapplication.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

// Data class chứa toàn bộ trạng thái cho luồng quên mật khẩu
data class ForgotPasswordUiState(
    val email: String = "",
    val verificationCode: String = "",
    val newPassword: String = "",
    val confirmPassword: String = ""
)

// Đây là một Shared ViewModel (ViewModel được dùng chung)
class ForgotPasswordViewModel : ViewModel() {

    private val _uiState = mutableStateOf(ForgotPasswordUiState())//ẩn mật khẩu
    val uiState: State<ForgotPasswordUiState> = _uiState// hiện email

    // --- Các hàm (event) để cập nhật state từ UI ---

    fun onEmailChange(newEmail: String) {
        _uiState.value = _uiState.value.copy(email = newEmail)
    }// dùng để lưu trạng thái khi nhập email

    fun onCodeChange(newCode: String) {
        _uiState.value = _uiState.value.copy(verificationCode = newCode)
    }

    fun onNewPasswordChange(newPass: String) {
        _uiState.value = _uiState.value.copy(newPassword = newPass)
    }

    fun onConfirmPasswordChange(newConfirm: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = newConfirm)
    }

    // Hàm để reset state khi luồng hoàn tất
    fun resetFlow() {
        _uiState.value = ForgotPasswordUiState()
    }
}