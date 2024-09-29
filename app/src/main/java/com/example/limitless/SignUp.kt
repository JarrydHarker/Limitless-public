package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.data.ViewModels.ActivityViewModel
import com.example.limitless.data.ViewModels.NutritionViewModel
import java.time.LocalDate

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnSignUp: Button = findViewById(R.id.SU_btnSignIn)
        val txtEmail: EditText = findViewById(R.id.SU_txtUsername)
        val txtPassword: EditText = findViewById(R.id.SU_txtPassword)
        val test: Button = findViewById(R.id.btnTest)

        test.setOnClickListener{
            val intent = Intent(this, User_Details::class.java)
            startActivity(intent)
        }


        btnSignUp.setOnClickListener {
            val email = txtEmail.text.toString()
            val password = txtPassword.text.toString()

            if(email.isEmpty()){
                Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            currentUser?.email = email
            currentUser?.password = password
            val response = currentUser?.SignUpUser()

            if(response == "Success"){
                // Initialize ViewModel with calorieWallet from currentUser
                currentUser!!.SetCalorieWallet(2000.0)
                nutritionViewModel = NutritionViewModel(LocalDate.now(), currentUser!!.GetCalorieWallet(), currentUser!!.ratios)
                activityViewModel = ActivityViewModel(LocalDate.now())

                val intent = Intent(this, User_Details::class.java)
                startActivity(intent)
            }

            Toast.makeText(this, response, Toast.LENGTH_LONG).show()
        }
    }


}