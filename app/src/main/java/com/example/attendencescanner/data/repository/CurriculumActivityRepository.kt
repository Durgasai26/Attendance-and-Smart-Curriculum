package com.example.attendencescanner.data.repository

import androidx.lifecycle.LiveData
import com.example.attendencescanner.data.dao.CurriculumActivityDao
import com.example.attendencescanner.data.entities.CurriculumActivity

class CurriculumActivityRepository(private val dao: CurriculumActivityDao) {

    fun getAllActivities(): LiveData<List<CurriculumActivity>> = dao.getAllActivities()
    fun getActivitiesBySubject(subject: String) = dao.getActivitiesBySubject(subject)
    fun getActivitiesByStatus(status: String) = dao.getActivitiesByStatus(status)

    suspend fun insert(activity: CurriculumActivity) = dao.insertActivity(activity)
    suspend fun update(activity: CurriculumActivity) = dao.updateActivity(activity)
    suspend fun delete(activity: CurriculumActivity) = dao.deleteActivity(activity)
}
