package com.example.limitless

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.limitless.data.Graphics.CircleGraphic


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private val currentDayViewModel: CurrentDayViewModel by activityViewModels()
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Exercises_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Exercises_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_exercises_, container, false)

        val cgSteps: CircleGraphic = view.findViewById(R.id.cgSteps)

        cgSteps.setSize(280f,280f)
        cgSteps.setThickness(20f)
        cgSteps.setType("circle")
        cgSteps.RemoveLabel()
        cgSteps.setGoal(currentUser?.stepGoal!!.toFloat())
        cgSteps.setProgress(activityViewModel.steps.toFloat())

        val btnAddWorkout: Button = view.findViewById(R.id.btnAddWorkout_EF)

        btnAddWorkout.setOnClickListener {

        }
        val btnExercise = view.findViewById<Button>(R.id.btnAddWorkout_EF)

        btnExercise.setOnClickListener {
            val intent = Intent(requireActivity(), Add_Exercise::class.java)
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
         * @return A new instance of fragment Exercises_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param2: String) =
            Exercises_Fragment().apply {
                arguments = Bundle().apply {

                    putString(ARG_PARAM2, param2)
                }
            }
    }
}