package com.example.myapplication.model

// 3. KẾ THỪA & ĐA HÌNH (TASK CỤ THỂ - TIME BOUND)
data class TimeBoundTask(
    val baseTitle: String,
    val baseDescription: String,
    val deadline: String
) : BaseTask(baseTitle, baseDescription) {

    override fun getDisplayDetails(): String {
        return "Hạn chót: $deadline | Loại: Quản lý thời gian"
    }
}

