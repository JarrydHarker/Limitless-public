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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintAttribute
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.limitless.data.Graphics.BarGraphic


class fragment_water_tracker : Fragment() {
    private var currentIntake: Float = 0f
    private lateinit var waterGraph: BarGraphic
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) :View{
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_water_tracker, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val ttb = AnimationUtils.loadAnimation(view.context, R.anim.ttb)
        //val stb = AnimationUtils.loadAnimation(view.context, R.anim.stb)
        //val btt = AnimationUtils.loadAnimation(view.context, R.anim.btt)
        //val btt2 = AnimationUtils.loadAnimation(view.context, R.anim.btt2)
        //val btt3 = AnimationUtils.loadAnimation(view.context, R.anim.btt3)
        val btt4 = AnimationUtils.loadAnimation(view.context, R.anim.btt4)
        waterGraph = view.findViewById(R.id.bgWater)
        val WaterCL = view.findViewById<ConstraintLayout>(R.id.WaterCL)

        WaterCL.startAnimation(btt4)
        currentIntake = getCurrentIntake()
        waterGraph.setGoal(50f)
        waterGraph.setType("exp")
        waterGraph.invalidate()
        waterGraph.setProgress(currentIntake)
        waterGraph.setSize(400f, 400f)
        waterGraph.setThickness(50f)
        waterGraph.setLevel(5)
        waterGraph.setBarHeight(20f)
        waterGraph.update()

        waterGraph.visibility = View.VISIBLE
        // Find the button and set its click listener
        val addWaterButton: Button = view.findViewById(R.id.addWater)
        addWaterButton.setOnClickListener {
            incrementWater()
        }
    }
    private fun incrementWater() {
        currentIntake += 0.25f
        if (currentIntake <= 3f) {
            waterGraph.setProgress(currentIntake)
            waterGraph.update()
            saveCurrentIntake(currentIntake)
        } else {

            Toast.makeText(requireContext(), "Water goal reached!", Toast.LENGTH_SHORT).show()
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