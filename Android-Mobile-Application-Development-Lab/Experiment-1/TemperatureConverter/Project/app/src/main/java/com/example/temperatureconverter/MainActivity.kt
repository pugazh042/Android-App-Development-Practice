package com.example.temperatureconverter

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etTemp = findViewById<EditText>(R.id.etTemp)
        val spFrom = findViewById<Spinner>(R.id.spFrom)
        val spTo = findViewById<Spinner>(R.id.spTo)
        val btnConvert = findViewById<Button>(R.id.btnConvert)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        val units = arrayOf("Celsius", "Fahrenheit", "Kelvin")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, units)
        spFrom.adapter = adapter
        spTo.adapter = adapter

        btnConvert.setOnClickListener {

            val tempText = etTemp.text.toString()

            if (tempText.isEmpty()) {
                tvResult.text = "Enter temperature"
                return@setOnClickListener
            }

            val temp = tempText.toDouble()
            val from = spFrom.selectedItem.toString()
            val to = spTo.selectedItem.toString()

            var result = temp

            if (from == "Celsius" && to == "Fahrenheit")
                result = (temp * 9 / 5) + 32

            else if (from == "Fahrenheit" && to == "Celsius")
                result = (temp - 32) * 5 / 9

            else if (from == "Celsius" && to == "Kelvin")
                result = temp + 273.15

            else if (from == "Kelvin" && to == "Celsius")
                result = temp - 273.15

            else if (from == "Fahrenheit" && to == "Kelvin")
                result = (temp - 32) * 5 / 9 + 273.15

            else if (from == "Kelvin" && to == "Fahrenheit")
                result = (temp - 273.15) * 9 / 5 + 32

            tvResult.text = "Result: %.2f".format(result)
        }
    }
}