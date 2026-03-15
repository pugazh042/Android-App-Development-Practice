package com.example.firstapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val btnFontSize = findViewById<Button>(R.id.btnFontSize)
        val btnFontColor = findViewById<Button>(R.id.btnFontColor)
        val btnBgColor = findViewById<Button>(R.id.btnBgColor)
        val mainLayout = findViewById<LinearLayout>(R.id.main)

        // Font Size
        var fontSize = 20f
        btnFontSize.setOnClickListener {
            textView.textSize = fontSize
            fontSize += 5
            if (fontSize > 50) fontSize = 20f
        }

        // Font Color
        var fontColor = 0
        btnFontColor.setOnClickListener {
            when (fontColor % 5) {
                0 -> textView.setTextColor(Color.RED)
                1 -> textView.setTextColor(Color.YELLOW)
                2 -> textView.setTextColor(Color.BLUE)
                3 -> textView.setTextColor(Color.GREEN)
                4 -> textView.setTextColor(Color.MAGENTA)
            }
            fontColor++
        }

        // Background Color
        var bgColor = 0
        btnBgColor.setOnClickListener {
            when (bgColor % 5) {
                0 -> mainLayout.setBackgroundColor(Color.RED)
                1 -> mainLayout.setBackgroundColor(Color.YELLOW)
                2 -> mainLayout.setBackgroundColor(Color.BLUE)
                3 -> mainLayout.setBackgroundColor(Color.GREEN)
                4 -> mainLayout.setBackgroundColor(Color.MAGENTA)
            }
            bgColor++
        }
    }
}