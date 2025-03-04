package com.main.limitless

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class User_DailySteps : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_daily_steps)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
        //val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
        //val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val textView42 = findViewById<TextView>(R.id.textView60)
        val UDS_txtSteps = findViewById<EditText>(R.id.UDS_txtSteps)
        val UDS_txtUnit = findViewById<TextView>(R.id.UDS_txtUnit)

        val next: Button = findViewById(R.id.UDS_btnNext)
        val skip: Button = findViewById(R.id.UDS_btnSkip)

        val userStepsGoal: EditText = findViewById(R.id.UDS_txtSteps)

        val npText: TextView = findViewById(R.id.UDS_txtUnit)
        npText.text = "steps"

        textView42.startAnimation(ttb)
        UDS_txtSteps.startAnimation(stb)
        UDS_txtUnit.startAnimation(stb)
        next.startAnimation(btt)
        skip.startAnimation(btt2)

        next.setOnClickListener{
            if(userStepsGoal.text.isNotEmpty())
            {
                try{
                    val steps = userStepsGoal.text.toString()
                    currentUser?.SetStepGoal(steps.toInt())

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }catch (ex: Exception){
                    Toast.makeText(this,
                        getString(R.string.please_enter_a_valid_number), Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,
                    getString(R.string.please_fill_in_field_or_press_skip_to_continue), Toast.LENGTH_SHORT).show()
            }
        }

        skip.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}