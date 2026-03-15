package com.example.bmicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etWeight = findViewById<EditText>(R.id.etWeight)
        val etHeight = findViewById<EditText>(R.id.etHeight)
        val btnCalc = findViewById<Button>(R.id.btnCalc)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnCalc.setOnClickListener {

            val weightText = etWeight.text.toString()
            val heightText = etHeight.text.toString()

            if (weightText.isEmpty() || heightText.isEmpty()) {
                tvResult.text = "Please enter weight and height"
                return@setOnClickListener
            }

            val weight = weightText.toFloat()
            val heightCm = heightText.toFloat()
            val heightM = heightCm / 100

            val bmi = weight / (heightM * heightM)

            val category = when {
                bmi < 18.5 -> "Underweight"
                bmi < 25 -> "Normal"
                bmi < 30 -> "Overweight"
                else -> "Obese"
            }

            tvResult.text = "BMI: %.2f\nCategory: %s".format(bmi, category)
        }
    }
}