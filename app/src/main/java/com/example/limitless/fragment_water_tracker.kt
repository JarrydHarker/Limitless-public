package com.example.limitless

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintAttribute
import androidx.constraintlayout.widget.ConstraintLayout

import com.example.limitless.data.Graphics.BarGraphic
import com.example.limitless.data.ViewModels.ActivityViewModel
import com.example.limitless.data.ViewModels.NutritionViewModel


class fragment_water_tracker : Fragment() {
    private var currentIntake: Float = 0f
    private lateinit var waterGraph: BarGraphic

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_water_tracker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btt4 = AnimationUtils.loadAnimation(view.context, R.anim.btt4)
        val txtViewWater = view.findViewById<TextView>(R.id.txtViewWater)
        waterGraph = view.findViewById(R.id.bgWater)
        val WaterCL = view.findViewById<ConstraintLayout>(R.id.WaterCL)

        WaterCL.startAnimation(btt4)

        currentIntake = getCurrentIntake()
        waterGraph.setGoal(3f)
        waterGraph.setType("exp")
        waterGraph.invalidate()
        waterGraph.setProgress(currentIntake)
        waterGraph.setSize(900f, 200f)
        waterGraph.setThickness(3f)
        waterGraph.setBarHeight(20f)
        waterGraph.update()

        txtViewWater.text = "0 L drank"
        waterGraph.visibility = View.VISIBLE

        val addWaterButton: Button = view.findViewById(R.id.addWater)
        addWaterButton.setOnClickListener {
            incrementWater()
            txtViewWater.text = "$currentIntake L drank"
        }
        val removeWaterButton: Button = view.findViewById(R.id.removeWater)
        removeWaterButton.setOnClickListener {
            removeWater()
            if (currentIntake < 0){
                txtViewWater.text = "0 L drank"
            }
            else
            txtViewWater.text = "$currentIntake L drank"
        }

    }

    private fun incrementWater() {
        currentIntake += 0.25f
        if (currentIntake <= 3f) {
            waterGraph.setProgress(currentIntake)
            waterGraph.update()
            saveCurrentIntake(currentIntake)
            nutritionViewModel.setWaterIntake(currentIntake.toDouble())
        } else {
            Toast.makeText(requireContext(), "Water goal reached!", Toast.LENGTH_SHORT).show()
        }
    }
    private fun removeWater() {
        currentIntake -= 0.25f
        if (currentIntake >= 0f) {
            waterGraph.setProgress(currentIntake)
            waterGraph.update()
            saveCurrentIntake(currentIntake)
            nutritionViewModel.setWaterIntake(currentIntake.toDouble())
        } else {
            Toast.makeText(requireContext(), "Cannot remove any more water", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveCurrentIntake(intake: Float) {
        val sharedPref = requireContext().getSharedPreferences("WaterTracker", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putFloat("currentIntake", intake)
        editor.apply()
    }

    private fun getCurrentIntake(): Float {
        val sharedPref = requireContext().getSharedPreferences("WaterTracker", Context.MODE_PRIVATE)
        return sharedPref.getFloat("currentIntake", 0f)  // Default to 0 if no value found
    }
}