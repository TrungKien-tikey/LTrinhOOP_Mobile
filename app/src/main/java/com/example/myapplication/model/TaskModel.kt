package com.example.myapplication.model
import java.util.UUID

// 1. TÍNH CHẤT TRỪU TƯỢNG (ABSTRACTION) & ĐA HÌNH (POLYMORPHISM)
interface Task {
    val id: String
    val title: String
    val description: String

    // Phương thức đa hình: Mỗi Task sẽ tự biết cách hiển thị chi tiết riêng
    fun getDisplayDetails(): String

}
// 2. TÍNH CHẤT KẾ THỪA (INHERITANCE) & ĐÓNG GÓI (ENCAPSULATION)
abstract class BaseTask(
    override val title: String,
    override val description: String,
) : Task {

    override val id: String = UUID.randomUUID().toString()

}
