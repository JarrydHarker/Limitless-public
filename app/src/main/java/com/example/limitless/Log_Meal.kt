package com.example.limitless

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.limitless.data.ViewModels.NutritionViewModel

class Log_Meal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_meal)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLog = findViewById<Button>(R.id.btnLog_LM)
        val btnCreateMeal = findViewById<Button>(R.id.btnCreateMeal_LM)
        val lblMealTitle = findViewById<TextView>(R.id.lblMealTitle_LM)
        lblMealTitle.text = mealTitle



        btnLog.setOnClickListener {

        }

        btnCreateMeal.setOnClickListener {
            showDialog()
        }

    }

    fun showDialog(){
        val dialog = Dialog(this@Log_Meal)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.create_meal_dialog)
        dialog.window!!.attributes.windowAnimations=R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        val close = dialog.findViewById<Button>(R.id.btnCloseDialog)
        val add = dialog.findViewById<Button>(R.id.btnAddMeal_LMD)

        close.setOnClickListener {
            dialog.cancel()
        }

        dialog.show()
    }
}