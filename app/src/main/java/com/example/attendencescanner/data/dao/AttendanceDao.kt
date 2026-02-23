package com.example.attendencescanner.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.example.attendencescanner.data.model.Attendance
import com.example.attendencescanner.data.model.AttendanceWithUser

@Dao
interface AttendanceDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(attendance: Attendance): Long

    @Update
    suspend fun update(attendance: Attendance)

    @Delete
    suspend fun delete(attendance: Attendance)

    @Query("SELECT * FROM attendance WHERE studentId = :studentId ORDER BY id DESC")
    suspend fun getByStudent(studentId: Long): List<Attendance>

    @Query("SELECT a.id as id, a.studentId as studentId, u.name as studentName, a.className as className, a.date as date, a.time as time FROM attendance a INNER JOIN users u ON a.studentId = u.id ORDER BY a.id DESC")
    suspend fun getAllWithUser(): List<AttendanceWithUser>

    @Query("SELECT a.id as id, a.studentId as studentId, u.name as studentName, a.className as className, a.date as date, a.time as time FROM attendance a INNER JOIN users u ON a.studentId = u.id WHERE a.studentId = :studentId AND (:classLike IS NULL OR a.className LIKE :classLike) AND (:dateLike IS NULL OR a.date LIKE :dateLike) ORDER BY a.id DESC")
    suspend fun getByStudentFiltered(studentId: Long, classLike: String?, dateLike: String?): List<AttendanceWithUser>

    @Query("SELECT a.id as id, a.studentId as studentId, u.name as studentName, a.className as className, a.date as date, a.time as time FROM attendance a INNER JOIN users u ON a.studentId = u.id WHERE (:classLike IS NULL OR a.className LIKE :classLike) AND (:dateLike IS NULL OR a.date LIKE :dateLike) ORDER BY a.id DESC")
    suspend fun getAllFiltered(classLike: String?, dateLike: String?): List<AttendanceWithUser>
}


