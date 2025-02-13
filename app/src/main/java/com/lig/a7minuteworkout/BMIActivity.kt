package com.lig.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_b_m_i.*
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"

    var currentVisibleView: String = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_m_i)

        setSupportActionBar(toolbar_bmi_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "CALCULATE BMI"
        toolbar_bmi_activity.setNavigationOnClickListener {
            onBackPressed()
        }

        btnCalculateUnits.setOnClickListener {
            if(currentVisibleView.equals(METRIC_UNITS_VIEW)){
                if(validateMetricUnits()){
                    val bmi = calculateBMI()
                    displayBMIResult(bmi)

                }else{
                    Toast.makeText(this@BMIActivity, "Please enter valid value", Toast.LENGTH_SHORT).show()
                }
            }

            else{
                if(validateUsUnits()){
                    val bmi = calculateUSBMI()
                    displayBMIResult(bmi)
                }

                else{
                    Toast.makeText(this@BMIActivity, "Please enter valid value", Toast.LENGTH_SHORT).show()

                }
            }
        }

        makeVisibleMetricUnitView()

        rgUnits.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.rbMetricsUnits){
                makeVisibleMetricUnitView()
            }else{
                makeVisibleUsUnitView()
            }
        }
    }

    private fun calculateBMI(): Float {
        //Formula: weight (kg) / [height (m)]2
        val heightValue: Float = etMetricUnitHeight.text.toString().toFloat() /100
        val weightValue: Float = etMetricUnitWeight.text.toString().toFloat()
        return (weightValue / (heightValue * heightValue))
    }

    private fun calculateUSBMI(): Float {
        val usUnitHeightValueFeet: String = etUsUnitHeightFeet.text.toString()
        val usUnitHeightValueInch: String = etUsUnitHeightInch.text.toString()
        val usUnitWeightValue: Float = etUsUnitWeight.text.toString().toFloat()

        val heightValue = usUnitHeightValueInch.toFloat() + usUnitHeightValueFeet.toFloat() * 12

        return 703 * (usUnitWeightValue / (heightValue * heightValue))
    }


    private fun makeVisibleUsUnitView(){
        currentVisibleView = US_UNITS_VIEW
        llMetricUnitsView.visibility = GONE
        llUsUnitsView.visibility = VISIBLE
        llDisplayBMIResult.visibility = GONE
    }

    private fun makeVisibleMetricUnitView(){
        currentVisibleView = METRIC_UNITS_VIEW
        llMetricUnitsView.visibility = VISIBLE
        llUsUnitsView.visibility = GONE
        llDisplayBMIResult.visibility = GONE //important using Gone to make space for layout
        //clear text
    }

    private fun displayBMIResult(bmi: Float){
        val bmiLabel:String
        val bmiDescription:String

        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0)
        {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0)
        {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take care of your better! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0)
        {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0)
        {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0)
        {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0)
        {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        llDisplayBMIResult.visibility = VISIBLE

        val bmiValue : String = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString() // to limit much short number in float
        tvBMIValue.text = bmiValue
        tvBMIType.text = bmiLabel
        tvBMIDesciption.text = bmiDescription
    }

    private fun validateMetricUnits(): Boolean{
        var isValid = true
        if (etMetricUnitWeight.text.toString().isEmpty() || etMetricUnitHeight.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }

    private fun validateUsUnits(): Boolean{
        var isValid = true
        if (etUsUnitWeight.text.toString().isEmpty() || etUsUnitHeightFeet.text.toString().isEmpty()  || etUsUnitHeightInch.text.toString().isEmpty()){
            isValid = false
        }
        return isValid
    }


}