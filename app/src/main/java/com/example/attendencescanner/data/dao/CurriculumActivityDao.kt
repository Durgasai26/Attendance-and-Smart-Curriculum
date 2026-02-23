package com.example.attendencescanner.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.attendencescanner.data.entities.CurriculumActivity

@Dao
interface CurriculumActivityDao {

    // 🟢 Insert or update (Room auto-replaces duplicates)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertActivity(activity: CurriculumActivity)

    // 🟡 Update existing activity
    @Update
    suspend fun updateActivity(activity: CurriculumActivity)

    // 🔴 Delete an activity
    @Delete
    suspend fun deleteActivity(activity: CurriculumActivity)

    // 📋 Get all activities (sorted by due date)
    @Query("SELECT * FROM curriculum_activities ORDER BY dueDate ASC")
    fun getAllActivities(): LiveData<List<CurriculumActivity>>

    // 🎯 Get activities filtered by subject
    @Query("SELECT * FROM curriculum_activities WHERE subject = :subject ORDER BY dueDate ASC")
    fun getActivitiesBySubject(subject: String): LiveData<List<CurriculumActivity>>

    // ⚙️ Get activities filtered by status (Pending, Completed, etc.)
    @Query("SELECT * FROM curriculum_activities WHERE status = :status ORDER BY dueDate ASC")
    fun getActivitiesByStatus(status: String): LiveData<List<CurriculumActivity>>

    // 🧮 Optional: quick count query for dashboard summary
    @Query("SELECT COUNT(*) FROM curriculum_activities")
    suspend fun getActivityCount(): Int

    @Query("SELECT * FROM curriculum_activities WHERE assignedTo = :assignedTo OR assignedTo = 'All' ORDER BY dueDate ASC")
    fun getActivitiesForStudent(assignedTo: String): LiveData<List<CurriculumActivity>>

}
