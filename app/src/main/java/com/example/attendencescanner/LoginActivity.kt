package com.example.attendencescanner

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.attendencescanner.data.database.AppDatabase
import com.example.attendencescanner.utils.Prefs
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val emailInput = findViewById<EditText>(R.id.etEmail)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val roleGroup = findViewById<RadioGroup>(R.id.roleGroup)
        val studentRadio = findViewById<RadioButton>(R.id.rbStudent)
        val teacherRadio = findViewById<RadioButton>(R.id.rbTeacher)
        val loginButton = findViewById<MaterialButton>(R.id.btnLogin)
        val signupRedirect = findViewById<TextView>(R.id.tvSignupRedirect)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            val selectedRole = when (roleGroup.checkedRadioButtonId) {
                R.id.rbTeacher -> "Teacher"
                else -> "Student"
            }

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val db = AppDatabase.getInstance(this@LoginActivity)
                val user = db.userDao().login(email, password)

                if (user == null || !user.role.equals(selectedRole, ignoreCase = true)) {
                    Toast.makeText(this@LoginActivity, "Invalid credentials or role mismatch", Toast.LENGTH_SHORT).show()
                } else {
                    Prefs.saveUser(this@LoginActivity, user.id, user.role)

                    if (user.role.equals("Teacher", ignoreCase = true)) {
                        startActivity(Intent(this@LoginActivity, TeacherDashboardActivity::class.java))
                    } else {
                        startActivity(Intent(this@LoginActivity, StudentDashboardActivity::class.java))
                    }
                    finish()
                }
            }
        }

        signupRedirect.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}


//package com.example.attendencescanner
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.ArrayAdapter
//import android.widget.AutoCompleteTextView
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import com.example.attendencescanner.data.database.AppDatabase
//import com.example.attendencescanner.utils.Prefs
//import kotlinx.coroutines.launch
//
//class LoginActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        val emailInput = findViewById<EditText>(R.id.etEmail)
//        val passwordInput = findViewById<EditText>(R.id.etPassword)
//        val roleDropdown = findViewById<AutoCompleteTextView>(R.id.tvRoleLabel)
//        val loginButton = findViewById<Button>(R.id.btnLogin)
//        val signupButton = findViewById<Button>(R.id.btnGoSignup)
//
//        val roles = listOf("Teacher", "Student")
//        roleDropdown.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, roles))
//        roleDropdown.threshold = 0
//        roleDropdown.setOnClickListener { roleDropdown.showDropDown() }
//        roleDropdown.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) roleDropdown.showDropDown() }
//
//        loginButton.setOnClickListener {
//            val email = emailInput.text.toString().trim()
//            val password = passwordInput.text.toString().trim()
//            val selectedRole = roleDropdown.text.toString().trim()
//            if (email.isEmpty() || password.isEmpty() || selectedRole.isEmpty()) {
//                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            lifecycleScope.launch {
//                val db = AppDatabase.getInstance(this@LoginActivity)
//                val user = db.userDao().login(email, password)
//                if (user == null || !user.role.equals(selectedRole, ignoreCase = true)) {
//                    Toast.makeText(this@LoginActivity, "Invalid credentials", Toast.LENGTH_SHORT).show()
//                } else {
//                    Prefs.saveUser(this@LoginActivity, user.id, user.role)
//                    if (user.role.equals("Teacher", ignoreCase = true)) {
//                        startActivity(Intent(this@LoginActivity, TeacherDashboardActivity::class.java))
//                    } else {
//                        startActivity(Intent(this@LoginActivity, StudentDashboardActivity::class.java))
//                    }
//                    finish()
//                }
//            }
//        }
//
//        signupButton.setOnClickListener {
//            startActivity(Intent(this, SignupActivity::class.java))
//        }
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        finish()
//        return true
//    }
//}
//
//
