//package com.example.attendencescanner
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.Toast
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import com.example.attendencescanner.data.database.AppDatabase
//import com.example.attendencescanner.data.model.Attendance
//import com.example.attendencescanner.utils.Prefs
//import com.journeyapps.barcodescanner.CaptureActivity
//import com.journeyapps.barcodescanner.ScanContract
//import com.journeyapps.barcodescanner.ScanOptions
//import kotlinx.coroutines.launch
//import org.json.JSONObject
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
//import android.Manifest
//
//import com.example.attendencescanner.ui.history.ActivityListActivity
//
//
//class StudentDashboardActivity : AppCompatActivity() {
//    private val cameraPermissionLauncher = registerForActivityResult(RequestPermission()) { granted ->
//        if (granted) startScan() else Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
//    }
//    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
//        if (result.contents != null) {
//            try {
//                val json = JSONObject(result.contents)
//                val className = json.optString("className")
//                var date = json.optString("date")
//                var time = json.optString("time")
//                if (date.isEmpty() || time.isEmpty()) {
//                    val now = Date()
//                    date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(now)
//                    time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(now)
//                }
//                val studentId = Prefs.getUserId(this)
//                if (studentId <= 0) {
//                    Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show()
//                    return@registerForActivityResult
//                }
//                lifecycleScope.launch {
//                    val db = AppDatabase.getInstance(this@StudentDashboardActivity)
//                    db.attendanceDao().insert(
//                        Attendance(
//                            studentId = studentId,
//                            className = className,
//                            date = date,
//                            time = time
//                        )
//                    )
//                    runOnUiThread {
//                        Toast.makeText(this@StudentDashboardActivity, "Attendance marked", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } catch (_: Exception) {
//                Toast.makeText(this, "Invalid QR", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_student_dashboard)
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        val scanButton = findViewById<Button>(R.id.btnScanQR)
//        val viewHistory = findViewById<Button>(R.id.btnViewHistoryStudent)
//        val logoutButton = findViewById<Button>(R.id.btnLogout)
//        scanButton.setOnClickListener {
//            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
//        }
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
//
//    }
//
//    private fun startScan() {
//        val options = ScanOptions().apply {
//            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
//            setPrompt("Scan class QR")
//            setBeepEnabled(true)
//            setOrientationLocked(true)
//            setCaptureActivity(CaptureActivity::class.java)
//        }
//        barcodeLauncher.launch(options)
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        finish()
//        return true
//    }
//}
//
//



//package com.example.attendencescanner
//
//import android.Manifest
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Button
//import android.widget.TextView
//import android.widget.Toast
//import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import com.example.attendencescanner.data.database.AppDatabase
//import com.example.attendencescanner.data.model.Attendance
//import com.example.attendencescanner.data.model.TimeTableItem
//import com.example.attendencescanner.ui.history.ActivityListActivity
//import com.example.attendencescanner.utils.Prefs
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import com.journeyapps.barcodescanner.CaptureActivity
//import com.journeyapps.barcodescanner.ScanContract
//import com.journeyapps.barcodescanner.ScanOptions
//import kotlinx.coroutines.launch
//import org.json.JSONObject
//import java.text.SimpleDateFormat
//import java.util.*
//
//class StudentDashboardActivity : AppCompatActivity() {
//
//    private val cameraPermissionLauncher = registerForActivityResult(RequestPermission()) { granted ->
//        if (granted) startScan() else Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
//    }
//
//    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
//        if (result.contents != null) {
//            try {
//                val json = JSONObject(result.contents)
//                val className = json.optString("className")
//                var date = json.optString("date")
//                var time = json.optString("time")
//                if (date.isEmpty() || time.isEmpty()) {
//                    val now = Date()
//                    date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(now)
//                    time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(now)
//                }
//                val studentId = Prefs.getUserId(this)
//                if (studentId <= 0) {
//                    Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show()
//                    return@registerForActivityResult
//                }
//                lifecycleScope.launch {
//                    val db = AppDatabase.getDatabase(this@StudentDashboardActivity)
//                    db.attendanceDao().insert(
//                        Attendance(
//                            studentId = studentId,
//                            className = className,
//                            date = date,
//                            time = time
//                        )
//                    )
//                    runOnUiThread {
//                        Toast.makeText(this@StudentDashboardActivity, "Attendance marked", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            } catch (_: Exception) {
//                Toast.makeText(this, "Invalid QR", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_student_dashboard)
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        val scanButton = findViewById<Button>(R.id.btnScanQR)
//        val viewHistory = findViewById<Button>(R.id.btnViewHistoryStudent)
//        val logoutButton = findViewById<Button>(R.id.btnLogout)
//        val smartActivityButton = findViewById<Button>(R.id.btnSmartActivity)
//        val viewTimetableButton = findViewById<Button>(R.id.btnViewFullTimetable)
//        val timetableText = findViewById<TextView>(R.id.textTimetablePreview)
//
//        // Load timetable preview
//        loadTimeTablePreview(timetableText)
//
//        // ---- Button Actions ----
//        scanButton.setOnClickListener {
//            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
//        }
//
//        viewHistory.setOnClickListener {
//            startActivity(Intent(this, AttendanceHistoryActivity::class.java))
//        }
//
//        smartActivityButton.setOnClickListener {
//            val intent = Intent(this, ActivityListActivity::class.java)
//            startActivity(intent)
//        }
//
//        viewTimetableButton.setOnClickListener {
//            startActivity(Intent(this, TimeTableActivity::class.java))
//        }
//
//        logoutButton.setOnClickListener {
//            val intent = Intent(this, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//            finish()
//        }
//
//        // Optional: Smart Curriculum summary
//        lifecycleScope.launch {
//            val dao = AppDatabase.getDatabase(this@StudentDashboardActivity).curriculumActivityDao()
//            dao.getAllActivities().observe(this@StudentDashboardActivity) { list ->
//                val count = list.size
//                Toast.makeText(
//                    this@StudentDashboardActivity,
//                    "You have $count Smart Curriculum Activities",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//    }
//
//    private fun startScan() {
//        val options = ScanOptions().apply {
//            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
//            setPrompt("Scan class QR")
//            setBeepEnabled(true)
//            setOrientationLocked(true)
//            setCaptureActivity(CaptureActivity::class.java)
//        }
//        barcodeLauncher.launch(options)
//    }
//
//    private fun loadTimeTablePreview(textView: TextView) {
//        val sharedPreferences = getSharedPreferences("time_table_prefs", Context.MODE_PRIVATE)
//        val json = sharedPreferences.getString("timetable_data", null)
//        if (json != null) {
//            val type = object : TypeToken<MutableList<TimeTableItem>>() {}.type
//            val list: MutableList<TimeTableItem> = Gson().fromJson(json, type)
//
//            if (list.isNotEmpty()) {
//                val previewText = list.take(3).joinToString("\n") {
//                    "${it.subjectDay}: ${it.subjectName} (${it.subjectTime})"
//                }
//                textView.text = previewText
//            } else {
//                textView.text = "No timetable available yet."
//            }
//        } else {
//            textView.text = "No timetable available yet."
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        finish()
//        return true
//    }
//}


