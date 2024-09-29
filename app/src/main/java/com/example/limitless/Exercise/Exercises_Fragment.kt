package com.example.limitless.Exercise

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
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

        val workouts = activityViewModel.GetWorkouts()
        if(workouts != null){
            for(workout in workouts){
                workoutAdapter.add(workout.toString())
            }

            workoutAdapter.notifyDataSetChanged()
            lvExercises.adapter = workoutAdapter
        }

        btnAddWorkout.setOnClickListener {
            val intent = Intent(requireActivity(), New_Workout::class.java)
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