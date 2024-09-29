package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.data.ViewModels.ActivityViewModel
import com.example.limitless.data.ViewModels.NutritionViewModel
import java.time.LocalDate

class User_Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)

        val txtName = findViewById<EditText>(R.id.txtName_UD)
        val txtSurname= findViewById<EditText>(R.id.txtSurname_UD)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm_UD)

        val DetailsLL = findViewById<LinearLayout>(R.id.DetailsLL)
        val textView60 = findViewById<TextView>(R.id.textView60)

        btnConfirm.startAnimation(btt)
        DetailsLL.startAnimation(stb)
        textView60.startAnimation(ttb)

        btnConfirm.setOnClickListener {
            val name = txtName.text.toString()
            val surname = txtSurname.text.toString()

            if(name.isNotEmpty() && surname.isNotEmpty()){
                currentUser?.name = name
                currentUser?.surname = surname

                currentUser?.SignUpUser{}

                val intent = Intent(this, User_Height::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this, "Please enter name and surname!", Toast.LENGTH_SHORT).show()
            }
        }



    }
}