package com.example.limitless.Exercise

import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.compose.ui.util.fastCbrt
import androidx.core.content.ContextCompat.startActivity
import com.example.limitless.R
import com.example.limitless.activityViewModel
import com.example.limitless.data.Workout

class Exercide_Adapter(context: Context, private val items: MutableList<Workout>) : ArrayAdapter<Workout>(context, 0, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.exercise_listview, parent, false)
        val title = view.findViewById<TextView>(R.id.lblExerciseT)
        val date = view.findViewById<TextView>(R.id.lblExerciseTime)
        val go = view.findViewById<Button>(R.id.btnGo_EL)

        val item = getItem(position)
        title.text = item?.name
        date.text = item?.date.toString()


        go.setOnClickListener {
            item?.let {
                activityViewModel.currentWorkout = it
                val intent = Intent(context, Log_Exercise::class.java)
                intent.putExtra("workoutId", it.workoutId)
                context.startActivity(intent)
            }
        }
        return view
    }

    fun updateItems(newItems: List<Workout>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}