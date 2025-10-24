package com.example.myapplication.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.Book
import com.example.myapplication.model.Student
import com.example.myapplication.model.LibraryItem

// Data class chứa toàn bộ trạng thái (state) của màn hình
data class LibraryUiState(
    // --- Trạng thái dữ liệu gốc ---
    val allStudents: List<Student> = emptyList(),
    val allBooks: List<LibraryItem> = emptyList(), // <-- Dùng Interface (Trừu tượng)

    // --- Trạng thái tương tác của UI ---
    val studentNameInput: String = "Nguyen Van A", // Nội dung trong ô nhập liệu
    val currentStudent: Student? = null, // Sinh viên đang được chọn

    // Map<Student ID, Set<Book ID>>
    // Ví dụ: Map["student_1" to Set("book_1", "book_2")]
    // Dùng để lưu vết sinh viên nào đang mượn sách nào
    val borrowingMap: Map<String, Set<String>> = emptyMap()
)

class LibraryViewModel : ViewModel() {

    // _uiState là biến nội bộ (private), có thể thay đổi
    private val _uiState = mutableStateOf(LibraryUiState())
    // uiState là biến công khai (public), chỉ có thể đọc (read-only)
    val uiState: State<LibraryUiState> = _uiState

    // Khối init được gọi khi ViewModel được tạo lần đầu
    init {
        // Tạo danh sách sinh viên mẫu
        val studentA = Student(id = "sv_a", fullName = "Nguyen Van A")
        val studentB = Student(id = "sv_b", fullName = "Nguyen Van B")
        val studentC = Student(id = "sv_c", fullName = "Nguyen Van C")
        val studentList = listOf(studentA, studentB, studentC)

        // Tạo danh sách sách mẫu (dùng lớp Book, nhưng lưu dưới dạng LibraryItem)
        val book1 = Book(id = "book_01", name = "Book 01", author = "Auth A")
        val book2 = Book(id = "book_02", name = "Book 02", author = "Auth B")
        val bookList: List<LibraryItem> = listOf(book1, book2) // <-- Tính đa hình

        // Tạo dữ liệu mượn sách mẫu (dựa theo hình ảnh)
        val initialBorrowingMap = mapOf(
            studentA.id to setOf(book1.id, book2.id), // SV A mượn 2 sách
            studentB.id to setOf(book1.id)           // SV B mượn 1 sách
            // SV C không có trong map, nghĩa là mượn 0 sách
        )

        // Cập nhật UiState ban đầu
        _uiState.value = LibraryUiState(
            allStudents = studentList,
            allBooks = bookList,
            studentNameInput = studentA.fullName, // Tên hiển thị trên TextField
            currentStudent = studentA,         // Sinh viên đang được chọn
            borrowingMap = initialBorrowingMap
        )
    }

    // Hàm này được gọi khi nội dung TextField thay đổi
    fun onStudentNameChange(newName: String) {
        _uiState.value = _uiState.value.copy(studentNameInput = newName)
    }

    // Hàm này được gọi khi nhấn nút "Change"
    fun onChangeStudent() {
        // Tìm sinh viên trong danh sách allStudents có tên khớp với studentNameInput
        val foundStudent = _uiState.value.allStudents.find {
            it.fullName.equals(_uiState.value.studentNameInput, ignoreCase = true)
        }

        // Cập nhật sinh viên đang được chọn
        _uiState.value = _uiState.value.copy(currentStudent = foundStudent)
    }

    // Hàm này được gọi khi Checkbox của một cuốn Sách được nhấn
    fun onToggleBorrowBook(book: LibraryItem) {
        // Không làm gì nếu chưa chọn sinh viên
        val student = _uiState.value.currentStudent ?: return

        // Lấy danh sách ID sách mượn hiện tại của sinh viên này
        // (Nếu SV chưa mượn gì, tạo một Set rỗng)
        val currentBorrowedIds = _uiState.value.borrowingMap
            .getOrDefault(student.id, emptySet())
            .toMutableSet() // Chuyển sang mutable để thêm/xóa

        // Logic thêm/xóa
        if (book.id in currentBorrowedIds) {
            currentBorrowedIds.remove(book.id) // Nếu đã mượn -> trả sách (bỏ check)
        } else {
            currentBorrowedIds.add(book.id) // Nếu chưa mượn -> mượn sách (check)
        }

        // Cập nhật lại borrowingMap
        val newBorrowingMap = _uiState.value.borrowingMap.toMutableMap()
        newBorrowingMap[student.id] = currentBorrowedIds // Gán danh sách mới cho SV

        // Cập nhật state để UI tự động vẽ lại
        _uiState.value = _uiState.value.copy(borrowingMap = newBorrowingMap)
    }
}