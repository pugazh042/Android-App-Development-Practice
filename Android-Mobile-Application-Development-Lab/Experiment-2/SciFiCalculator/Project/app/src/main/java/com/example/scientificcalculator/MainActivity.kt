package com.example.scientificcalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvExpression = findViewById(R.id.tvExpression)
        tvResult = findViewById(R.id.tvResult)

        val buttons = listOf(
            R.id.btn0,R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,
            R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9,
            R.id.btnAdd,R.id.btnMinus,R.id.btnMul,R.id.btnDiv,
            R.id.btnDot,R.id.btnBraceOpen,R.id.btnBraceClose,
            R.id.btnSin,R.id.btnCos,R.id.btnTan,R.id.btnLog,
            R.id.btnLn,R.id.btnSqrt,R.id.btnPower,R.id.btnPi
        )

        for (id in buttons) {
            val btn = findViewById<Button>(id)
            btn.setOnClickListener {
                tvExpression.append(btn.text.toString())
            }
        }

        findViewById<Button>(R.id.btnAC).setOnClickListener {
            tvExpression.text = ""
            tvResult.text = ""
        }

        findViewById<Button>(R.id.btnC).setOnClickListener {
            val exp = tvExpression.text.toString()
            if (exp.isNotEmpty()) {
                tvExpression.text = exp.substring(0, exp.length - 1)
            }
        }

        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            try {
                val result = evaluate(tvExpression.text.toString())
                tvResult.text = result.toString()
            } catch (e: Exception) {
                tvResult.text = "Error"
            }
        }
    }

    private fun evaluate(expression: String): Double {
        return object {

            var pos = -1
            var ch = 0

            fun nextChar() {
                ch = if (++pos < expression.length) expression[pos].code else -1
            }

            fun eat(charToEat: Int): Boolean {
                while (ch == ' '.code) nextChar()
                if (ch == charToEat) {
                    nextChar()
                    return true
                }
                return false
            }

            fun parse(): Double {
                nextChar()
                val x = parseExpression()
                if (pos < expression.length) throw RuntimeException()
                return x
            }

            fun parseExpression(): Double {
                var x = parseTerm()
                while (true) {
                    if (eat('+'.code)) x += parseTerm()
                    else if (eat('-'.code)) x -= parseTerm()
                    else return x
                }
            }

            fun parseTerm(): Double {
                var x = parseFactor()
                while (true) {
                    if (eat('*'.code)) x *= parseFactor()
                    else if (eat('/'.code)) x /= parseFactor()
                    else return x
                }
            }

            fun parseFactor(): Double {

                if (eat('+'.code)) return parseFactor()
                if (eat('-'.code)) return -parseFactor()

                var x: Double
                val startPos = pos

                if (eat('('.code)) {
                    x = parseExpression()
                    eat(')'.code)
                }

                else if (ch in '0'.code..'9'.code || ch == '.'.code) {
                    while (ch in '0'.code..'9'.code || ch == '.'.code) nextChar()
                    x = expression.substring(startPos, pos).toDouble()
                }

                else if (ch in 'a'.code..'z'.code || ch == 'π'.code || ch == '√'.code) {

                    while (ch in 'a'.code..'z'.code || ch == 'π'.code || ch == '√'.code) nextChar()
                    val func = expression.substring(startPos, pos)

                    if (func == "π") {
                        x = PI
                    } else {
                        x = parseFactor()
                        x = when (func) {
                            "sin" -> sin(Math.toRadians(x))
                            "cos" -> cos(Math.toRadians(x))
                            "tan" -> tan(Math.toRadians(x))
                            "log" -> log10(x)
                            "ln" -> ln(x)
                            "√" -> sqrt(x)
                            else -> throw RuntimeException()
                        }
                    }
                }

                else {
                    throw RuntimeException()
                }

                if (eat('^'.code)) x = x.pow(parseFactor())

                return x
            }

        }.parse()
    }
}