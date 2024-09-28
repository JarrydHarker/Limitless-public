package com.example.limitless

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.widget.Button
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


    fun showStepDialog(){
        val dialog = Dialog(this@Goal_page)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.step_goal_dialog)
        dialog.window!!.attributes.windowAnimations=R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        val btnClose = dialog.findViewById<Button>(R.id.btnClose_SD)
        val btnConfirm = dialog.findViewById<Button>(R.id.btnConfirm_SD)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {

        }


        dialog.show()
    }

    fun showWeightDialog(){
        val dialog = Dialog(this@Goal_page)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.weight_goal_dialog)
        dialog.window!!.attributes.windowAnimations=R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        val btnClose = dialog.findViewById<Button>(R.id.btnClose_WGD)
        val btnConfirm = dialog.findViewById<Button>(R.id.btnConfirm_WGD)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {

        }

        dialog.show()
    }

    fun showCalorieDialog(){
        val dialog = Dialog(this@Goal_page)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.calorie_goal_dialog)
        dialog.window!!.attributes.windowAnimations=R.style.dialogAnimation
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        val btnClose = dialog.findViewById<Button>(R.id.btnClose_CD)
        val btnConfirm = dialog.findViewById<Button>(R.id.btnConfirm_CD)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {

        }

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
            .setTitle("Set Goal")
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