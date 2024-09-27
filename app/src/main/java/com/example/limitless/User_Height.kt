package com.example.limitless

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.data.User

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
        showHeightPickerDialog()

    }
    private fun showHeightPickerDialog() {
        // Inflate the custom layout
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_picker, null)

        // Initialize the NumberPicker
        val npMain: NumberPicker = dialogView.findViewById(R.id.np_main)
        npMain.minValue = 0
        npMain.maxValue = 300

        val npSecondary: NumberPicker = dialogView.findViewById(R.id.np_secondary)
        npSecondary.minValue = 0
        npSecondary.maxValue = 9

        val npText: TextView = dialogView.findViewById(R.id.txtUnit)
        npText.text = "cm"

        // Build and show the dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Set Height")
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Done") { dialog, which ->
                val selectedMainHeight = npMain.value
                val selectedSecondaryHeight = npSecondary.value

                if(selectedMainHeight != 0)
                {
                    Toast.makeText(this, "Height Captured: $selectedMainHeight, $selectedSecondaryHeight ${npText.text}", Toast.LENGTH_SHORT).show()
                    showWeightPickerDialog()

                }
                else{
                    Toast.makeText(this, "Please fill in field or press Cancel to continue", Toast.LENGTH_SHORT).show()
                    showHeightPickerDialog()
                }
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                showWeightPickerDialog()
            }
            .create()

        dialog.setCanceledOnTouchOutside(false)
        dialog.show()

    }
    private fun showWeightPickerDialog() {
        // Inflate the custom layout
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_picker, null)

        // Initialize the NumberPicker
        val npMain: NumberPicker = dialogView.findViewById(R.id.np_main)
        npMain.minValue = 0
        npMain.maxValue = 500

        val npSecondary: NumberPicker = dialogView.findViewById(R.id.np_secondary)
        npSecondary.minValue = 0
        npSecondary.maxValue = 9

        val npText: TextView = dialogView.findViewById(R.id.txtUnit)
        npText.text = "kg"

        // Build and show the dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Set Weight")
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Done") { dialog, which ->
                val selectedMainWeight = npMain.value
                val selectedSecondaryWeight = npSecondary.value

                if(selectedMainWeight != 0)
                {
                    Toast.makeText(this, "Weight Captured: $selectedMainWeight, $selectedSecondaryWeight ${npText.text}", Toast.LENGTH_SHORT).show()
                    showWeightGoalPickerDialog()
                }
                else
                {
                    Toast.makeText(this, "Please fill in field or press Cancel to continue", Toast.LENGTH_SHORT).show()
                    showWeightPickerDialog()
                }

            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                showWeightGoalPickerDialog()
            }
            .create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun showWeightGoalPickerDialog() {
        // Inflate the custom layout
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_picker, null)

        // Initialize the NumberPicker
        val npMain: NumberPicker = dialogView.findViewById(R.id.np_main)
        npMain.minValue = 0
        npMain.maxValue = 500

        val npSecondary: NumberPicker = dialogView.findViewById(R.id.np_secondary)
        npSecondary.minValue = 0
        npSecondary.maxValue = 9

        val npText: TextView = dialogView.findViewById(R.id.txtUnit)
        npText.text = "kg"

        // Build and show the dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Set Weight Goal")
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Done") { dialog, which ->
                val selectedMainWeightGoal = npMain.value
                val selectedSecondaryWeightGoal = npSecondary.value

                if(selectedMainWeightGoal != 0)
                {
                    Toast.makeText(this, "Weight Goal Captured: $selectedMainWeightGoal, $selectedSecondaryWeightGoal ${npText.text}", Toast.LENGTH_SHORT).show()
                    showCalorieWalletPickerDialog()
                }
                else{
                    Toast.makeText(this, "Please fill in field or press Cancel to continue", Toast.LENGTH_SHORT).show()
                    showWeightGoalPickerDialog()
                }

            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                showCalorieWalletPickerDialog()
            }
            .create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun showCalorieWalletPickerDialog() {
        // Inflate the custom layout
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_inserter, null)

        val userCalories: EditText = dialogView.findViewById(R.id.txt_UserNumber)

        val npText: TextView = dialogView.findViewById(R.id.txt_UserUnits)
        npText.text = "calories"

        // Build and show the dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Set Calorie Wallet")
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Done") { dialog, which ->
                val selectedCalories = userCalories

                if(selectedCalories.text.isNotEmpty())
                {
                    Toast.makeText(this, "Calorie Wallet Captured: ${selectedCalories.text} ${npText.text}", Toast.LENGTH_SHORT).show()
                    showStepsGoalPickerDialog()
                }
                else{
                    Toast.makeText(this, "Please fill in field or press Cancel to continue", Toast.LENGTH_SHORT).show()
                    showCalorieWalletPickerDialog()
                }
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                showStepsGoalPickerDialog()
            }
            .create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
    private fun showStepsGoalPickerDialog() {
        // Inflate the custom layout
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_inserter, null)

        val userStepsGoal: EditText = dialogView.findViewById(R.id.txt_UserNumber)

        val npText: TextView = dialogView.findViewById(R.id.txt_UserUnits)
        npText.text = "steps"

        // Build and show the dialog
        val dialog = AlertDialog.Builder(this)
            .setTitle("Set Daily Steps Goal")
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Done") { dialog, which ->
                val selectedSteps = userStepsGoal

                if(selectedSteps.text.isNotEmpty())
                {
                    Toast.makeText(this, "Daily Steps Goal Captured: ${selectedSteps.text} ${npText.text}", Toast.LENGTH_SHORT).show()
                    currentUser = User()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Please fill in field or press Cancel to continue", Toast.LENGTH_SHORT).show()
                    showStepsGoalPickerDialog()
                }

            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()

                currentUser = User()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}