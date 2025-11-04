package com.example.myapplication.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.myapplication.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(authViewModel: AuthViewModel, onLogoutSuccess: () -> Unit) {
    val user by authViewModel.user.collectAsState()
    val context = LocalContext.current

    if (user == null) {
        // Trường hợp hiếm gặp (user bị null), tự động gọi logout để về login
        onLogoutSuccess()
        return
    }

    // Giao diện (Mô phỏng theo ảnh)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Profile",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 32.dp)
        )

        // Ảnh đại diện
        AsyncImage(
            model = user?.photoUrl.toString(),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .padding(top = 24.dp)
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        // Thông tin
        ProfileInfoRow(label = "Name", value = user?.displayName ?: "No Name")
        ProfileInfoRow(label = "Email", value = user?.email ?: "No Email")
        ProfileInfoRow(label = "Date of Birth", value = "Not provided") // Google Auth ko cấp DOB

        Spacer(modifier = Modifier.weight(1f)) // Đẩy nút xuống

        Button(
            onClick = {
                authViewModel.signOut {
                    Toast.makeText(context, "Đã đăng xuất", Toast.LENGTH_SHORT).show()
                    onLogoutSuccess() // Gọi callback để chuyển về Login
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            Text("Back") // Nút này giờ là "Đăng xuất"
        }
    }
}

// Composable nhỏ để tái sử dụng cho các dòng thông tin
@Composable
private fun ProfileInfoRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        Text(text = label, fontSize = 12.sp)
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = androidx.compose.ui.graphics.Color.Black
        )
    }
}