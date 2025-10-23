package com.example.myapplication.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.components.TaskDisplayCard
import com.example.myapplication.model.Task
import com.example.myapplication.viewmodel.*
import com.example.myapplication.R
@Composable
fun SmartTasksApp() {
    // Gọi ViewModel
    val viewModel: OnboardingViewModel = viewModel()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(Modifier.fillMaxSize()) {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(30.dp)
                    .background(Color.LightGray)
            )

            // Logic Navigation dùng AnimatedContent
            AnimatedContent(
                targetState = viewModel.currentScreenIndex,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally(animationSpec = tween(400)) { fullWidth -> fullWidth } + fadeIn(
                            tween(400)
                        ) togetherWith
                                slideOutHorizontally(animationSpec = tween(400)) { fullWidth -> -fullWidth } + fadeOut(
                            tween(400)
                        )
                    } else {
                        slideInHorizontally(animationSpec = tween(400)) { fullWidth -> -fullWidth } + fadeIn(
                            tween(400)
                        ) togetherWith
                                slideOutHorizontally(animationSpec = tween(400)) { fullWidth -> fullWidth } + fadeOut(
                            tween(400)
                        )
                    }
                },
                label = "Onboarding Animation"
            ) { screenIndex ->
                OnboardingPage(
                    screenIndex = screenIndex,
                    onNext = viewModel::navigateNext,
                    onBack = viewModel::navigateBack,
                    onGetStarted = {},
                    task = viewModel.getCurrentTaskToDisplay() // Lấy đối tượng OOP từ ViewModel
                )
            }
        }
    }
}

@Composable
fun OnboardingPage(
    screenIndex: Int,
    onNext: () -> Unit,
    onBack: () -> Unit,
    onGetStarted: () -> Unit,
    task: Task // Nhận đối tượng OOP chung
) {
    val pageData = remember(screenIndex) {
        when (screenIndex) {
            0 -> OnboardingData(
                "Chào mừng bạn đến với UTH SmartTasks",
                "Hãy bắt đầu hành trình quản lý công việc thông minh!",
                R.drawable.uth, // <-- THAY TÊN ẢNH TƯƠNG ỨNG
                isFinal = false
            )

            1 -> OnboardingData(
                "Easy Time Management",
                "Với việc quản lý ưu tiên hàng ngày, chúng tôi sẽ giúp bạn quản lý các trạng thái Task.",
                R.drawable.uth, // <-- THAY TÊN ẢNH TƯƠNG ỨNG
                isFinal = false
            )

            2 -> OnboardingData(
                "Increase Task Effectiveness",
                "Quản lý thời gian và xác định mục tiêu quan trọng giúp bạn cải thiện hiệu suất.",
                R.drawable.uth, // <-- THAY TÊN ẢNH TƯƠNG ỨNG
                isFinal = false
            )

            3 -> OnboardingData(
                "Reminder Notification",
                "Ưu điểm là nó đặt lời nhắc chính xác để bạn không quên lịch trình.",
                R.drawable.uth, // <-- THAY TÊN ẢNH TƯƠNG ỨNG
                isFinal = true
            )

            else -> OnboardingData("", "", R.drawable.uth, isFinal = false) // Ảnh mặc định
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(48.dp))

        // Hình ảnh thật
        Image(
            painter = painterResource(id = pageData.imageResId), // <-- Lấy ID ảnh từ pageData
            contentDescription = pageData.title, // Mô tả ảnh
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(24.dp)), // Giữ lại bo góc
            contentScale = ContentScale.Crop // Giúp ảnh lấp đầy khung hình
        )

        Spacer(Modifier.height(32.dp))

        Text(
            text = pageData.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = pageData.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(Modifier.height(48.dp))

        if (pageData.isFinal) {
            TaskDisplayCard(task = task,) // Dùng Component
            Spacer(Modifier.weight(1f))
        } else {
            Spacer(Modifier.weight(1f))
        }

        // --- Navigation Buttons ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (screenIndex > OnboardingScreen.Welcome.index) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            } else {
                Spacer(Modifier.size(48.dp))
            }

            Button(
                onClick = {
                    if (pageData.isFinal) {
                        onGetStarted()
                    } else {
                        onNext()
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Text(if (pageData.isFinal) "Get Started" else "Next", fontSize = 18.sp)
            }
        }
    }
}
