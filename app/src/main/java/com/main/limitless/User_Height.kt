package com.main.limitless

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class User_Height : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_height)
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

        val UH_Linear = findViewById<LinearLayout>(R.id.UH_Linear)
        val textView42 = findViewById<TextView>(R.id.textView47)

        val next: Button = findViewById(R.id.UH_btnNext)
        val skip:Button = findViewById(R.id.UH_btnSkip)

        val npMain: NumberPicker = findViewById(R.id.UH_Main)
        npMain.minValue = 0
        npMain.maxValue = 300

        val npSecondary: NumberPicker = findViewById(R.id.UH_Secondary)
        npSecondary.minValue = 0
        npSecondary.maxValue = 9

        val npText: TextView = findViewById(R.id.UH_txtUnit)
        npText.text = "cm"

        textView42.startAnimation(ttb)
        UH_Linear.startAnimation(stb)
        next.startAnimation(btt)
        skip.startAnimation(btt2)

        next.setOnClickListener{
            if(npMain.value != 0)
            {
                val height: Double = (npMain.value + (npSecondary.value/10)).toDouble()
                currentUser?.SetHeight(height)

                val intent = Intent(this, User_Weight::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,
                    getString(R.string.please_fill_in_field_or_press_skip_to_continue1), Toast.LENGTH_SHORT).show()
            }
        }
        skip.setOnClickListener{
            val intent = Intent(this, User_Weight::class.java)
            startActivity(intent)
        }
    }
}