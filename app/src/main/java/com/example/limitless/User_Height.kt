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
        /*val heightSpinner: Spinner = findViewById(R.id.height_spHeight)

        // Create a list of height options
        //val heights = arrayOf("150 cm", "155 cm", "160 cm", "165 cm", "170 cm", "175 cm", "180 cm", "185 cm", "190 cm")

        val heights = (0..300).map { "$it cm" }
        // Create an ArrayAdapter using the height array and a default spinner layout
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, heights)

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Apply the adapter to the spinner
        heightSpinner.adapter = adapter

        // Set a listener to handle selection events
        heightSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedHeight = parent.getItemAtPosition(position).toString()
                // Handle the selected height here (e.g., save it or display it)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do something if no height is selected
            }
        }*/
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
        AlertDialog.Builder(this)
            .setTitle("Set Height")
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Done") { dialog, which ->
                val selectedMainHeight = npMain.value
                val selectedSecondaryHeight = npSecondary.value

                Toast.makeText(this, "Height Captured: $selectedMainHeight, $selectedSecondaryHeight ${npText.text}", Toast.LENGTH_SHORT).show()
                showWeightPickerDialog()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                showWeightPickerDialog()
            }
            .create()
            .show()
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
        AlertDialog.Builder(this)
            .setTitle("Set Weight")
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Done") { dialog, which ->
                val selectedMainHeight = npMain.value
                val selectedSecondaryHeight = npSecondary.value

                Toast.makeText(this, "Weight Captured: $selectedMainHeight, $selectedSecondaryHeight ${npText.text}", Toast.LENGTH_SHORT).show()
                showWeightGoalPickerDialog()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                showWeightGoalPickerDialog()
            }
            .create()
            .show()
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
        AlertDialog.Builder(this)
            .setTitle("Set Weight Goal")
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Done") { dialog, which ->
                val selectedMainHeight = npMain.value
                val selectedSecondaryHeight = npSecondary.value

                Toast.makeText(this, "Weight Goal Captured: $selectedMainHeight, $selectedSecondaryHeight ${npText.text}", Toast.LENGTH_SHORT).show()
                showCalorieWalletPickerDialog()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                showCalorieWalletPickerDialog()
            }
            .create()
            .show()
    }
    private fun showCalorieWalletPickerDialog() {
        // Inflate the custom layout
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_inserter, null)

        val userCalories: EditText = dialogView.findViewById(R.id.txt_UserNumber)

        val npText: TextView = dialogView.findViewById(R.id.txt_UserUnits)
        npText.text = "calories"

        // Build and show the dialog
        AlertDialog.Builder(this)
            .setTitle("Set Calorie Wallet")
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Done") { dialog, which ->
                val selectedCalories = userCalories

                Toast.makeText(this, "Calorie Wallet Captured: ${selectedCalories.text} ${npText.text}", Toast.LENGTH_SHORT).show()
                showStepsGoalPickerDialog()
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
                showStepsGoalPickerDialog()
            }
            .create()
            .show()
    }
    private fun showStepsGoalPickerDialog() {
        // Inflate the custom layout
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_inserter, null)

        val userStepsGoal: EditText = dialogView.findViewById(R.id.txt_UserNumber)

        val npText: TextView = dialogView.findViewById(R.id.txt_UserUnits)
        npText.text = "steps"

        // Build and show the dialog
        AlertDialog.Builder(this)
            .setTitle("Set Daily Steps Goal")
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Done") { dialog, which ->
                val selectedSteps = userStepsGoal

                Toast.makeText(this, "Daily Steps Goal Captured: ${selectedSteps.text} ${npText.text}", Toast.LENGTH_SHORT).show()
                currentUser = User()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()

                currentUser = User()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            .create()
            .show()
    }
}