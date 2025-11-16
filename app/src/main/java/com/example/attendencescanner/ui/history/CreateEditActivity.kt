package com.example.attendencescanner.ui.history


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.attendencescanner.data.database.AppDatabase
import com.example.attendencescanner.data.entities.CurriculumActivity
import com.example.attendencescanner.data.repository.CurriculumActivityRepository
import com.example.attendencescanner.databinding.ActivityCreateEditBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CreateEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateEditBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val subject = binding.etSubject.text.toString()
            val desc = binding.etDescription.text.toString()

            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val parsedDate = dateFormat.parse(binding.etDueDate.text.toString())
            val dueDateMillis = parsedDate?.time ?: System.currentTimeMillis()

            val newActivity = CurriculumActivity(
                title = title,
                subject = subject,
                description = desc,
                assignedTo = "All",
                dueDate = dueDateMillis
            )

            // ✅ Insert into database
            lifecycleScope.launch {
                val dao = AppDatabase.getDatabase(application).curriculumActivityDao()
                val repo = CurriculumActivityRepository(dao)
                repo.insert(newActivity)

                runOnUiThread {
                    finish() // go back to list
                }
            }
        }
    }
}



//package com.example.attendencescanner.ui.history
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.example.attendencescanner.databinding.ActivityCreateEditBinding
//import com.example.attendencescanner.data.entities.CurriculumActivity
//import java.text.SimpleDateFormat
//import java.util.*
//
//class CreateEditActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityCreateEditBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityCreateEditBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        binding.btnSave.setOnClickListener {
//            val title = binding.etTitle.text.toString()
//            val subject = binding.etSubject.text.toString()
//            val desc = binding.etDescription.text.toString()
//            val dueDate = SimpleDateFormat("yyyy-MM-dd").parse(binding.etDueDate.text.toString())
//
//            val activity = CurriculumActivity(
//                title = title,
//                subject = subject,
//                description = desc,
//                assignedTo = "All",
//                dueDate = dueDate
//            )
//            // call ViewModel.insert(activity)
//            finish()
//        }
//    }
//}
