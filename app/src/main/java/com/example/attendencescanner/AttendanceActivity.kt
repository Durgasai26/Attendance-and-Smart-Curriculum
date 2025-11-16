package com.example.attendencescanner

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import android.content.Intent

class AttendanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

        supportActionBar?.title = "Attendance"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val inputClassName = findViewById<EditText>(R.id.inputClassName)
        val btnGenerateQR = findViewById<Button>(R.id.btnGenerateQR)
        val imageQR = findViewById<ImageView>(R.id.imageQR)
        val btnViewHistory = findViewById<Button>(R.id.btnViewHistory)

        // 🟩 Generate QR code
        btnGenerateQR.setOnClickListener {
            val className = inputClassName.text.toString().trim()

            if (className.isEmpty()) {
                inputClassName.error = "Enter class name"
                return@setOnClickListener
            }

            val data = "{\"className\":\"$className\"}"
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(data, BarcodeFormat.QR_CODE, 512, 512)

            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
                }
            }

            imageQR.setImageBitmap(bmp)
            imageQR.visibility = View.VISIBLE
        }

        // 🟦 View history
        btnViewHistory.setOnClickListener {
            startActivity(Intent(this, AttendanceHistoryActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
