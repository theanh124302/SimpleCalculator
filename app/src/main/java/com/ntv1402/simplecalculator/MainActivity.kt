package com.ntv1402.simplecalculator

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ntv1402.simplecalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var firstNumber: Int = 0;
    private var secondNumber: String = "";
    private var result: Float = 0f;
    private var operator: String = "";
    private var listNumberBtn = mutableListOf<Button>();
    private var listOperatorBtn = mutableListOf<Button>();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listNumberBtn.addAll(listOf<Button>(binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4, binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9));
        listOperatorBtn.addAll(listOf<Button>(binding.btnCong, binding.btnTru, binding.btnNhan, binding.btnChia));
        initButtonClick();
    }

    private fun initButtonClick() {
        binding.btnCe.setOnClickListener{_ ->
            firstNumber = 0;
            secondNumber = "";
            result = 0f;
            operator = "";
            updateHistoryTextView();
        }

        binding.btnC.setOnClickListener{ _ ->
            firstNumber = 0;
            secondNumber = "";
            result = 0f;
            operator = "";
            updateHistoryTextView();
        }

        binding.btnBs.setOnClickListener{_ ->
            if(firstNumber.toString() != "" && operator == "") {
                try {
                    var string = binding.tvPheptinh.text.toString();
                    string = string.substring(0, string.length - 1);
                    if(string.isEmpty()) {
                        string = "0";
                        firstNumber = 0;
                        secondNumber= "";
                    } else {
                        secondNumber = string;
                    }

                    updateHistoryTextView()
                } catch (e: Exception) {
                }
                return@setOnClickListener;
            }

            if(operator != "" && secondNumber == "") {
                return@setOnClickListener;
            }

            if(secondNumber != "" && operator != "") {
                var string = secondNumber;
                string = string.substring(0, string.length - 1);
                secondNumber = string;
                updateHistoryTextView();
                return@setOnClickListener;
            }


        }

        listNumberBtn.forEachIndexed{ number, button ->
            button.setOnClickListener {
                if (secondNumber != "" && number == 0) {
                    secondNumber += number.toString();
                }
                if(number != 0) {
                    secondNumber += number.toString();
                }
                updateHistoryTextView();
            }
        };

        listOperatorBtn.forEachIndexed{ _, button ->
            button.setOnClickListener {
                setupMathOperator(button.text.toString());
            }
        };

        binding.btnTinh.setOnClickListener {
            try {
                if (operator != "") {
                    when (operator) {
                        "+" -> result = (firstNumber + secondNumber.toInt()).toFloat();
                        "-" -> result = (firstNumber - secondNumber.toInt()).toFloat();
                        "X" -> result = (firstNumber * secondNumber.toInt()).toFloat();
                        "/" -> result = (firstNumber / secondNumber.toInt()).toFloat();
                    }
                } else {
                    result = 0f;
                }
            } catch(e: Exception) {
                result = 0f;
            }
            updateResultTextView();
        }
    }

    private fun setupMathOperator(type: String) {
        if (operator == "") {
            try{
                firstNumber = secondNumber.toInt();
            }catch(e: Exception) {
                firstNumber = 0;
            }
            secondNumber = "";
            operator = type;
            updateHistoryTextView();
            return;
        }

        if (secondNumber != "") {
            try {
                when (operator) {
                    "+" -> firstNumber += secondNumber.toInt();
                    "-" -> firstNumber -= secondNumber.toInt();
                    "X" -> firstNumber *= secondNumber.toInt();
                    "/" -> firstNumber /= secondNumber.toInt();
                }

                secondNumber = "";
                operator = type;
                updateHistoryTextView();
                return;
            } catch (e: Exception){
                firstNumber = 0;
            }
        }

        operator = type;
        updateHistoryTextView();
    }


    private fun updateResultTextView() {
        binding.tvKetqua.text = result.toString();
    }

    private fun updateHistoryTextView() {
        var resultCal: String;
        if (firstNumber != 0) {
            resultCal = "$firstNumber $operator $secondNumber";
        } else {
            resultCal = secondNumber;
        }
        binding.tvPheptinh.text = resultCal;
    }
}
