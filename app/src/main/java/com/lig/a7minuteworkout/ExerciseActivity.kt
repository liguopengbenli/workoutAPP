package com.lig.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {
    private var restTimer: CountDownTimer? = null
    private var exTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exProgress = 0
    private val exTimerDuration = 30
    private val restTimerDuration = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbar_exercise_activity)
        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbar_exercise_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()
    }

    override fun onDestroy() {
        if(restTimer !=null){
            restTimer!!.cancel()
            restProgress = 0
        }
        super.onDestroy()
    }

    private fun setRestProgressBar(){
        restProgressBar.progress = restProgress
        restTimer = object : CountDownTimer((restTimerDuration*1000).toLong(), 1000){
                override fun onTick(millisUntilFinished: Long) {
                    restProgress++
                    restProgressBar.progress = restTimerDuration - restProgress
                    tvTimer.text = (restTimerDuration-restProgress).toString()
                }

                override fun onFinish() {
                    Toast.makeText(this@ExerciseActivity, "Here now we will start exercise",Toast.LENGTH_SHORT).show()
                    setupExView()
                }
            }.start()
        }

    private fun setExProgressBar(){
        exProgressBar.progress = exProgress
        exTimer = object : CountDownTimer((exTimerDuration*1000).toLong(), 1000){
            override fun onTick(millisUntilFinished: Long) {
                exProgress++
                exProgressBar.progress = exTimerDuration - exProgress
                EXTimer.text = (exTimerDuration-exProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity, "Here now we get some rest",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun setupRestView(){
        llRestView.visibility = VISIBLE
        llExView.visibility = INVISIBLE
        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        setRestProgressBar()
    }

    private fun setupExView(){
        llRestView.visibility = INVISIBLE
        llExView.visibility = VISIBLE
        if(exTimer != null){
            exTimer!!.cancel()
            exProgress = 0
        }
        setExProgressBar()
    }

}