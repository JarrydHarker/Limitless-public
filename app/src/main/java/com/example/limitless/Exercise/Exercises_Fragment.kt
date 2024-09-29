package com.example.limitless.Exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.limitless.Nutrition.Diet_Activity
import com.example.limitless.R
import com.example.limitless.activityViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private val currentDayViewModel: CurrentDayViewModel by activityViewModels()
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Exercises_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exercises_, container, false)
        val btnAddWorkout: Button = view.findViewById(R.id.btnAddWorkout_EF)
        val lvExercises: ListView = view.findViewById(R.id.listExercises_EF)
        val workoutAdapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line)
        val arrExercises: MutableList<String> = mutableListOf()

        val ttb = AnimationUtils.loadAnimation(view.context, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(view.context, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(view.context, R.anim.btt)
        val btt2 = AnimationUtils.loadAnimation(view.context, R.anim.btt2)
        val btt3 = AnimationUtils.loadAnimation(view.context, R.anim.btt3)
        val btt4 = AnimationUtils.loadAnimation(view.context, R.anim.btt4)

        val constraintLayout8 = view.findViewById<ConstraintLayout>(R.id.constraintLayout8)
        val horizontalScrollViewCategories = view.findViewById<HorizontalScrollView>(R.id.horizontalScrollViewCategories)
        val textView31 = view.findViewById<TextView>(R.id.textView31)
        val listExercises_EF = view.findViewById<ListView>(R.id.listExercises_EF)

        constraintLayout8.startAnimation(stb)
        horizontalScrollViewCategories.startAnimation(btt)
        textView31.startAnimation(btt2)
        listExercises_EF.startAnimation(btt3)

        val workouts = activityViewModel.GetWorkouts()
        if(workouts != null){
            for(workout in workouts){
                workoutAdapter.add(workout.toString())
            }

            workoutAdapter.notifyDataSetChanged()
            lvExercises.adapter = workoutAdapter
        }

        btnAddWorkout.setOnClickListener {
            val intent = Intent(requireActivity(), exerciseCategory::class.java)
            startActivity(intent)
        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Calorie_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Exercises_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}