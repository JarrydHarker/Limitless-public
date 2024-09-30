package com.example.limitless

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.constraintlayout.widget.ConstraintAttribute
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.limitless.data.Graphics.BarGraphic

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment_water_tracker.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_water_tracker : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_water_tracker, container, false)

        //val ttb = AnimationUtils.loadAnimation(view.context, R.anim.ttb)
        //val stb = AnimationUtils.loadAnimation(view.context, R.anim.stb)
        //val btt = AnimationUtils.loadAnimation(view.context, R.anim.btt)
        //val btt2 = AnimationUtils.loadAnimation(view.context, R.anim.btt2)
        //val btt3 = AnimationUtils.loadAnimation(view.context, R.anim.btt3)
        val btt4 = AnimationUtils.loadAnimation(view.context, R.anim.btt4)
        val waterGraph: BarGraphic = view.findViewById(R.id.bgWater)
        val WaterCL = view.findViewById<ConstraintLayout>(R.id.WaterCL)

        WaterCL.startAnimation(btt4)

        waterGraph.setGoal(3f)
        waterGraph.setType("exp")
        waterGraph.setProgress(1.25f)
        waterGraph.setSize(400f, 400f)
        waterGraph.setThickness(50f)
        waterGraph.setLevel(5)
        waterGraph.update()

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragment_water_tracker.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_water_tracker().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}