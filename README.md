BÁO CÁO - TUẦN 1


1. Các thành phần chính của Android (Android Components)

Android có 4 thành phần cơ bản cốt lõi (Core Components) cấu thành nên một ứng dụng:

- Activity: Đại diện cho một màn hình đơn lẻ có giao diện người dùng (UI). Đây là nơi người dùng tương tác trực tiếp (ví dụ: màn hình đăng nhập, màn hình danh sách).

- Service: Thành phần chạy ngầm thực hiện các tác vụ dài hạn mà không cần giao diện người dùng (ví dụ: phát nhạc ngầm, tải file lớn).

- Broadcast Receiver: Thành phần cho phép ứng dụng nhận các thông báo/sự kiện từ hệ thống hoặc từ các ứng dụng khác (ví dụ: thông báo pin yếu, kết nối mạng thay đổi).

- Content Provider: Quản lý và cung cấp quyền truy cập dữ liệu của ứng dụng cho các ứng dụng khác một cách bảo mật (ví dụ: truy cập danh bạ, thư viện ảnh).

* Code Example: Ví dụ về một Activity cơ bản bằng Kotlin
  package com.example.myapp
  import android.os.Bundle
  import android.widget.Button
  import android.widget.Toast
  import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_main)

        val btnClick = findViewById<Button>(R.id.btnClickMe)
        btnClick.setOnClickListener {
            Toast.makeText(this, "Xin chào Android!", Toast.LENGTH_SHORT).show()
        }
    }
}


2. Cấu trúc bộ khung của một dự án Android Studio

Khi bạn tạo một project mới dạng Android Project, cấu trúc thư mục chính (Android view) bao gồm các thành phần sau:

- manifests/AndroidManifest.xml: Tệp cấu hình quan trọng nhất của app. Nơi khai báo tên ứng dụng, icon, các quyền (permissions như internet, camera), và khai báo các thành phần như Activity, Service.

- java (hoặc kotlin + java): Chứa mã nguồn viết bằng Kotlin/Java. Thư mục này chia thành các package chứa Activity, Fragment, ViewModel, Model,...

- res (Resources): Chứa tất cả tài nguyên không phải mã nguồn:
    * drawable/: Chứa hình ảnh, vector assets, file shape XML.
    * layout/: Chứa các tệp XML thiết kế giao diện màn hình.
    * mipmap/: Chứa icon của ứng dụng cho các độ phân giải màn hình khác nhau.
    * values/: Chứa các tệp cấu hình giá trị như strings.xml (chuỗi văn bản), colors.xml (màu sắc), themes.xml (giao diện).

- Gradle Scripts: Chứa các file cấu hình xây dựng project (build.gradle.kts hoặc build.gradle), quản lý thư viện bên thứ ba (dependencies), cấu hình phiên bản SDK tối thiểu (minSdk) và mục tiêu (targetSdk).


3. Vòng đời của một ứng dụng Android (Activity Lifecycle)

Vòng đời của ứng dụng thường được thể hiện qua vòng đời của Activity. Các trạng thái (states) và phương thức gọi tương ứng gồm:

- Created (Khởi tạo - onCreate()): Activity bắt đầu được tạo. Đây là nơi bạn khởi tạo các biến, gọi setContentView() để nạp giao diện.

- Started (Bắt đầu - onStart()): Activity chuyển sang trạng thái chuẩn bị hiển thị lên màn hình nhưng chưa nhận tương tác từ người dùng.

- Resumed (Hoạt động - onResume()): Activity hiển thị hoàn toàn trên màn hình và người dùng có thể tương tác trực tiếp (đang ở foreground).

- Paused (Tạm dừng - onPause()): Activity bắt đầu mất tiêu điểm (focus) vì một Activity khác che một phần (ví dụ: xuất hiện một dialog bán trong suốt hoặc cuộc gọi đến).

- Stopped (Dừng hẳn - onStop()): Activity hoàn toàn bị khuất khỏi màn hình (ví dụ: người dùng bấm nút Home hoặc chuyển sang app khác).

- Destroyed (Hủy - onDestroy()): Activity bị hủy hoàn toàn khỏi bộ máy để giải phóng tài nguyên (do người dùng thoát hẳn app hoặc hệ thống thu hồi RAM).


4. Kiến trúc Android và các Design Patterns phổ biến

A. Kiến trúc tổng quan của hệ điều hành Android (Android Architecture)
Kiến trúc Android được chia thành 5 tầng chính từ dưới lên trên:
- Linux Kernel: Tầng đáy cùng, quản lý trình điều khiển thiết bị (drivers), bảo mật, quản lý tiến trình và quản lý bộ nhớ.
- Hardware Abstraction Layer (HAL): Cung cấp các giao diện chuẩn che giấu phần cứng bên dưới khỏi tầng framework bên trên.
- Android Runtime (ART) & Native Libraries: Bao gồm máy ảo ART (chạy mã ứng dụng) và các thư viện C/C++ cốt lõi (OpenGL, SQLite, SSL,...).
- Java API Framework: Các API cung cấp cho lập trình viên xây dựng ứng dụng (ActivityManager, LocationManager, View System,...).
- System Apps (Applications): Tầng ứng dụng trên cùng bao gồm danh bạ, trình duyệt, máy ảnh và các app do bạn tự viết.

B. Các Design Patterns phổ biến trong lập trình ứng dụng Android
Để tách biệt mã nguồn giao diện và xử lý logic, Android sử dụng các kiến trúc sau:
- MVC (Model - View - Controller): Tách dữ liệu (Model), giao diện (View) và điều phối (Controller). Trong Android truyền thống, Activity/Fragment thường đóng vai trò là Controller, dễ dẫn đến tình trạng "Massive Activity".
- MVP (Model - View - Presenter): Tách biệt View (Activity/Fragment chỉ làm nhiệm vụ hiển thị) và Presenter (nơi xử lý logic nghiệp vụ). View giao tiếp với Model thông qua Presenter bằng Interface.
- MVVM (Model - View - ViewModel) - Được Google khuyến nghị hiện nay:
  + Model: Chứa dữ liệu và logic dữ liệu (Repository, Room Database, Retrofit).
  + View: Activity/Fragment/Jetpack Compose, chỉ làm nhiệm vụ hiển thị giao diện và lắng nghe dữ liệu từ ViewModel.
  + ViewModel: Lưu trữ dữ liệu liên quan đến UI và sống sót qua các sự kiện xoay màn hình, giúp kết nối View và Model mượt mà thông qua LiveData hoặc StateFlow.