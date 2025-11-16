package com.example.attendencescanner

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText as WEditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attendencescanner.data.database.AppDatabase
import com.example.attendencescanner.ui.history.AttendanceAdapter
import com.example.attendencescanner.utils.Prefs
import kotlinx.coroutines.launch

class AttendanceHistoryActivity : AppCompatActivity() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AttendanceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance_history)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recycler = findViewById(R.id.recyclerHistory)
        adapter = AttendanceAdapter()
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        if (Prefs.getRole(this)?.equals("Teacher", true) == true) {
            adapter.onEdit = { item -> showEditDialog(item.id, item.className, item.date, item.time, item.studentId) }
            adapter.onDelete = { item -> confirmDelete(item.id) }
        }

        val filterClass = findViewById<EditText>(R.id.filterClass)
        val filterDate = findViewById<EditText>(R.id.filterDate)

        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) { loadData(filterClass.text.toString(), filterDate.text.toString()) }
        }
        filterClass.addTextChangedListener(watcher)
        filterDate.addTextChangedListener(watcher)

        loadData(null, null)
    }

    private fun loadData(classFilter: String?, dateFilter: String?) {
        lifecycleScope.launch {
            val db = AppDatabase.getInstance(this@AttendanceHistoryActivity)
            val role = Prefs.getRole(this@AttendanceHistoryActivity)
            val classLike = classFilter?.takeIf { it.isNotBlank() }?.let { "%$it%" }
            val dateLike = dateFilter?.takeIf { it.isNotBlank() }?.let { "%$it%" }
            val data = if (role.equals("Teacher", ignoreCase = true))
                db.attendanceDao().getAllFiltered(classLike, dateLike)
            else
                db.attendanceDao().getByStudentFiltered(Prefs.getUserId(this@AttendanceHistoryActivity), classLike, dateLike)
            runOnUiThread { adapter.submit(data) }
        }
    }

    private fun showEditDialog(attendanceId: Long, className: String, date: String, time: String, studentId: Long) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_edit_attendance, null)
        val cls = view.findViewById<WEditText>(R.id.editClassName)
        val dt = view.findViewById<WEditText>(R.id.editDate)
        val tm = view.findViewById<WEditText>(R.id.editTime)
        cls.setText(className)
        dt.setText(date)
        tm.setText(time)
        AlertDialog.Builder(this)
            .setTitle("Edit Attendance")
            .setView(view)
            .setPositiveButton("Save") { _, _ ->
                lifecycleScope.launch {
                    val db = AppDatabase.getInstance(this@AttendanceHistoryActivity)
                    db.attendanceDao().update(
                        com.example.attendencescanner.data.model.Attendance(
                            id = attendanceId,
                            studentId = studentId,
                            className = cls.text.toString(),
                            date = dt.text.toString(),
                            time = tm.text.toString()
                        )
                    )
                    loadData(null, null)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun confirmDelete(attendanceId: Long) {
        AlertDialog.Builder(this)
            .setTitle("Delete Attendance")
            .setMessage("Are you sure you want to delete this entry?")
            .setPositiveButton("Delete") { _, _ ->
                lifecycleScope.launch {
                    val db = AppDatabase.getInstance(this@AttendanceHistoryActivity)
                    db.attendanceDao().delete(
                        com.example.attendencescanner.data.model.Attendance(
                            id = attendanceId,
                            studentId = 0,
                            className = "",
                            date = "",
                            time = ""
                        )
                    )
                    loadData(null, null)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}


