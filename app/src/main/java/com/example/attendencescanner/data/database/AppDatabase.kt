package com.example.attendencescanner.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.attendencescanner.data.dao.AttendanceDao
import com.example.attendencescanner.data.dao.UserDao
import com.example.attendencescanner.data.dao.CurriculumActivityDao
import com.example.attendencescanner.data.model.Attendance
import com.example.attendencescanner.data.model.User
import com.example.attendencescanner.data.entities.CurriculumActivity

@Database(
    entities = [User::class, Attendance::class, CurriculumActivity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun attendanceDao(): AttendanceDao
    abstract fun curriculumActivityDao(): CurriculumActivityDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "attendance_app.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }

        fun getInstance(context: Context): AppDatabase = getDatabase(context)
    }

}


//package com.example.attendencescanner.data.database
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.attendencescanner.data.dao.AttendanceDao
//import com.example.attendencescanner.data.dao.UserDao
//import com.example.attendencescanner.data.model.Attendance
//import com.example.attendencescanner.data.model.User
//
//@Database(entities = [User::class, Attendance::class], version = 1, exportSchema = false)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//    abstract fun attendanceDao(): AttendanceDao
//
//    companion object {
//        @Volatile private var INSTANCE: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
//            INSTANCE ?: Room.databaseBuilder(
//                context.applicationContext,
//                AppDatabase::class.java,
//                "attendance_app.db"
//            ).build().also { INSTANCE = it }
//        }
//    }
//}
//
//
