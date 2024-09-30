package com.example.limitless

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Goal_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_goal_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val lblStepGoal = findViewById<TextView>(R.id.lblGoalSteps_GP)
        val lblWeightGoal = findViewById<TextView>(R.id.lblGoalWeight_GP)
        val lblCalorieGoal = findViewById<TextView>(R.id.lblGoalCalorie_GP)
        val Back: ImageView = findViewById(R.id.GP_ivBack)

        Back.setOnClickListener{
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        lblStepGoal.setOnClickListener {
            showStepDialog()
        }

        lblWeightGoal.setOnClickListener {
            showWeightDialog()
        }

        lblCalorieGoal.setOnClickListener {
           Goals()
        }

    }


    fun showStepDialog() {
        val builder = AlertDialog.Builder(this@Goal_page)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.step_goal_dialog, null)
        builder.setView(dialogView)

        builder.setPositiveButton("Confirm") { dialog, _ ->
            // Handle confirm button click
            dialog.dismiss()
        }

        builder.setNegativeButton("Close") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.attributes.windowAnimations = R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.show()
    }


    fun showWeightDialog() {
        val builder = AlertDialog.Builder(this@Goal_page)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.weight_goal_dialog, null)
        builder.setView(dialogView)

        builder.setPositiveButton("Confirm") { dialog, _ ->
            // Handle confirm button click
            dialog.dismiss()
        }

        builder.setNegativeButton("Close") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.attributes.windowAnimations = R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.show()
    }


    fun showCalorieDialog() {
        val builder = AlertDialog.Builder(this@Goal_page)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.calorie_goal_dialog, null)
        builder.setView(dialogView)

        builder.setPositiveButton("Confirm") { dialog, _ ->
            // Handle confirm button click
            dialog.dismiss()
        }

        builder.setNegativeButton("Close") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.window!!.attributes.windowAnimations = R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)
        dialog.show()
    }


    private fun Goals(){
        // Inflate the custom layout
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.layout_set_goal, null)

        val seekbar : SeekBar = dialogView.findViewById(R.id.seekbarTime)
        val showSeekbar: TextView = dialogView.findViewById(R.id.lblSelectPeriod)
        val loseWeight: RadioButton = dialogView.findViewById(R.id.rbLoseWeight)
        val gainWeight: RadioButton = dialogView.findViewById(R.id.rbGainWeight)

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                showSeekbar.text = "Time Period: $progress weeks"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        // Build and show the dialog
        AlertDialog.Builder(this)
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Confirm") { dialog, which ->
                showCalorieDialog()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()

            }
            .create()
            .show()
    }


}