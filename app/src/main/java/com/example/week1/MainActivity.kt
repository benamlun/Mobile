package com.example.week1

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.week1.ui.theme.Week1Theme

class MainActivity : ComponentActivity() {

    // Khai báo biến để kết nối với WordCountService
    private var wordCountService: WordCountService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as WordCountService.LocalBinder
            wordCountService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
            wordCountService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WordCountScreen(
                        modifier = Modifier.padding(innerPadding),
                        onCountWords = { text ->
                            // Gọi hàm đếm từ từ Service nếu đã kết nối thành công
                            if (isBound && wordCountService != null) {
                                wordCountService!!.countWords(text)
                            } else {
                                0
                            }
                        }
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind vào WordCountService khi Activity bắt đầu chạy
        Intent(this, WordCountService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        // Unbind khi Activity dừng để giải phóng tài nguyên
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }
}

@Composable
fun WordCountScreen(modifier: Modifier = Modifier, onCountWords: (String) -> Int) {
    var textInput by remember { mutableStateOf("") }
    var wordCount by remember { mutableStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ứng Dụng Đếm Số Từ", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = textInput,
            onValueChange = {
                textInput = it
                wordCount = onCountWords(it)
            },
            label = { Text("Nhập nội dung vào đây...") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Số từ: $wordCount",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}