package com.lig.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_finish.*
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setSupportActionBar(toolbar_finish_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar_finish_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnFinish.setOnClickListener {
            finish() //kill the activity back to main activity
        }

        addDateToDatabase()
    }

    private fun addDateToDatabase(){
        val calendar = Calendar.getInstance()
        val dateTime = calendar.time               // get the date with defaut format
        Log.i("DATE:" , "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault()) // custom date format
        val date = sdf.format(dateTime)

        val dbHandler = sqliteOpenHelper(this, null)
        dbHandler.addDate(date)
        Log.i("DATE: ", "add date + $date")

    }
}