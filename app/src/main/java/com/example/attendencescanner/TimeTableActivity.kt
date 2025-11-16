//package com.example.attendencescanner
//
//import android.app.AlertDialog
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.attendencescanner.adapters.TimeTableAdapter
//import com.example.attendencescanner.data.model.TimeTableItem
//import com.google.android.material.floatingactionbutton.FloatingActionButton
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//
//class TimeTableActivity : AppCompatActivity() {
//
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: TimeTableAdapter
//    private val timeTableList = mutableListOf<TimeTableItem>()
//    private val PREFS_NAME = "time_table_prefs"
//    private val KEY_TIMETABLE = "timetable_data"
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_time_table)
//
//        recyclerView = findViewById(R.id.recyclerViewTimeTable)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        loadTimeTable()
//
//        adapter = TimeTableAdapter(timeTableList)
//        recyclerView.adapter = adapter
//
//        val btnAddSubject = findViewById<FloatingActionButton>(R.id.btnAddSubject)
//        btnAddSubject.setOnClickListener { showAddDialog() }
//    }
//
//    private fun showAddDialog() {
//        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_subject, null)
//        val subjectName = dialogView.findViewById<EditText>(R.id.editSubjectName)
//        val subjectTime = dialogView.findViewById<EditText>(R.id.editSubjectTime)
//        val subjectDay = dialogView.findViewById<EditText>(R.id.editSubjectDay)
//
//        AlertDialog.Builder(this)
//            .setTitle("Add Subject")
//            .setView(dialogView)
//            .setPositiveButton("Add") { _, _ ->
//                val name = subjectName.text.toString()
//                val time = subjectTime.text.toString()
//                val day = subjectDay.text.toString()
//
//                if (name.isNotEmpty() && time.isNotEmpty() && day.isNotEmpty()) {
//                    timeTableList.add(TimeTableItem(name, time, day))
//                    adapter.notifyItemInserted(timeTableList.size - 1)
//                    saveTimeTable()
//                } else {
//                    Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
//                }
//            }
//            .setNegativeButton("Cancel", null)
//            .show()
//    }
//
//    private fun saveTimeTable() {
//        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        val json = Gson().toJson(timeTableList)
//        editor.putString(KEY_TIMETABLE, json)
//        editor.apply()
//    }
//
//    private fun loadTimeTable() {
//        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//        val json = sharedPreferences.getString(KEY_TIMETABLE, null)
//        if (json != null) {
//            val type = object : TypeToken<MutableList<TimeTableItem>>() {}.type
//            val savedList: MutableList<TimeTableItem> = Gson().fromJson(json, type)
//            timeTableList.addAll(savedList)
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        saveTimeTable()
//    }
//}
//
//
//
////package com.example.attendencescanner
////
////
////import android.app.AlertDialog
////import android.os.Bundle
////import android.view.LayoutInflater
////import android.widget.EditText
////import android.widget.Toast
////import androidx.appcompat.app.AppCompatActivity
////import androidx.recyclerview.widget.LinearLayoutManager
////import androidx.recyclerview.widget.RecyclerView
////import com.example.attendencescanner.adapters.TimeTableAdapter
////import com.example.attendencescanner.viewmodel.TimeTableItem
////import com.google.android.material.floatingactionbutton.FloatingActionButton
////
////
////class TimeTableActivity : AppCompatActivity() {
////
////    private lateinit var recyclerView: RecyclerView
////    private lateinit var adapter: TimeTableAdapter
////    private val timeTableList = mutableListOf<TimeTableItem>()
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_time_table)
////
////        recyclerView = findViewById(R.id.recyclerViewTimeTable)
////        recyclerView.layoutManager = LinearLayoutManager(this)
////
////        adapter = TimeTableAdapter(timeTableList)
////        recyclerView.adapter = adapter
////
////        val btnAddSubject = findViewById<FloatingActionButton>(R.id.btnAddSubject)
////        btnAddSubject.setOnClickListener { showAddDialog() }
////    }
////
////    private fun showAddDialog() {
////        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_subject, null)
////        val subjectName = dialogView.findViewById<EditText>(R.id.editSubjectName)
////        val subjectTime = dialogView.findViewById<EditText>(R.id.editSubjectTime)
////        val subjectDay = dialogView.findViewById<EditText>(R.id.editSubjectDay)
////
////        AlertDialog.Builder(this)
////            .setTitle("Add Subject")
////            .setView(dialogView)
////            .setPositiveButton("Add") { _, _ ->
////                val name = subjectName.text.toString()
////                val time = subjectTime.text.toString()
////                val day = subjectDay.text.toString()
////
////                if (name.isNotEmpty() && time.isNotEmpty() && day.isNotEmpty()) {
////                    timeTableList.add(TimeTableItem(name, time, day))
////                    adapter.notifyItemInserted(timeTableList.size - 1)
////                } else {
////                    Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
////                }
////            }
////            .setNegativeButton("Cancel", null)
////            .show()
////    }
////}


package com.example.attendencescanner

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.attendencescanner.adapters.TimeTableAdapter
import com.example.attendencescanner.data.model.TimeTableItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TimeTableActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TimeTableAdapter
    private val timeTableList = mutableListOf<TimeTableItem>()
    private val PREFS_NAME = "time_table_prefs"
    private val KEY_TIMETABLE = "timetable_data"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_table)

        recyclerView = findViewById(R.id.recyclerViewTimeTable)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadTimeTable()

        adapter = TimeTableAdapter(timeTableList)
        recyclerView.adapter = adapter

        val btnAddSubject = findViewById<FloatingActionButton>(R.id.btnAddSubject)
        btnAddSubject.setOnClickListener { showAddDialog() }

        // ✅ Swipe to Delete
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = timeTableList[position]

                AlertDialog.Builder(this@TimeTableActivity)
                    .setTitle("Delete Subject")
                    .setMessage("Are you sure you want to delete '${item.subjectName}'?")
                    .setPositiveButton("Delete") { _, _ ->
                        timeTableList.removeAt(position)
                        adapter.notifyItemRemoved(position)
                        saveTimeTable()
                        Toast.makeText(this@TimeTableActivity, "Deleted successfully", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        adapter.notifyItemChanged(position)
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun showAddDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_subject, null)
        val subjectName = dialogView.findViewById<EditText>(R.id.editSubjectName)
        val subjectTime = dialogView.findViewById<EditText>(R.id.editSubjectTime)
        val subjectDay = dialogView.findViewById<EditText>(R.id.editSubjectDay)

        AlertDialog.Builder(this)
            .setTitle("Add Subject")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = subjectName.text.toString()
                val time = subjectTime.text.toString()
                val day = subjectDay.text.toString()

                if (name.isNotEmpty() && time.isNotEmpty() && day.isNotEmpty()) {
                    timeTableList.add(TimeTableItem(name, time, day))
                    adapter.notifyItemInserted(timeTableList.size - 1)
                    saveTimeTable()
                } else {
                    Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun saveTimeTable() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(timeTableList)
        editor.putString(KEY_TIMETABLE, json)
        editor.apply()
    }

    private fun loadTimeTable() {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(KEY_TIMETABLE, null)
        if (json != null) {
            val type = object : TypeToken<MutableList<TimeTableItem>>() {}.type
            val savedList: MutableList<TimeTableItem> = Gson().fromJson(json, type)
            timeTableList.addAll(savedList)
        }
    }

    override fun onPause() {
        super.onPause()
        saveTimeTable()
    }
}

