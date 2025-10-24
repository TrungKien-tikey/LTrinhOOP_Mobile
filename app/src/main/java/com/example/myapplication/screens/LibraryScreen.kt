package com.example.myapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.model.LibraryItem
import com.example.myapplication.viewmodel.LibraryViewModel

// Hàm Composable chính, thay thế cho SmartTasksApp()
@Composable
fun LibraryManagementApp() {
    // 1. Gọi ViewModel
    val viewModel: LibraryViewModel = viewModel()

    // 2. Lấy state từ ViewModel
    // Dùng 'by' để tự động 'unwrap' giá trị .value
    val uiState by viewModel.uiState

    // 3. Truyền State và các hàm xử lý sự kiện (event handlers)
    // vào Composable con (là 1 Composable "Stateless")
    LibraryScreen(
        studentNameInput = uiState.studentNameInput,
        currentStudentId = uiState.currentStudent?.id,
        allBooks = uiState.allBooks,
        borrowingMap = uiState.borrowingMap,
        onStudentNameChange = viewModel::onStudentNameChange, // Truyền hàm
        onChangeStudent = viewModel::onChangeStudent,         // Truyền hàm
        onToggleBorrowBook = viewModel::onToggleBorrowBook    // Truyền hàm
    )
}

// Composable này "stateless" (không chứa trạng thái) và chỉ chịu trách nhiệm hiển thị UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryScreen(
    studentNameInput: String,
    currentStudentId: String?,
    allBooks: List<LibraryItem>,
    borrowingMap: Map<String, Set<String>>,
    onStudentNameChange: (String) -> Unit,
    onChangeStudent: () -> Unit,
    onToggleBorrowBook: (LibraryItem) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hệ thống quản lý thư viện") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        bottomBar = {
            // Đây là thanh Bottom Navigation Bar giả lập như trong hình
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ) {
                // Bạn có thể thêm các Icon Button ở đây
                Text("Quản lý", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                Text("DS Sách", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
                Text("Sinh Viên", modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Padding từ Scaffold
                .padding(16.dp),        // Padding cho nội dung
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Khu vực Sinh viên ---
            Text(
                text = "Sinh Viên",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = studentNameInput,
                    onValueChange = onStudentNameChange,
                    label = { Text("Tên Sinh Viên") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(Modifier.width(8.dp))
                Button(onClick = onChangeStudent) {
                    Text("Thay đổi")
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- Khu vực Danh sách sách ---
            Text(
                text = "DS Sách",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // Lấy danh sách ID sách mượn của sinh viên hiện tại
            val borrowedBookIds = borrowingMap.getOrDefault(currentStudentId, emptySet())

            if (currentStudentId == null) {
                // Trường hợp không tìm thấy sinh viên
                Text(
                    "Không tìm thấy sinh viên mượn sách.",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else if (borrowedBookIds.isEmpty()) {
                // Trường hợp như "Nguyen Van C" (mượn 0 sách)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.Gray.copy(alpha = 0.1f), // Màu nền xám mờ
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Bạn không có mượn sách. " +
                                "Hãy bấm thêm sách",
                        textAlign = TextAlign.Center,
                        color = Color.Gray
                    )
                }
            } else {
                // Hiển thị danh sách sách đã mượn (giống "Nguyen Van A")
                // Dùng LazyColumn để tối ưu hiệu năng (nếu danh sách dài)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color.Gray.copy(alpha = 0.1f),
                            shape = MaterialTheme.shapes.medium
                        ),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    // Lọc ra chỉ những sách mà sinh viên này đã mượn
                    val borrowedBooks = allBooks.filter { it.id in borrowedBookIds }

                    items(borrowedBooks) { book ->
                        // Gọi Composable tái sử dụng
                        LibraryItemRow(
                            item = book, // <-- Truyền đối tượng OOP
                            isChecked = true, // Luôn là true vì ta đã lọc
                            onCheckedChange = {
                                // Cho phép "trả sách" (bỏ check)
                                onToggleBorrowBook(book)
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Nút "Thêm" (Hiện tại chưa có logic, bạn có thể tự thêm)
            Button(
                onClick = { /* TODO: Mở màn hình thêm sách mới */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Thêm", fontSize = 18.sp)
            }
        }
    }
}

/**
 * Composable tái sử dụng, tương tự 'TaskDisplayCard.kt'
 * Nó nhận một interface 'LibraryItem' -> Đây là tính Đa hình!
 */
@Composable
fun LibraryItemRow(
    item: LibraryItem, // <-- Nhận interface (Trừu tượng)
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White) // Nền trắng
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = onCheckedChange
            )
            Spacer(Modifier.width(8.dp))

            // **TÍNH ĐA HÌNH (POLYMORPHISM) TẠI ĐÂY!**
            // UI không cần biết đây là Sách hay Tạp chí,
            // nó chỉ gọi hàm getDisplayInfo().
            Text(
                text = item.getDisplayInfo(), // <-- Gọi hàm của interface
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}