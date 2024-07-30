package com.example.employeeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels

class EmployeeDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)

        val employeeId = intent.getStringExtra(EXTRA_EMPLOYEE_ID) ?: return

        // Viewの参照を取得
        val idTextView: TextView = findViewById(R.id.detail_employee_id)
        val firstNameTextView: TextView = findViewById(R.id.detail_first_name)
        val secondNameTextView: TextView = findViewById(R.id.detail_second_name)
        val sectionTextView: TextView = findViewById(R.id.detail_section)
        val emailTextView: TextView = findViewById(R.id.detail_email)
        val genderTextView: TextView = findViewById(R.id.detail_gender)

        // ViewModelのインスタンスを取得して、指定したIDの社員情報を取得
        val employeeViewModel: EmployeeViewModel by viewModels()

        employeeViewModel.getEmployeeById(employeeId) { employee ->
            employee?.let {
                idTextView.text = it.idholder
                firstNameTextView.text = it.firstName
                secondNameTextView.text = it.secondName
                sectionTextView.text = when (it.sectionItem) {
                    1 -> "シス開"
                    2 -> "ビジソル"
                    else -> "グロカル"
                }
                emailTextView.text = it.editTextText
                genderTextView.text = when (it.radioGroup){
                    1 -> "男性"
                    else -> "女性"
                }

            }
        }

        // "戻る"ボタンの参照を取得
        val backButton: Button = findViewById(R.id.backbtn)
        backButton.setOnClickListener {
            // EmployeeListFragmentに遷移する
            val fragment = EmployeeListFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }


    companion object {
        const val EXTRA_EMPLOYEE_ID = "com.example.employeeapp.EMPLOYEE_ID"
    }
}