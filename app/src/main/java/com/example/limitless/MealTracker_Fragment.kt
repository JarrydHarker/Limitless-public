package com.example.limitless

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.limitless.data.ViewModels.NutritionViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MealTracker_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
var mealTitle: String = ""

class MealTracker_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var nutritionViewModel: NutritionViewModel

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
        // Get the shared ViewModel from the activity scope
        nutritionViewModel = ViewModelProvider(requireActivity())[NutritionViewModel::class.java]

        val view = inflater.inflate(R.layout.fragment_meal_tracker_, container, false)

        val btnAddBreakfast: Button = view.findViewById(R.id.btnAddBreakfast_MT)
        val btnAddLunch: Button = view.findViewById(R.id.btnAddLunch_MT)
        val btnAddDinner: Button = view.findViewById(R.id.btnAddDinner_MT)

        btnAddBreakfast.setOnClickListener{
            mealTitle = "Breakfast"
            val intent = Intent(requireActivity(), Log_Meal::class.java)
            startActivity(intent)
        }

        btnAddLunch.setOnClickListener{
            mealTitle = "Lunch"
            val intent = Intent(requireActivity(), Log_Meal::class.java)
            startActivity(intent)
        }

        btnAddDinner.setOnClickListener{
            mealTitle = "Dinner"
            val intent = Intent(requireActivity(), Log_Meal::class.java)
            startActivity(intent)
        }

        // Inflate the layout for this fragment
        return view
    }

    fun showDialog(){

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MealTracker_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MealTracker_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}