package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var currentInput = StringBuilder()
    private var currentOperator: String? = null
    private var firstOperand: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setDigitButtonsClickListeners()
        setOperatorButtonsClickListeners()
        setEqualsButtonClickListener()
        setClearButtonClickListener()
    }

    private fun setDigitButtonsClickListeners() {
        val digitButtons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )

        digitButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                appendDigit(index)
            }
        }
    }

    private fun appendDigit(digit: Int) {
        currentInput.append(digit)
        updateInputDisplay()
    }

    private fun setOperatorButtonsClickListeners() {
        val operatorButtons = listOf(
            binding.btnPlus, binding.btnSubstract, binding.btnMultiply, binding.btnDivide
        )

        operatorButtons.forEach { button ->
            button.setOnClickListener {
                handleOperator(button.text.toString())
            }
        }
    }

    private fun handleOperator(operator: String) {
        if (currentOperator != null) {
            calculateResult()
        }
        currentOperator = operator
        firstOperand = currentInput.toString().toDouble()
        currentInput.clear()
    }

    private fun calculateResult() {
        if (currentOperator != null && currentInput.isNotEmpty()) {
            val secondOperand = currentInput.toString().toDouble()
            when (currentOperator) {
                "+" -> firstOperand += secondOperand
                "-" -> firstOperand -= secondOperand
                "*" -> firstOperand *= secondOperand
                "/" -> {
                    if (secondOperand != 0.0) {
                        firstOperand /= secondOperand
                    }
                }
            }
            currentInput.clear()
            currentInput.append(firstOperand)
            currentOperator = null
            updateInputDisplay()
            Log.d("Calculator", "Result: $firstOperand")
        }
    }


    private fun setEqualsButtonClickListener() {
        binding.btnEquals.setOnClickListener {
            if (currentOperator != null) {
                calculateResult()
            }
        }
    }

    private fun setClearButtonClickListener() {
        binding.btnClear.setOnClickListener {
            currentInput.clear()
            currentOperator = null
            firstOperand = 0.0
            updateInputDisplay()
        }
    }

    private fun updateInputDisplay() {
        binding.editText.setText(currentInput.toString())
    }

}
