package com.example.attendencescanner.viewmodel

import androidx.lifecycle.*
import com.example.attendencescanner.data.entities.CurriculumActivity
import com.example.attendencescanner.data.repository.CurriculumActivityRepository
import kotlinx.coroutines.launch

class CurriculumActivityViewModel(private val repository: CurriculumActivityRepository) : ViewModel() {

    val allActivities = repository.getAllActivities()

    fun insert(activity: CurriculumActivity) = viewModelScope.launch {
        repository.insert(activity)
    }

    fun update(activity: CurriculumActivity) = viewModelScope.launch {
        repository.update(activity)
    }

    fun delete(activity: CurriculumActivity) = viewModelScope.launch {
        repository.delete(activity)
    }
}

class CurriculumActivityViewModelFactory(private val repository: CurriculumActivityRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurriculumActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CurriculumActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
