package com.main.limitless

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
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
        checker(lblStepGoal, lblWeightGoal, lblCalorieGoal)


        Back.setOnClickListener{
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        lblStepGoal.setOnClickListener {
            showStepDialog(lblStepGoal)
        }

        lblWeightGoal.setOnClickListener {
            showWeightDialog(lblWeightGoal)
        }

        lblCalorieGoal.setOnClickListener {
            if(lblCalorieGoal.text != "Click to Add"){
                Goals(lblCalorieGoal)
            }else{
                Toast.makeText(this,
                    getString(R.string.please_enter_a_weight_goal_first), Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun showStepDialog(lblStepGoal:TextView) {
        val builder = AlertDialog.Builder(this@Goal_page)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.step_goal_dialog, null)
        builder.setView(dialogView)

        val txtSteps = dialogView.findViewById<TextView>(R.id.txtStepGoal_SD)

        builder.setPositiveButton("Confirm") { dialog, _ ->
            val txtStepsText: String = txtSteps.text.toString()
            val steps: Int = txtStepsText.toIntOrNull() ?: 0

            if (txtSteps.text.isNotEmpty()) {
                currentUser!!.SetStepGoal(steps)
                lblStepGoal.text = currentUser!!.GetStepGoal().toString()
                dialog.dismiss()
            } else {
                Toast.makeText(this, getString(R.string.please_enter_step_goal), Toast.LENGTH_SHORT).show()
            }
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


    fun showWeightDialog(lblWeightGoal: TextView) {
        val builder = AlertDialog.Builder(this@Goal_page)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.weight_goal_dialog, null)
        builder.setView(dialogView)

        val txtWeightView: TextView = dialogView.findViewById(R.id.txtWeightGoal_WD)

        builder.setPositiveButton("Confirm") { dialog, _ ->
            val txtWeightText: String = txtWeightView.text.toString()
            val txtWeight: Double = txtWeightText.toDoubleOrNull() ?: 0.0
            currentUser!!.SetWeightGoal(txtWeight)
            lblWeightGoal.text = currentUser!!.GetWeightGoal().toString()
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


    fun showCalorieDialog(lblCalorieGoal: TextView) {
        val builder = AlertDialog.Builder(this@Goal_page)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.calorie_goal_dialog, null)
        builder.setView(dialogView)
        val txtCalorie = dialogView.findViewById<TextView>(R.id.txtCalorie_CD)


        builder.setPositiveButton("Confirm") { dialog, _ ->
            val txtCalorieText: String = txtCalorie.text.toString()
            val calories: Double = txtCalorieText.toDoubleOrNull() ?: 0.0
            currentUser!!.SetCalorieWallet(calories)
            lblCalorieGoal.text = currentUser!!.GetCalorieWallet().toString()
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


    private fun Goals(lblCalorieGoal: TextView){
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
                showCalorieDialog(lblCalorieGoal)
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()

            }
            .create()
            .show()
    }

    fun checker(lblStepGoal: TextView, lblWeightGoal: TextView, lblCalorieGoal: TextView){
        if(currentUser!!.GetStepGoal() != 0){
            lblStepGoal.text = currentUser!!.GetStepGoal().toString()
        }
        if(currentUser!!.GetWeightGoal() != 0.0){
            lblWeightGoal.text = currentUser!!.GetWeightGoal().toString()
        }
        if(currentUser!!.GetCalorieWallet() != 0.0){
            lblCalorieGoal.text = currentUser!!.GetCalorieWallet().toString()
        }
    }


}