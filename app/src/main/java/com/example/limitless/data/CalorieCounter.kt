package com.example.limitless.data

class CalorieCounter(var calorieWallet: Double?) {
    var calories = 0
    var arrMeals: MutableList<Meal>? = null

    fun CreateMeal(foods: List<Food>){
        val dbAccess = DbAccess.GetInstance()


    }

    fun ChangeWallet(newWallet: Double){
        calorieWallet = newWallet
    }

    fun CalculateTotalCalories(): Double{
        var totalCalories = 0.0

        if(arrMeals != null){
            for(meal in arrMeals!!){
                var mealCalories = 0.0

                for(food in meal.foods){
                    mealCalories += food.calories
                }

                totalCalories += mealCalories
            }
        }

        return totalCalories
    }

    fun GetTotalCarbs(): Double{
        var totalCarbs = 0.0

        if(arrMeals != null){
            for(meal in arrMeals!!){
                var mealCarbs = 0.0

                for(food in meal.foods){
                    mealCarbs += food.carbohydrates!!
                }

                totalCarbs += mealCarbs
            }
        }

        return totalCarbs
    }

    fun GetTotalFat(): Double {
        var totalFats = 0.0

        if (arrMeals != null) {
            for (meal in arrMeals!!) {
                var mealFats = 0.0

                for (food in meal.foods) {
                    mealFats += food.fat!!
                }

                totalFats += mealFats
            }
        }

        return totalFats
    }

    fun GetTotalProtein(): Double {
        var totalProteins = 0.0

        if (arrMeals != null) {
            for (meal in arrMeals!!) {
                var mealProteins = 0.0

                for (food in meal.foods) {
                    mealProteins += food.protein!!
                }

                totalProteins += mealProteins
            }
        }

        return totalProteins
    }

    fun GetTotalFibre(): Double {
        var totalFibre = 0.0

        if (arrMeals != null) {
            for (meal in arrMeals!!) {
                var mealFibre = 0.0

                for (food in meal.foods) {
                    mealFibre += food.fibre!!
                }

                totalFibre += mealFibre
            }
        }

        return totalFibre
    }

}