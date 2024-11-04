package com.main.limitless

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.main.limitless.R
import com.main.limitless.data.Graphics.BarGraphic


class HomeFragment : Fragment() {
    private var currentIntake: Float = 0f
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        val lblSteps: TextView = view.findViewById(R.id.WP_txtSteps)
        val lblWeight: TextView = view.findViewById(R.id.WP_txtWeight)
        val lblWater: TextView = view.findViewById(R.id.WP_txtWater)
        val lblCal: TextView = view.findViewById(R.id.WP_txtCal)
        val waterGraph: BarGraphic = view.findViewById(R.id.HP_Water)


        lblSteps.setText(activityViewModel.steps.toString())
        if(lblSteps.text == "null")
        {
            lblSteps.text = "0"
        }
        lblWeight.setText(currentUser!!.GetWeight().toString())

        if(lblWeight.text == "null")
        {
            lblWeight.text = "-"
        }
        //lblWater.setText("${nutritionViewModel.getWaterIntake().toString()} L")

        lblCal.setText(nutritionViewModel.CalculateTotalCalories().toString())
        if(lblCal.text == "null")
        {
            lblCal.text = "-"
        }

        currentIntake = getCurrentIntake()
        waterGraph.setGoal(3f)
        waterGraph.setType("exp")
        waterGraph.invalidate()
        waterGraph.setProgress(currentIntake)
        waterGraph.setSize(900f, 200f)
        waterGraph.setThickness(3f)
        waterGraph.setBarHeight(20f)
        waterGraph.update()

        lblWater.text = getString(R.string.l_drank, currentIntake)
        waterGraph.visibility = View.VISIBLE

        return view
    }
    private fun getCurrentIntake(): Float {
        val sharedPref = requireContext().getSharedPreferences("WaterTracker", Context.MODE_PRIVATE)
        return sharedPref.getFloat("currentIntake", 0f)  // Default to 0 if no value found
    }
}