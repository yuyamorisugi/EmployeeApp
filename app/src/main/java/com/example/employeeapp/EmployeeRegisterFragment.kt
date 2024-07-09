package com.example.employeeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class EmployeeRegisterFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // フラグメントのレイアウトをインフレート
        return inflater.inflate(R.layout.fragment_employee_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 戻るボタンの処理を追加する
        val backButton = view.findViewById<Button>(R.id.backbtn)
        backButton.setOnClickListener {
            // EmployeeListFragmentに遷移する
            val fragment = EmployeeListFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}