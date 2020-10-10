package com.lig.a7minuteworkout

import java.util.ArrayList

class Constants {

    companion object{
        fun defaultExerciseList():ArrayList<ExerciseModel> {
            val exciseList = ArrayList<ExerciseModel>()

            val jumpingJacks = ExerciseModel(1, "JumpingJacks", R.drawable.ic_jumping_jacks, false, false)
            exciseList.add(jumpingJacks)

            val wallSit = ExerciseModel(2, "WallSit", R.drawable.ic_wall_sit, false, false)
            exciseList.add(wallSit)

            val abdominalCrunch = ExerciseModel(3, "AbdominalCrunch", R.drawable.ic_abdominal_crunch, false, false)
            exciseList.add(abdominalCrunch)

            val highKneesRunning = ExerciseModel(4, "HighKneesRunning", R.drawable.ic_high_knees_running_in_place, false, false)
            exciseList.add(highKneesRunning)

            val lunge = ExerciseModel(5, "Lunge", R.drawable.ic_lunge, false, false)
            exciseList.add(lunge)

            val plank = ExerciseModel(6, "Plank", R.drawable.ic_plank, false, false)
            exciseList.add(plank)

            val pushUp = ExerciseModel(7, "PushUp", R.drawable.ic_push_up, false, false)
            exciseList.add(pushUp)

            val pushUpRotation = ExerciseModel(8, "PushUpRotation", R.drawable.ic_push_up_and_rotation, false, false)
            exciseList.add(pushUpRotation)

            val sidePlank = ExerciseModel(9, "SidePlank", R.drawable.ic_side_plank, false, false)
            exciseList.add(sidePlank)

            val squat = ExerciseModel(10, "Squat", R.drawable.ic_squat, false, false)
            exciseList.add(squat)

            val stepUpOntoChair = ExerciseModel(11, "StepUpOntoChair", R.drawable.ic_step_up_onto_chair, false, false)
            exciseList.add(stepUpOntoChair)

            val tricepsDipOnChair = ExerciseModel(12, "TricepsDipOnChair", R.drawable.ic_triceps_dip_on_chair, false, false)
            exciseList.add(tricepsDipOnChair)

            return exciseList
        }

    }


}