package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // 마지막 키 숫자 여부
    var lastNumeric: Boolean = false

    // 마지막 키 점 여부
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 숫자 입력
    fun onDigit(view: View) {
        // 텍스트에 숫자 추가
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    // 점 입력
    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    // 연산자 입력
    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    // 텍스트 초기화
    fun onClear(view: View) {
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    // 텍스트 계산
    fun onEqual(view: View) {
        // 마지막 입력이 숫자일 시 계산
        if (lastNumeric) {
            // 텍스트 값 불러오기
            var value = tvInput.text.toString()
            var prefix = ""
            try {
                // 음수값으로 시작 시 구분
                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1);
                }

                // "/" 연산자 일 때
                if (value.contains("/")) {
                    // "/" 연산자로 텍스트 구분
                    val splitedValue = value.split("/")

                    var one = splitedValue[0] // 첫번째 값
                    val two = splitedValue[1] // 두번째 값

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    // 소수점 뒤에 0이 포함된 경우 제거
                    tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
                // "*" 연산자 일 때
                else if (value.contains("*")) {
                    // "*" 연산자로 텍스트 구분
                    val splitedValue = value.split("*")

                    var one = splitedValue[0] // 첫번째 값
                    val two = splitedValue[1] // 두번째 값

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    // 소수점 뒤에 0이 포함된 경우 제거
                    tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }
                // "-" 연산자 일 때
                else if (value.contains("-")) {
                    // "-" 연산자로 텍스트 구분
                    val splitedValue = value.split("-")

                    var one = splitedValue[0] // 첫번째 값
                    val two = splitedValue[1] // 두번째 값

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    // 소수점 뒤에 0이 포함된 경우 제거
                    tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }
                // "+" 연산자 일 때
                else if (value.contains("+")) {
                    // "+" 연산자로 텍스트 구분
                    val splitedValue = value.split("+")

                    var one = splitedValue[0] // 첫번째 값
                    val two = splitedValue[1] // 두번째 값

                    if (!prefix.isEmpty()) {
                        one = prefix + one
                    }

                    // 소수점 뒤에 0이 포함된 경우 제거
                    tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    // 연산자 사용 여부
    private fun isOperatorAdded(value: String): Boolean {
        // 텍스트가 "-"로 시작하는 경우 무시
        return if (value.startsWith("-")) {
            false
        } else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+"))
        }
    }

    // "." 뒤의 0 제거
    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }
}