package com.main.limitless

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val lblSteps: TextView = view.findViewById(R.id.WP_txtSteps)
        val lblWeight: TextView = view.findViewById(R.id.WP_txtWeight)



        lblSteps.setText(nutritionViewModel.steps.toString())
        lblWeight.setText(currentUser!!.GetWeightGoal().toString())
        if(lblWeight.text == "null")
        {
            lblWeight.text = "-"
        }

        return view
    }
}