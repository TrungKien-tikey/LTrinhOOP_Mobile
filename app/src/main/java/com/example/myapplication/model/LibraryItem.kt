package com.example.myapplication.model

import java.util.UUID

// 1. TÍNH TRỪU TƯỢNG
interface LibraryItem {
    val id: String
    val name: String

    // Phương thức đa hình: Mỗi item tự biết cách hiển thị thông tin
    fun getDisplayInfo(): String
}

// 2. TÍNH KẾ THỪA (INHERITANCE - Giống TimeBoundTask kế thừa BaseTask)
data class Book(
    override val id: String = UUID.randomUUID().toString(),
    override val name: String,
    val author: String
) : LibraryItem { // Kế thừa từ interface LibraryItem

    // 3. TÍNH ĐA HÌNH (POLYMORPHISM - Ghi đè phương thức)
    override fun getDisplayInfo(): String {
        // Tự định nghĩa cách hiển thị thông tin
        return "$name (Auth: $author)"
    }
}