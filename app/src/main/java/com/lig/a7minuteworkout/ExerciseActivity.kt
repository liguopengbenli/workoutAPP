package com.lig.a7minuteworkout

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var restTimer: CountDownTimer? = null
    private var exTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exProgress = 0
    private val exTimerDuration = 30
    private val restTimerDuration = 10

    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null
    private var exerciseAdapter: ExerciseStatusAdapter? = null

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

        tts = TextToSpeech(this, this)

        exerciseList = Constants.defaultExerciseList()
        setupRestView()

        setupExerciseStatusRecyclerView()
    }

    override fun onDestroy() {
        if(restTimer !=null){
            restTimer!!.cancel()
            restProgress = 0
        }
        if(exTimer != null){
            exTimer!!.cancel()
            exProgress = 0
        }
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!= null){
            player!!.stop()
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
                    currentExercisePosition++
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
                if(currentExercisePosition < exerciseList!!.size - 1){
                    setupRestView()
                }else{
                    Toast.makeText(this@ExerciseActivity, "You have completed workout",Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun setupRestView(){
        llRestView.visibility = VISIBLE
        llExView.visibility = INVISIBLE

        try {
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping = false
            player!!.start()

        }catch (e: Exception){
            e.printStackTrace()
        }


        if(restTimer != null){
            restTimer!!.cancel()
            restProgress = 0
        }
        tvUpComingExName.text = "${exerciseList!![currentExercisePosition+1].name}"
        setRestProgressBar()
    }

    private fun setupExView(){
        llRestView.visibility = INVISIBLE
        llExView.visibility = VISIBLE
        if(exTimer != null){
            exTimer!!.cancel()
            exProgress = 0
        }
        speakOut(exerciseList!![currentExercisePosition].name)
        setExProgressBar()

        ivImage.setImageResource(exerciseList!![currentExercisePosition].image)
        exName.text = exerciseList!![currentExercisePosition].name
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.US)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "The language specified is not supported")
            }
        }else{
            Log.e("TTS", "Initilisation failed")

        }
    }

    private fun speakOut(text : String){
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    private fun setupExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!, this)
        rvExerciseStatus.adapter = exerciseAdapter

    }

}