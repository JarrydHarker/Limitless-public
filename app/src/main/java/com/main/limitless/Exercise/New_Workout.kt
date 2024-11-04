package com.main.limitless.Exercise

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
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isNotEmpty
import com.main.limitless.Nutrition.Diet_Activity
import com.main.limitless.MainActivity
import com.main.limitless.R
import com.main.limitless.Settings
import com.main.limitless.activityViewModel
import com.main.limitless.data.Cardio
import com.main.limitless.data.Exercise
import com.main.limitless.data.Movement
import com.main.limitless.data.Strength
import com.main.limitless.data.dbAccess
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.main.limitless.data.offline.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Locale

class New_Workout : AppCompatActivity() {

    var currentMove = Movement()
    val workoutExercises : MutableList<Exercise> = mutableListOf()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_new_workout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val btnCreateExercise = findViewById<Button>(R.id.btnCreateExercise_AE)
       // val spinCategory: Spinner  = findViewById(R.id.spinCategory)
        val lvNewWorkout: ListView = findViewById(R.id.lvNewWorkout)
        val newWorkoutAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line)
        val btnAddWorkout: Button = findViewById(R.id.btnAddWorkout_AE)
        val txtName: TextView = findViewById(R.id.txtName)
        val workoutId = intent.getIntExtra("workoutId", -1)
        val currentWorkout = activityViewModel.GetWorkout(workoutId)
        val backimg: ImageView = findViewById(R.id.imgback)

        backimg.setOnClickListener{
            val intent = Intent(this, Exercise_Activity::class.java)
            startActivity(intent)
        }

        if(currentWorkout == null){
            Toast.makeText(this, getString(R.string.invalid_workout), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Exercise_Activity::class.java)
            startActivity(intent)
        }

        txtName.setText(currentWorkout?.name)
        lvNewWorkout.adapter = newWorkoutAdapter

        btnCreateExercise.setOnClickListener {
            ShowDialog{
                newWorkoutAdapter.clear()
                for(we in workoutExercises){
                    newWorkoutAdapter.add(we.toString())
                }
                newWorkoutAdapter.notifyDataSetChanged()
            }
        }

        btnAddWorkout.setOnClickListener{
            if(lvNewWorkout.isNotEmpty())
            {
                currentWorkout!!.AddExercises(workoutExercises)

                activityViewModel.SaveWorkout(currentWorkout)

                GlobalScope.launch(Dispatchers.IO) {
                    UpdateOfflineDb()
                }
                val intent = Intent(this, Exercise_Activity::class.java)
                startActivity(intent)
            }
            else
            {
                Toast.makeText(this,
                    getString(R.string.please_add_new_exercises_before_adding_a_workout), Toast.LENGTH_SHORT).show()
            }
        }

        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)

        bottomNavBar.setSelectedItemId(R.id.ic_workouts)
        bottomNavBar.setOnNavigationItemSelectedListener{item ->
            when (item.itemId){
                R.id.ic_home -> {
                    navigateToActivityRight(MainActivity::class.java)
                    true
                }
                R.id.ic_nutrition -> {
                    navigateToActivityRight(Diet_Activity::class.java)
                    true
                }

                R.id.ic_settings -> {
                    navigateToActivityRight(Settings::class.java)
                    true
                }
                R.id.ic_workouts -> {
                    startActivity(Intent(this, Exercise_Activity::class.java))
                    true
                }
                else -> false
            }
        }

    }

    suspend fun UpdateOfflineDb() {
        val offlineDB = AppDatabase.getDatabase(this)

        for(workout in activityViewModel.arrWorkouts){
            if(!offlineDB.workoutDao().getAllWorkouts().contains(workout)){
                offlineDB.workoutDao().insert(workout)

                for(exercise in workout.arrExercises){
                    offlineDB.exerciseDao().insert(exercise)

                    if(exercise.cardio != null){
                        offlineDB.cardioDao().insert(exercise.cardio!!)
                    }else {
                        offlineDB.strengthDao().insert(exercise.strength!!)
                    }
                }
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

    fun ShowDialog(onComplete: () -> Unit){
        val dialog = Dialog(this@New_Workout)

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
        val txtName: TextView = dialog.findViewById(R.id.txtName)
        val txtType: TextView = dialog.findViewById(R.id.txtType)
        val txtBodypart: TextView = dialog.findViewById(R.id.txtBodypart)
        val txtEquipment: TextView = dialog.findViewById(R.id.txtEquipment)
        val txtDifficulty: TextView = dialog.findViewById(R.id.txtDifficulty)

        txtName.setText("-")
        txtType.setText("-")
        txtBodypart.setText("-")
        txtEquipment.setText("-")
        txtDifficulty.setText("-")

        txtAddExercise.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()) {  // Start searching after 1 character
                    fetchExerciseSuggestions(s.toString())  // Fetch suggestions from the server
                }
            }

            private fun fetchExerciseSuggestions(search: String) {
                val moveAdapter = ArrayAdapter<String>(this@New_Workout, android.R.layout.simple_dropdown_item_1line, mutableListOf())
                // Start a coroutine in the Main (UI) thread
                CoroutineScope(Dispatchers.Main).launch {
                    val arrFetchMoves = dbAccess.SearchForMovements(search)
                    arrMoves = arrFetchMoves.toMutableList()
                    for (move in arrFetchMoves) {
                        moveAdapter.add(move.name)
                    }

                    if (moveAdapter.isEmpty) {
                        moveAdapter.add("No exercise found") // Show no results
                    }

                    Log.d("MoveAdapter", "arrMoves: ${arrFetchMoves.size}")

                    moveAdapter.notifyDataSetChanged()
                    txtAddExercise.setAdapter(moveAdapter)
                    Log.d("MoveAdapter", "MoveAdapter after notify: ${moveAdapter.count}")
                    txtAddExercise.showDropDown()
                }
            }
        })

        txtAddExercise.setOnItemClickListener { parent, view, position, id ->
            // Get the index of the item clicked
            val itemIndex = position
            currentMove = arrMoves[itemIndex]

            Log.d("Fuck", "MovementID: ${currentMove.movementId}")

            val exercisepreviewAdapter = ArrayAdapter<String>(this@New_Workout, android.R.layout.simple_list_item_1, mutableListOf())

            for (exercise in workoutExercises) {
                exercisepreviewAdapter.add(exercise.GetName())
            }

            txtName.setText(currentMove.name)
            txtType.setText(currentMove.type)
            txtBodypart.setText(currentMove.bodypart)
            txtEquipment.setText(currentMove.equipment)
            txtDifficulty.setText(currentMove.difficultyLevel)

            txtAddExercise.dismissDropDown()
        }

        btnAddMovement.setOnClickListener {
            val sets = txtSets.text.toString()
            val reps = txtReps.text.toString()

            if(txtAddExercise.text.isEmpty()){
                Toast.makeText(this@New_Workout,
                    getString(R.string.please_select_an_exercise_to_add_to_your_workout), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val exercise = Exercise(0, currentMove.movementId!!)
            exercise.movement = currentMove

            if (currentMove.type.lowercase(Locale.getDefault()) == "cardio") {
                exercise.cardio = Cardio()
            } else {
                if (sets.isEmpty()) {
                    Toast.makeText(
                        this@New_Workout,
                        getString(R.string.please_enter_the_number_of_sets_for_this_exercise),
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                if (reps.isEmpty()) {
                    Toast.makeText(
                        this@New_Workout,
                        getString(R.string.please_enter_the_number_of_reps_for_this_exercise),
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }

                exercise.strength = Strength(sets = sets.toInt(), repetitions = reps.toInt())
            }

            workoutExercises.add(exercise)
            dialog.dismiss()
            onComplete()
        }

        btnClose.setOnClickListener {
            dialog.dismiss()
            onComplete()
        }
        dialog.show()
    }
}