package com.example.attendencescanner

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import com.example.attendencescanner.data.database.AppDatabase
import com.example.attendencescanner.data.model.Attendance
import com.example.attendencescanner.data.model.TimeTableItem
import com.example.attendencescanner.ui.history.ActivityListActivity
import com.example.attendencescanner.utils.Prefs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.journeyapps.barcodescanner.CaptureActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class StudentDashboardActivity : AppCompatActivity() {

    private val cameraPermissionLauncher = registerForActivityResult(RequestPermission()) { granted ->
        if (granted) startScan() else Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show()
    }

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            try {
                val json = JSONObject(result.contents)
                val className = json.optString("className")
                var date = json.optString("date")
                var time = json.optString("time")

                if (date.isEmpty() || time.isEmpty()) {
                    val now = Date()
                    date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(now)
                    time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(now)
                }

                val studentId = Prefs.getUserId(this)
                if (studentId <= 0) {
                    Toast.makeText(this, "Please login again", Toast.LENGTH_SHORT).show()
                    return@registerForActivityResult
                }

                lifecycleScope.launch {
                    val db = AppDatabase.getDatabase(this@StudentDashboardActivity)
                    db.attendanceDao().insert(
                        Attendance(
                            studentId = studentId,
                            className = className,
                            date = date,
                            time = time
                        )
                    )
                    runOnUiThread {
                        Toast.makeText(this@StudentDashboardActivity, "Attendance marked", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (_: Exception) {
                Toast.makeText(this, "Invalid QR", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ✅ Match with your new CardView layout
        val cardScanAttendance: CardView = findViewById(R.id.cardScanAttendance)
        val cardAssignments: CardView = findViewById(R.id.cardAssignments)
        val cardTimeTable: CardView = findViewById(R.id.cardTimeTable)
        val cardHistory: CardView = findViewById(R.id.cardHistory)
        val btnLogout: Button = findViewById(R.id.btnLogout)


        // ---- Card Actions ----

        // 🔹 Scan Attendance
        cardScanAttendance.setOnClickListener {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        // 🔹 Assignments (Smart Activity)
        cardAssignments.setOnClickListener {
            startActivity(Intent(this, ActivityListActivity::class.java))
        }

        // 🔹 Timetable
        cardTimeTable.setOnClickListener {
            startActivity(Intent(this, TimeTableActivity::class.java))
        }

        // 🔹 Attendance History
        cardHistory.setOnClickListener {
            startActivity(Intent(this, AttendanceHistoryActivity::class.java))
        }

        // 🔹 Logout
        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        // Optional Smart Curriculum summary
        lifecycleScope.launch {
            val dao = AppDatabase.getDatabase(this@StudentDashboardActivity).curriculumActivityDao()
            dao.getAllActivities().observe(this@StudentDashboardActivity) { list ->
                val count = list.size
                Toast.makeText(
                    this@StudentDashboardActivity,
                    "You have $count Smart Curriculum Activities",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // (Optional) loadTimeTablePreview could be used for a preview card in future
    }

    private fun startScan() {
        val options = ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setPrompt("Scan class QR")
            setBeepEnabled(true)
            setOrientationLocked(true)
            setCaptureActivity(CaptureActivity::class.java)
        }
        barcodeLauncher.launch(options)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

