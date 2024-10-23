package com.example.limitless.Exercise

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.limitless.Exercise_Summary
import com.example.limitless.R
import com.example.limitless.activityViewModel
import com.example.limitless.currentUser
import com.example.limitless.data.Workout
import com.example.limitless.data.dbAccess
import com.example.limitless.nutritionViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate


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

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_exercises_, container, false)
        val btnAddWorkout: Button = view.findViewById(R.id.btnAddWorkout_EF)
        val lvWorkouts: ListView = view.findViewById(R.id.listExercises_EF)
        val workoutAdapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_dropdown_item_1line)

        val arrWorkouts: MutableList<Workout> = mutableListOf()
        val adapter = Exercide_Adapter(requireActivity(), arrWorkouts)


        dbAccess.GetUserWorkoutsByDate(currentUser?.userId!!, nutritionViewModel.currentDate) { workouts ->
            arrWorkouts.addAll(workouts)

            for (workout in activityViewModel.arrWorkouts) {
                workoutAdapter.add(workout.name)
            }

            adapter.notifyDataSetChanged()
           //lvWorkouts.adapter = workoutAdapter
            lvWorkouts.adapter = adapter
        }



        lvWorkouts.setOnItemClickListener { parent, view, position, id ->
            val selectedWorkout = activityViewModel.arrWorkouts[position]
            activityViewModel.currentWorkout = selectedWorkout

            val intent = Intent(requireActivity(), Log_Exercise::class.java)
            intent.putExtra("workoutId", selectedWorkout.workoutId)
            startActivity(intent)
        }

        val ttb = AnimationUtils.loadAnimation(view.context, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(view.context, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(view.context, R.anim.btt)
        val btt2 = AnimationUtils.loadAnimation(view.context, R.anim.btt2)
        val btt3 = AnimationUtils.loadAnimation(view.context, R.anim.btt3)
        val btt4 = AnimationUtils.loadAnimation(view.context, R.anim.btt4)

        val constraintLayout8 = view.findViewById<ConstraintLayout>(R.id.constraintLayout8)
        val horizontalScrollViewCategories = view.findViewById<HorizontalScrollView>(R.id.horizontalScrollViewCategories)
        val textView31 = view.findViewById<TextView>(R.id.textView31)

        constraintLayout8.startAnimation(stb)
        horizontalScrollViewCategories.startAnimation(btt)
        textView31.startAnimation(btt2)
        lvWorkouts.startAnimation(btt3)

        btnAddWorkout.setOnClickListener {
            ShowDialog()
        }

        return view
    }

    fun ShowDialog() {
        val dialog = Dialog(requireActivity())

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.attributes.windowAnimations = R.style.dialogAnimation
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.new_workout_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        dialog.show()

        val txtName: EditText = dialog.findViewById(R.id.txtWorkoutName)
        val btnCreate: Button = dialog.findViewById(R.id.btnCreateWorkout)
        val btnCancel: Button = dialog.findViewById(R.id.btnCancelWorkout)

        btnCreate.setOnClickListener {
            val name = txtName.text.toString()

            if(name.isNotEmpty()) {
                val workout = Workout(null, LocalDate.now(), name, currentUser!!.userId)

                activityViewModel.AddWorkout(workout) { id ->
                    val intent = Intent(requireActivity(), New_Workout::class.java)
                    intent.putExtra("workoutId", id)
                    startActivity(intent)
                }
            }else{
                Toast.makeText(
                    requireActivity(),
                    "Please enter a workout name to continue",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
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