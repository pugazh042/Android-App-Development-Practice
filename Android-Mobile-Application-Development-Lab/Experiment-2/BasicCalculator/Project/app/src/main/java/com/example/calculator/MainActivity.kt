package com.example.calculator

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val num1 = findViewById<EditText>(R.id.num1)
        val num2 = findViewById<EditText>(R.id.num2)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnSub = findViewById<Button>(R.id.btnSub)
        val btnMul = findViewById<Button>(R.id.btnMul)
        val btnDiv = findViewById<Button>(R.id.btnDiv)
        val btnMod = findViewById<Button>(R.id.btnMod)

        val tvResult = findViewById<TextView>(R.id.tvResult)

        fun getInputs(): Pair<Double, Double>? {
            val a = num1.text.toString()
            val b = num2.text.toString()

            if (a.isEmpty() || b.isEmpty()) {
                Toast.makeText(this, "Enter both numbers", Toast.LENGTH_SHORT).show()
                return null
            }

            return try {
                Pair(a.toDouble(), b.toDouble())
            } catch (e: Exception) {
                Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show()
                null
            }
        }

        btnAdd.setOnClickListener {
            getInputs()?.let { (a, b) ->
                tvResult.text = "Result: ${a + b}"
            }
        }

        btnSub.setOnClickListener {
            getInputs()?.let { (a, b) ->
                tvResult.text = "Result: ${a - b}"
            }
        }

        btnMul.setOnClickListener {
            getInputs()?.let { (a, b) ->
                tvResult.text = "Result: ${a * b}"
            }
        }

        btnDiv.setOnClickListener {
            getInputs()?.let { (a, b) ->
                if (b == 0.0) {
                    Toast.makeText(this, "Cannot divide by 0", Toast.LENGTH_SHORT).show()
                } else {
                    tvResult.text = "Result: ${a / b}"
                }
            }
        }

        btnMod.setOnClickListener {
            getInputs()?.let { (a, b) ->
                tvResult.text = "Result: ${a % b}"
            }
        }
    }
}