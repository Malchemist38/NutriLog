NutriLog
-
NutriLog is an Android application designed to help users track their daily calorie intake, calories burned through exercises, and progress toward fitness goals. It provides features for logging food and exercise, updating goals, and reviewing progress with a calendar interface.

Features
-
Track daily calorie consumption and burned calories.

Set and update fitness goals.

View progress and goal conformance using a calendar.

Clear all stored data when needed (this was primarily for debugging but has been left in for QoL)

Prerequisites
Android Studio,
Java Development Kit (JDK),
Android SDK (Ensure the required SDK tools and libraries are installed in Android Studio),
Git

Installation Instructions
-
1. Clone the Repository
Use the following command to clone the repository to your local machine:

```bash
git clone https://github.com/Malchemist38/NutriLog.git
```

2. Open in Android Studio
Launch Android Studio.
Click on File > Open.
Navigate to the folder where you cloned the repository.
Select the project directory and click OK.

3. Setup
Setting up a run configuration, JDK, and gradle sync generally happens automatically if you have used Android Studio before and have ensured that you have opened the project at the root of the directory hierarchy (when unzipping, sometimes Windows will create extra layers of folders that can cause issues)

4. Sanity Check
Start an emulator (I used the generic medium phone with the default API)
Click the Run button (green play icon) in Android Studio.
Select your device or emulator to install and run the app.
Directory Structure (the most pertinent files are listed)

```bash
NutriLog/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/edu/utsa/cs3443/nutrilog/
│   │   │   │   ├── MainActivity.java
│   │   │   │   ├── CalorieTrackerActivity.java
│   │   │   │   ├── ExerciseTrackerActivity.java
│   │   │   │   ├── ProgressTrackerActivity.java
│   │   │   │   ├── UpdateGoalsActivity.java
│   │   │   │   ├── DatabaseHelper.java
│   │   │   ├── res/
│   │   │   │   ├── layout/  (XML layouts for UI)
│   │   │   ├── AndroidManifest.xml
├── build.gradle
```
Usage
-
Calorie Tracker: Log food items and calories consumed.

Exercise Tracker: Log exercises and calories burned.
Progress Tracker: View progress and goal conformance using a calendar.

Update Goals: Set daily calorie and exercise goals.

Troubleshooting
-
Gradle Sync Issues:

You can try running ./gradlew build from the terminal (will need Git Bash for Windows). Keep in mind you will need to be in the project directory where this file is located.

Emulator Errors:

Check that the emulator has enough allocated RAM.
Ensure the emulator supports the app's minimum API level.
Dependency Issues:

Open the Build menu in Android Studio and select Clean Project and Rebuild Project.

License

This project is licensed by nobody. Who would ever want their license on such a silly project?
