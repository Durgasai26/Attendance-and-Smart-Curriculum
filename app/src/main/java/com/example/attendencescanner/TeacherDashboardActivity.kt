package com.example.attendencescanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.attendencescanner.ui.history.ActivityListActivity
import com.example.attendencescanner.ui.history.CreateEditActivity

class TeacherDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_dashboard)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val cardAttendance = findViewById<CardView>(R.id.cardAttendance)
        val cardAssignments = findViewById<CardView>(R.id.cardAssignments)
        val cardTimeTable = findViewById<CardView>(R.id.cardTimeTable)
        val cardHistory = findViewById<CardView>(R.id.cardHistory)
        val logoutButton = findViewById<Button>(R.id.btnLogout)


        cardAttendance.setOnClickListener {
            startActivity(Intent(this, AttendanceActivity::class.java))
        }


        cardAssignments.setOnClickListener {
            startActivity(Intent(this, ActivityListActivity::class.java))
        }


        cardTimeTable.setOnClickListener {
            startActivity(Intent(this, TimeTableActivity::class.java))
        }

        cardHistory.setOnClickListener {
            startActivity(Intent(this, AttendanceHistoryActivity::class.java))
        }

        logoutButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}




//package com.example.attendencescanner
//
//import android.content.Intent
//import android.graphics.Bitmap
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//import com.example.attendencescanner.ui.history.ActivityListActivity
//import com.example.attendencescanner.ui.history.CreateEditActivity
//import com.example.attendencescanner.data.model.TimeTableItem
//
//import com.google.zxing.BarcodeFormat
//import com.google.zxing.qrcode.QRCodeWriter
//
//class TeacherDashboardActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_teacher_dashboard)
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        val classNameInput = findViewById<EditText>(R.id.inputClassName)
//        val generateButton = findViewById<Button>(R.id.btnGenerateQR)
//        val qrImage = findViewById<ImageView>(R.id.imageQR)
//        val viewHistory = findViewById<Button>(R.id.btnViewHistory)
//        val btnGiveAssignment = findViewById<Button>(R.id.btnGiveAssignment)
//        val btnViewAssignments = findViewById<Button>(R.id.btnViewAssignments)
//        val logoutButton = findViewById<Button>(R.id.btnLogout)
//        val timeTableButton = findViewById<Button>(R.id.btntimeTable)
//
//        // 🟩 QR generation for Attendance
//        generateButton.setOnClickListener {
//            val data = "{\"className\":\"${classNameInput.text}\"}"
//            val writer = QRCodeWriter()
//            val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
//            val width = bitMatrix.width
//            val height = bitMatrix.height
//            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
//            for (x in 0 until width) {
//                for (y in 0 until height) {
//                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
//                }
//            }
//            qrImage.setImageBitmap(bmp)
//        }
//
//        // 🟦 View attendance history
//        viewHistory.setOnClickListener {
//            startActivity(Intent(this, AttendanceHistoryActivity::class.java))
//        }
//
//        // 🟧 Give new assignment
//        btnGiveAssignment.setOnClickListener {
//            val intent = Intent(this, CreateEditActivity::class.java)
//            startActivity(intent)
//        }
//
//        // 🟨 View all assignments (Smart Curriculum list)
//        btnViewAssignments.setOnClickListener {
//            val intent = Intent(this, ActivityListActivity::class.java)
//            startActivity(intent)
//        }
//
//        // 🔴 Logout
//        logoutButton.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            finish()
//        }
//
//        timeTableButton.setOnClickListener {
//            val intent = Intent(this, TimeTableActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        finish()
//        return true
//    }
//}


//package com.example.attendencescanner
//
//import android.os.Bundle
//import android.content.Intent
//import android.widget.Button
//import android.widget.EditText
//import android.widget.ImageView
//import androidx.appcompat.app.AppCompatActivity
//import com.google.zxing.BarcodeFormat
//import com.google.zxing.qrcode.QRCodeWriter
//
//class TeacherDashboardActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_teacher_dashboard)
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        val classNameInput = findViewById<EditText>(R.id.inputClassName)
//        val generateButton = findViewById<Button>(R.id.btnGenerateQR)
//        val qrImage = findViewById<ImageView>(R.id.imageQR)
//        val viewHistory = findViewById<Button>(R.id.btnViewHistory)
//        val logoutButton = findViewById<Button>(R.id.btnLogout)
//
//        generateButton.setOnClickListener {
//            val data = "{\"className\":\"${classNameInput.text}\"}"
//            val writer = QRCodeWriter()
//            val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)
//            val width = bitMatrix.width
//            val height = bitMatrix.height
//            val bmp = android.graphics.Bitmap.createBitmap(width, height, android.graphics.Bitmap.Config.RGB_565)
//            for (x in 0 until width) {
//                for (y in 0 until height) {
//                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
//                }
//            }
//            qrImage.setImageBitmap(bmp)
//        }
//
//        viewHistory.setOnClickListener {
//            startActivity(Intent(this, AttendanceHistoryActivity::class.java))
//        }
//
//        logoutButton.setOnClickListener {
//            // Clear user session/preferences if needed
//            // Navigate back to login activity
//            val intent = Intent(this, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            finish()
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        finish()
//        return true
//    }
//}
//
//
