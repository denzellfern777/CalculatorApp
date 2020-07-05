package com.app.myapplication

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private var displayText: String = ""
    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false
    private var isEqualsPressed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        if (isEqualsPressed) {
            input.text = ((view as TextView).text)
            result.text = ""
            isEqualsPressed = false
            defaultInputStyle()
        } else {
            if (isOperatorAdded(input.text.toString())) {
                input.append((view as TextView).text)
                val tvValue = input.text.toString()
                calculate(tvValue)
            } else {
                input.append((view as TextView).text)
            }
        }
        lastNumeric = true

        if (input.length() >= 9) {
            input.textSize = 30F
        }
    }

    fun onAllClear(view: View) {
        input.text = ""
        result.text = ""
        defaultInputStyle()
        lastNumeric = false
    }

    fun onClear(view: View) {
        defaultInputStyle()
        val inputValue = input.text.toString()
        if (!TextUtils.isEmpty(inputValue)) {
            displayText = inputValue
            val sub = displayText.substring(0, displayText.length - 1)
            input.text = sub

            if (isOperatorAdded(input.text.toString())) {
                val tvValue = input.text.toString()
                calculate(tvValue)
            }
        }
        if (TextUtils.isEmpty(input.text.toString())) {
            lastNumeric = false
        }

        if (input.length() >= 14) {
            input.textSize = 30F
        }
    }

    fun onDecimalPoint(view: View) {
        if (!input.text.contains(".")) {
            if (lastNumeric) {
                input.append(".")
            }
        }
    }

    fun onOperator(view: View) {
        if (!isEqualsPressed) {
            if (lastNumeric && !isOperatorAdded(input.text.toString())) {
                input.append((view as TextView).text)
                lastNumeric = false
                lastDot = false
            }
        } else {
            defaultInputStyle()
            input.text = result.text.toString()
            input.append((view as TextView).text)
            result.text = ""
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("÷") || value.contains("+")
                    || value.contains("×") || value.contains("−")
        }
    }

    private fun calculate(tvValue: String) {
        var operator = ""
        when {
            tvValue.contains("÷") -> {
                operator = "÷"
            }
            tvValue.contains("+") -> {
                operator = "+"
            }
            tvValue.contains("×") -> {
                operator = "×"
            }
            tvValue.contains("−") -> {
                operator = "−"
            }
        }
        val format = DecimalFormat("0.#")

        val splitValue = tvValue.split(operator)
        val valueOne = splitValue[0]
        val valueTwo = splitValue[1]

        if (!TextUtils.isEmpty(valueTwo)) {

            when (operator) {
                "+" -> result.text =
                    format.format((valueOne.toDouble() + valueTwo.toDouble())).toString()
                "−" -> result.text =
                    format.format((valueOne.toDouble() - valueTwo.toDouble())).toString()
                "×" -> result.text =
                    format.format((valueOne.toDouble() * valueTwo.toDouble())).toString()
                "÷" -> result.text =
                    format.format((valueOne.toDouble() / valueTwo.toDouble())).toString()
            }
        } else {
            result.text = ""
        }
    }

    fun onEquals(view: View) {
        if (!TextUtils.isEmpty(input.text.toString())) {
            input.setTextColor(ContextCompat.getColor(this,R.color.colorDarkGrey))
            if (input.length() >= 9) {
                input.textSize = 30F
            } else {
                input.textSize = 45F
            }
            result.setTextColor(ContextCompat.getColor(this,R.color.colorWhite))
            result.textSize = 80F
            isEqualsPressed = true
        }
    }

    private fun defaultInputStyle() {
        result.setTextColor(ContextCompat.getColor(this, R.color.colorDarkGrey))
        result.textSize = 45F
        input.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))

        if (input.length() >= 9) {
            input.textSize = 30F
        } else {
            input.textSize = 80F
        }
        isEqualsPressed = false
    }
}
