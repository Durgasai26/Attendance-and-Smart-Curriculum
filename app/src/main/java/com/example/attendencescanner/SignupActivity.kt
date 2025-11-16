package com.example.attendencescanner

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.attendencescanner.data.database.AppDatabase
import com.example.attendencescanner.data.model.User
import com.example.attendencescanner.utils.Prefs
import kotlinx.coroutines.launch

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val emailInput = findViewById<EditText>(R.id.signupEmail)
        val passwordInput = findViewById<EditText>(R.id.signupPassword)
        val nameInput = findViewById<EditText>(R.id.signupName)
        val roleDropdown = findViewById<AutoCompleteTextView>(R.id.signupRole)
        val signupButton = findViewById<Button>(R.id.btnSignup)

        val roles = listOf("Teacher", "Student")
        roleDropdown.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, roles))
        roleDropdown.threshold = 0
        roleDropdown.setOnClickListener { roleDropdown.showDropDown() }
        roleDropdown.setOnFocusChangeListener { _, hasFocus -> if (hasFocus) roleDropdown.showDropDown() }

        signupButton.setOnClickListener {
            val name = nameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val role = roleDropdown.text.toString().trim()
            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                val db = AppDatabase.getInstance(this@SignupActivity)
                val userId = db.userDao().insert(User(name = name, email = email, password = password, role = role))
                Prefs.saveUser(this@SignupActivity, userId, role)
                Toast.makeText(this@SignupActivity, "Account created", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}


