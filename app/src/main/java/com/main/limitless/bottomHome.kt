package com.main.limitless

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.main.limitless.Exercise.Exercise_Activity
import com.main.limitless.Nutrition.Diet_Activity
import com.main.limitless.AI.AI_Page


class bottomHome : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bottom_home, container, false)

        val clExercises: ConstraintLayout = view.findViewById(R.id.cl_Exercise)
        val clNutrition: ConstraintLayout = view.findViewById(R.id.cl_Nutrition)
        val clAI: ConstraintLayout = view.findViewById(R.id.cl_AI)

        clExercises.setOnClickListener{
        val intent = Intent(requireActivity(), Exercise_Activity::class.java)
        startActivity(intent)
    }
        clNutrition.setOnClickListener{
            val intent = Intent(requireActivity(), Diet_Activity::class.java)
            startActivity(intent)
        }
        clAI.setOnClickListener{
            val intent = Intent(requireActivity(), AI_Page::class.java)
            startActivity(intent)
        }

        return view
    }
}