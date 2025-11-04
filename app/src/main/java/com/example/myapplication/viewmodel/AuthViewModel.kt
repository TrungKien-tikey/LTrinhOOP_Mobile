package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    val auth: FirebaseAuth = Firebase.auth
    val googleSignInClient: GoogleSignInClient

    // StateFlow để giữ trạng thái user (null = chưa đăng nhập)
    private val _user = MutableStateFlow(auth.currentUser)
    val user = _user.asStateFlow()

    init {
        // Cấu hình Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(application.applicationContext, gso)
    }

    // Hàm xác thực với Firebase
    fun firebaseAuthWithGoogle(idToken: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential).await() // Dùng await cho coroutine
                _user.value = auth.currentUser // Cập nhật trạng thái user
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Xác thực Firebase thất bại")
            }
        }
    }

    // Hàm đăng xuất
    fun signOut(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signOut()
                googleSignInClient.signOut().await()
                _user.value = null // Cập nhật trạng thái user
                onSuccess()
            } catch (e: Exception) {
                // Xử lý lỗi
            }
        }
    }
}