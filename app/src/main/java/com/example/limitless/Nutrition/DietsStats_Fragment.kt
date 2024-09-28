package com.example.limitless.Nutrition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.limitless.R
import com.example.limitless.data.Graphics.CircleGraphic
import com.example.limitless.nutritionViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DietsStats_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DietsStats_Fragment : Fragment() {
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


        // Get the shared ViewModel from the activity scope
        val view = inflater.inflate(R.layout.fragment_diets_stats_, container, false)
        val lblGoal: TextView = view.findViewById(R.id.lblGoal_DSF)
        val lblFood: TextView = view.findViewById(R.id.lblFood_DSF)
        val cgTotalCalories: CircleGraphic = view.findViewById(R.id.cgTotalCalories)
        val cgCarbs: CircleGraphic = view.findViewById(R.id.cgCarbs)
        val cgFats: CircleGraphic = view.findViewById(R.id.cgFats)
        val cgFibre: CircleGraphic = view.findViewById(R.id.cgFibre)
        val cgProtein: CircleGraphic = view.findViewById(R.id.cgProtein)

        cgTotalCalories.setGoal(nutritionViewModel.calorieWallet!!.toFloat())
        cgTotalCalories.setProgress(nutritionViewModel.calorieCounter.CalculateTotalCalories().toFloat())
        cgTotalCalories.setSize(370f,370f)
        cgTotalCalories.setThickness(20f)
        cgTotalCalories.setType("circle")

        lblFood.setText(nutritionViewModel.GetTotalCalories().toString())
        lblGoal.setText(nutritionViewModel.calorieWallet.toString())
        DrawMiniCircle(cgFibre, nutritionViewModel.fibreWallet.toFloat(), nutritionViewModel.calorieCounter.GetTotalFibre().toFloat())
        DrawMiniCircle(cgCarbs, nutritionViewModel.carbWallet.toFloat(), nutritionViewModel.calorieCounter.GetTotalCarbs().toFloat())
        DrawMiniCircle(cgFats, nutritionViewModel.fatWallet.toFloat(), nutritionViewModel.calorieCounter.GetTotalFat().toFloat())
        DrawMiniCircle(cgProtein, nutritionViewModel.proteinWallet.toFloat(), nutritionViewModel.calorieCounter.GetTotalProtein().toFloat())

        // Inflate the layout for this fragment
        return view
    }

    fun DrawMiniCircle(circle: CircleGraphic, goal: Float, progress: Float){
        circle.RemoveLabel()
        circle.setGoal(goal)
        circle.setProgress(progress)
        circle.setSize(100f,100f)
        circle.setThickness(10f)
        circle.setType("circle")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DietsStats_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DietsStats_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}