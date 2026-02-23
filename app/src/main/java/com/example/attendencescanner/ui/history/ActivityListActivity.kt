package com.example.attendencescanner.ui.history

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.attendencescanner.databinding.ActivityListBinding
import com.example.attendencescanner.data.database.AppDatabase
import com.example.attendencescanner.data.repository.CurriculumActivityRepository
import com.example.attendencescanner.viewmodel.CurriculumActivityViewModel
import com.example.attendencescanner.viewmodel.CurriculumActivityViewModelFactory
import com.example.attendencescanner.utils.Prefs

class ActivityListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var adapter: ActivityAdapter
    private lateinit var viewModel: CurriculumActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ActivityAdapter { } // no click action for student
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Get current student's ID or class from Prefs
        val studentId = Prefs.getUserId(this).toString()

        val dao = AppDatabase.getDatabase(application).curriculumActivityDao()
        val repository = CurriculumActivityRepository(dao)
        val factory = CurriculumActivityViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CurriculumActivityViewModel::class.java]

        // Observe only activities for this student
        dao.getActivitiesForStudent("student_$studentId").observe(this) { list ->
            adapter.submitList(list)
            if (list.isEmpty()) {
                binding.tvEmptyState.text = "No activities assigned yet"
                binding.tvEmptyState.visibility = View.VISIBLE
            } else {
                binding.tvEmptyState.visibility = View.GONE
            }
        }

        // Hide Add button (students can’t create activities)
        binding.btnAddActivity.visibility = View.GONE
    }
}



//package com.example.attendencescanner.ui.history
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.attendencescanner.databinding.ActivityListBinding
//import com.example.attendencescanner.data.entities.CurriculumActivity
//import com.example.attendencescanner.data.repository.CurriculumActivityRepository
//import com.example.attendencescanner.viewmodel.CurriculumActivityViewModel
//import com.example.attendencescanner.viewmodel.CurriculumActivityViewModelFactory
//import com.example.attendencescanner.data.database.AppDatabase
//
//class ActivityListActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityListBinding
//    private lateinit var adapter: ActivityAdapter
//
//    private val viewModel: CurriculumActivityViewModel by viewModels {
//        val dao = AppDatabase.getDatabase(application).curriculumActivityDao()
//        val repository = CurriculumActivityRepository(dao)
//        CurriculumActivityViewModelFactory(repository)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityListBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        adapter = ActivityAdapter { openDetails(it) }
//
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.adapter = adapter
//
//        viewModel.allActivities.observe(this) { activities ->
//            adapter.submitList(activities)
//        }
//
//        binding.btnAddActivity.setOnClickListener {
//            val intent = Intent(this, CreateEditActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    // ✅ Properly defined class-level function (not inside onCreate)
//    private fun openDetails(activity: CurriculumActivity) {
//        val intent = Intent(this, CreateEditActivity::class.java)
//        intent.putExtra("activityId", activity.id)
//        startActivity(intent)
//    }
//}
