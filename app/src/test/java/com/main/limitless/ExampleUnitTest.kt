package com.main.limitless

import com.main.limitless.data.DbAccess
import com.main.limitless.data.Food
import com.main.limitless.data.Meal
import com.main.limitless.data.Ratios
import com.main.limitless.data.User
import com.main.limitless.data.ViewModels.ActivityViewModel
import com.main.limitless.data.ViewModels.NutritionViewModel
import com.main.limitless.data.Workout
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import java.time.LocalDate

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {
    private lateinit var viewModel: NutritionViewModel
    private lateinit var dbAccess: DbAccess
    private lateinit var currentUser: User
    private lateinit var currentDate: LocalDate
    private lateinit var ratio: Ratios

    @Before
    fun setUp() {
        dbAccess = mock(DbAccess::class.java)
        currentUser = User("123", "John", "Doe", "john.doe@example.com", "password123")
        currentDate = LocalDate.now()
        ratio = Ratios(protein = 0.35, carbs = 0.45, fibre = 0.014, fat = 0.20)
        viewModel = NutritionViewModel(currentDate, 2000.0, ratio)
    }

    @Test
    fun testCreateMeal() {
        val meal = Meal(date = currentDate, userId = currentUser.userId, name = "Breakfast")
        viewModel.CreateMeal(meal)

        assertNotNull(viewModel.arrMeals)
        assertEquals(1, viewModel.arrMeals!!.size)
        assertEquals(meal, viewModel.arrMeals!![0])
    }

    @Test
    fun testChangeWallet() {
        viewModel.ChangeWallet(1500.0)
        assertEquals(1500.0, viewModel.calorieWallet, 0.0)
    }

    @Test
    fun testCalculateTotalCalories() {
        val foods = listOf(Food(description = "Cheese,Feta", calories = 264))
        val meal = Meal(date = currentDate, userId = currentUser.userId, name = "Breakfast")
        meal.arrFoods.addAll(foods)
        val meals = listOf(meal)
        viewModel.arrMeals = meals.toMutableList()

        val totalCalories = viewModel.CalculateTotalCalories()
        assertEquals(264.0, totalCalories, 0.0)
    }

    @Test
    fun testGetTotalCarbs() {
        val foods = listOf(Food(description = "Apple", carbohydrates = 14.0))
        val meal = Meal(date = currentDate, userId = currentUser.userId, name = "Breakfast")
        meal.arrFoods.addAll(foods)
        val meals = listOf(meal)
        viewModel.arrMeals = meals.toMutableList()

        val totalCarbs = viewModel.GetTotalCarbs()
        assertEquals(14.0, totalCarbs, 0.0)
    }


    @Test
    fun testGetTotalFat() {
        val foods = listOf(Food(description = "Apple", fat = 0.2))
        val meal = Meal(date = currentDate, userId = currentUser.userId, name = "Breakfast")
        meal.arrFoods.addAll(foods)
        val meals = listOf(meal)
        viewModel.arrMeals = meals.toMutableList()

        val totalFat = viewModel.GetTotalFat()
        assertEquals(0.2, totalFat, 0.0)
    }

    @Test
    fun testGetTotalProtein() {
        val foods = listOf(
            Food(5, protein = 23.24),
            Food(61, protein = 1.0)
        )
        val meal = Meal(date = LocalDate.now(), userId = "user123", name = "Lunch")
        meal.arrFoods.addAll(foods)
        val meals = listOf(meal)
        viewModel.arrMeals = meals.toMutableList()

        val totalProtein = viewModel.GetTotalProtein()
        assertEquals(24.24, totalProtein, 0.0)
    }

    @Test
    fun testGetTotalFibre() {
        val foods = listOf(
            Food(foodId = 3, fibre = 2.6),
            Food(foodId = 4, fibre = 4.4)
        )
        val meal = Meal(date = LocalDate.now(), userId = "user123", name = "Dinner")
        meal.arrFoods.addAll(foods)
        val meals = listOf(meal)
        viewModel.arrMeals = meals.toMutableList()

        val totalFibre = viewModel.GetTotalFibre()
        assertEquals(7.0, totalFibre, 0.0)
    }



    @Test
    fun testGetWorkouts() {
        // Create the ViewModel
        val viewModel = ActivityViewModel(LocalDate.now())

        // Create a workout
        val workout = Workout(
            workoutId = 1,
            date = LocalDate.now().toString(),
            name = "Morning Workout"
        )

        // Add the workout to the list
        viewModel.arrWorkouts = mutableListOf(workout)

        // Get the workouts
        val workouts = viewModel.GetWorkouts()

        // Verify that the workouts list is not null and contains the workout
        assertNotNull(workouts)
        assertEquals(1, workouts.size)
        assertEquals(workout, workouts.get(0))
    }






}