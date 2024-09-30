# Limitless Fitness App

## Overview

The Limitless Fitness App is a comprehensive mobile application designed to help users track their fitness progress, set goals, and monitor nutrition intake. This Android app provides a range of features to support users in their fitness journey, including calorie counting, personalized goal setting, and exercise tracking.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Navigating the Menu](#navigating-the-menu)
- [Automated Testing](#automated-testing)
- [License](#license)



## Features

- **Calorie Counter**: Track daily calorie intake by scanning barcodes, searching for food, or adding custom meals.
- **Personalized Goals**: Set fitness goals such as weight loss or gain and specify a time period to achieve them.
- **Nutritional Tracking**: Monitor macronutrients (protein, fiber, carbs, fats) with values rounded to two decimal places.
- **Exercise Tracking**: Log and categorize various types of exercises.
- **Custom Workouts**: Add and manage custom workout routines.
- **Visual Progress**: Track fitness and nutritional progress with visual indicators and graphs.

## Usage
1. Launching the App
Open the project in Android Studio.
Sync the Gradle project.
Run the app on an Android emulator or a physical Android device.

2. Creating Custom Workout Plans
Navigate to the Workouts section of the app.
Tap on Create New Workout to start building your custom plan.
Name your workout and select the target muscle groups or fitness goals.
Add exercises to your plan:
Choose from the app's exercise database, or
Create custom exercises by tapping Add Custom Exercise.
Save your workout plan.
Access and start your custom workouts from the My Workouts list.

3. Adding Custom Food
Navigate to the Add Food section to input custom food items along with their nutritional values.

4. Setting Fitness Goals
On the first launch or within the Goals section, set your desired fitness goals:
Choose between Weight Loss or Weight Gain.
Specify the target period (e.g., weeks or months).


## Navigating the Menu

1. **Home Page**: 
    - Upon launching the app, you'll be taken to the **Login** page, where you can login in and access the main features of the application.
    
2. **Menu Items**:
    - **Home**: This page will show all the basic features of tracking the user stats.
    - **Nutrition**: This page is where you can add food to track calories.
    - **Exercise**: This page is where you can add Exercises to track your exercise routine.
    - **Settings**: The settings page is where the all the settings of the app are show and where users can personalize the app byu setting goals and light/dark mode.

3. **Follow the Prompts**: Each page provides prompts for interacting with the app. For example, in **Nutrition**, Click the add meal and fill in the form with your food details and submit.


## Automated Testing
We use GitHub Actions for automated testing and continuous integration. Our workflow automatically runs tests on every push or pull request to ensure that changes do not break the existing functionality.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

