package com.example.limitless.Exercise

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.Nutrition.Diet_Activity
import com.example.limitless.MainActivity
import com.example.limitless.R
import com.example.limitless.Report_Activity
import com.example.limitless.Settings
import com.example.limitless.data.DbAccess
import com.example.limitless.data.Movement
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Add_Exercise : AppCompatActivity() {

    var currentMove = Movement()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_exercise)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnCreateExercise = findViewById<Button>(R.id.btnCreateExercise_AE)
        val spinCategory: Spinner  = findViewById(R.id.spinCategory)
        val  arrExerciseMoves: MutableList<Movement> = mutableListOf()
        val db = DbAccess.GetInstance()
        val categories = db.GetCategories()

        spinCategory.adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categories)

        btnCreateExercise.setOnClickListener {
            ShowDialog(arrExerciseMoves)
        }

        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)

        bottomNavBar.setSelectedItemId(R.id.ic_workouts)
        bottomNavBar.setOnNavigationItemSelectedListener{item ->
            when (item.itemId){
                R.id.ic_home -> {
                    navigateToActivityLeft(MainActivity::class.java)
                    true
                }
                R.id.ic_nutrition -> {
                    navigateToActivityRight(Diet_Activity::class.java)
                    true
                }
                R.id.ic_Report -> {
                    navigateToActivityLeft(Report_Activity::class.java)
                    true
                }
                R.id.ic_settings -> {
                    navigateToActivityRight(Settings::class.java)
                    true
                }
                else -> false
            }
        }

    }
    private fun navigateToActivityRight(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this, R.anim.slide_in_right, R.anim.slide_out_left
        )
        startActivity(intent, options.toBundle())
    }
    private fun navigateToActivityLeft(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this, R.anim.slide_in_left, R.anim.slide_out_right
        )
        startActivity(intent, options.toBundle())
    }

    fun ShowDialog(exerciseMoves: MutableList<Movement>){
        val dialog = Dialog(this@Add_Exercise)

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.attributes.windowAnimations= R.style.dialogAnimation
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.add_exercise_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window!!.setGravity(Gravity.BOTTOM)

        val btnAddMovement = dialog.findViewById<Button>(R.id.btnAddMovement_WD)
        val txtAddExercise: AutoCompleteTextView = dialog.findViewById(R.id.txtAddExercise_WD)
        val btnClose = dialog.findViewById<Button>(R.id.btnClose_WD)
        val txtSets: EditText = dialog.findViewById(R.id.txtSets_WD)
        val txtReps: EditText = dialog.findViewById(R.id.txtReps_WD)
        var arrMoves: MutableList<Movement> = mutableListOf()

        txtAddExercise.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {  // Start searching after 1 character
                    fetchExerciseSuggestions(s.toString())  // Fetch suggestions from the server
                }
            }

            private fun fetchExerciseSuggestions(search: String) {
                val moveAdapter = ArrayAdapter<Movement>(this@Add_Exercise, android.R.layout.simple_dropdown_item_1line, mutableListOf())
                // Start a coroutine in the Main (UI) thread
                CoroutineScope(Dispatchers.Main).launch {
                    val db = DbAccess.GetInstance()
                    val arrFetchMoves = db.SearchForMovements(search)
                    arrMoves = arrFetchMoves.toMutableList()
                    for (move in arrFetchMoves) {
                        moveAdapter.add(move)
                    }

                    moveAdapter.notifyDataSetChanged()
                    txtAddExercise.setAdapter(moveAdapter)
                    Log.d("FoodAdapter", "MoveAdapter after notify: ${moveAdapter.count}")
                    txtAddExercise.showDropDown()
                }
            }
        })

        txtAddExercise.setOnItemClickListener { parent, view, position, id ->
            // Get the index of the item clicked
            val itemIndex = position


            currentMove = arrMoves[itemIndex]

            val exercisepreviewAdapter = ArrayAdapter<String>(this@Add_Exercise, android.R.layout.simple_list_item_1, mutableListOf())

            for (move in exerciseMoves) {
                exercisepreviewAdapter.add(move.description)
            }

            //lvMealPreview.adapter = exercisepreviewAdapter

            txtAddExercise.text.clear()
            txtAddExercise.dismissDropDown()
        }

        btnAddMovement.setOnClickListener {
            exerciseMoves.add(currentMove)
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}