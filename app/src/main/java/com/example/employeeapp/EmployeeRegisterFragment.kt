package com.example.employeeapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.room.Entity
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EmployeeRegisterFragment : Fragment() {
    private lateinit var employeeDatabase: EmployeeDatabase
    private lateinit var employeeDao: EmployeeDao
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // フラグメントのレイアウトをインフレート
        return inflater.inflate(R.layout.fragment_employee_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        employeeDatabase = Room.databaseBuilder(
            requireContext(),
            EmployeeDatabase::class.java, "employee-database"
        ).build()
        employeeDao = employeeDatabase.employeeDao()

        val submitButton = view.findViewById<Button>(R.id.submitbtn)
        submitButton.setOnClickListener {
            if (validateInputs()) {
                // バリデーションが通過した場合の処理
                Toast.makeText(context, "登録成功", Toast.LENGTH_SHORT).show()
                // 登録処理
                saveEmployee()
                showAlertDialog(requireContext())
            }

        }
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

    /**
     * バリデーションチェックする
     */
    private fun validateInputs(): Boolean {
        val id = view?.findViewById<EditText>(R.id.idholder)?.text.toString()
        val firstName = view?.findViewById<EditText>(R.id.firstName)?.text.toString()
        val lastName = view?.findViewById<EditText>(R.id.secondName)?.text.toString()
        val section = view?.findViewById<Spinner>(R.id.sectionItem)?.selectedItem.toString()
        val email = view?.findViewById<EditText>(R.id.editTextText)?.text.toString()
        val genderId = view?.findViewById<RadioGroup>(R.id.radioGroup)?.checkedRadioButtonId
        val gender = when (genderId) {
            R.id.mensItem -> "男性"
            R.id.girlItem -> "女性"
            else -> ""
        }
        val validSections = listOf("シス開", "ビジソル", "グロカル")

        return when {
            id.isEmpty() -> {
                showToast("社員IDを入力してください")
                false
            }
            firstName.isEmpty() -> {
                showToast("社員名（姓）を入力してください")
                false
            }
            lastName.isEmpty() -> {
                showToast("社員名（名）を入力してください")
                false
            }
            section == "選択してください" -> {
                showToast("所属セクションを正しく選択してください")
                false
            }
            email.isEmpty() -> {
                showToast("メールアドレスを入力してください")
                false
            }
            gender.isEmpty() -> {
                showToast("性別を入力してください")
                false
            }
            id.length != 10 -> {
                showToast("社員IDは10桁で入力してください")
                false
            }
            firstName.length > 25 -> {
                showToast("社員名（姓）は25文字以内で入力してください")
                false
            }
            lastName.length > 25 -> {
                showToast("社員名（名）は25文字以内で入力してください")
                false
            }
            email.length > 256 -> {
                showToast("メールアドレスは256文字以内で入力してください")
                false
            }
            !Regex("^YZ\\d{8}$").matches(id) -> {
                showToast("社員IDはYZを含む8桁の数字で入力してください")
                false
            }
            !isValidEmail(email) -> {
                showToast("メールアドレスを正しく入力してください")
                false
            }
            section !in validSections -> {
                showToast("所属セクションを正しく選択してください")
                false
            }
            !listOf("男性", "女性").contains(gender) -> {
                showToast("性別を正しく入力してください")
                false
            }
            else -> true
        }
    }
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return Regex(emailPattern).matches(email)
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveEmployee() {
        val id = view?.findViewById<EditText>(R.id.idholder)?.text.toString()
        val familyName = view?.findViewById<EditText>(R.id.firstName)?.text.toString()
        val firstName = view?.findViewById<EditText>(R.id.secondName)?.text.toString()
        val section = view?.findViewById<Spinner>(R.id.sectionItem)?.selectedItemPosition ?: 0
        val email = view?.findViewById<EditText>(R.id.editTextText)?.text.toString()
        val genderId = view?.findViewById<RadioGroup>(R.id.radioGroup)?.checkedRadioButtonId
        val gender = when (genderId) {
            R.id.mensItem -> 1
            R.id.girlItem -> 2
            else -> 0
        }

        val employee = Employee(
            idholder = id,
            firstName = familyName,
            secondName = firstName,
            sectionItem = section,
            editTextText = email,
            radioGroup = gender
        )

        lifecycleScope.launch {
            val employees = employeeDao.selectAll()
            for (employee in employees) {
                Log.d(
                    "EmployeeRegisterFragment",
                    "ID:${employee.id},ID: ${employee.idholder}, 名: ${employee.firstName} ${employee.secondName}, セクション: ${employee.sectionItem}, メール: ${employee.editTextText}, 性別: ${employee.radioGroup}"
                )
            }
        }

        lifecycleScope.launch {
            employeeDao.insert(employee)
        }

//        lifecycleScope.launch {
//            val employees = employeeDao.selectAll()
//            val adapter = ArrayAdapter(
//                requireContext(),
//                android.R.layout.simple_list_item_1,
//                employees.map { "${it.firstName} ${it.secondName}" }
//            )
//            listView.adapter = adapter
//        }

    }
    private fun showAlertDialog(context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setMessage("登録しました")

        // Positive Button（肯定的なボタン）の設定
        alertDialogBuilder.setPositiveButton("はい") { _, _ ->
            // Yesボタンがクリックされた時の処理
            val fragment = EmployeeListFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
        // AlertDialogの作成と表示
        alertDialogBuilder.show()
    }

}
