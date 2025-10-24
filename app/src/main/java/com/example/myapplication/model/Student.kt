package com.example.myapplication.model

import java.util.UUID

// Data class đơn giản để lưu trữ thông tin sinh viên
data class Student(
    val id: String = UUID.randomUUID().toString(),
    val fullName: String
)