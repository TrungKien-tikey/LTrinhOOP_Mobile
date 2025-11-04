package com.example.myapplication.screens

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException

@Composable
fun LoginScreen(authViewModel: AuthViewModel, onLoginSuccess: () -> Unit) {
    val context = LocalContext.current

    // Launcher để nhận kết quả từ Google Sign-In
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                // Gọi ViewModel để xác thực với Firebase
                authViewModel.firebaseAuthWithGoogle(
                    idToken = account.idToken!!,
                    onSuccess = {
                        Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
                        onLoginSuccess() // Gọi callback để chuyển màn hình
                    },
                    onError = { errorMsg ->
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
                    }
                )
            } catch (e: ApiException) {
                Toast.makeText(context, "Đăng nhập Google thất bại: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Giao diện (Mô phỏng theo ảnh)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))
        Image(
            painter = painterResource(id = R.drawable.uthh), // Bạn cần có logo này
            contentDescription = "UTH Logo",
            modifier = Modifier.size(120.dp)
        )
        Text("SmartTasks", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
        Text("A simple and efficient todo app", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(64.dp))
        Text("Welcome", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Ready to explore? Log in to get started.", fontSize = 14.sp, modifier = Modifier.padding(top = 8.dp))

        // Đẩy nút xuống dưới cùng
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                // Khi bấm nút -> Mở cửa sổ chọn tài khoản của Google
                googleSignInLauncher.launch(authViewModel.googleSignInClient.signInIntent)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text("G SIGN IN WITH GOOGLE")
        }
    }
}