package com.example.employeeapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
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

        val submitButton = view.findViewById<Button>(R.id.submitbtn)
        submitButton.setOnClickListener {
            if (validateInputs()) {
                // バリデーションが通過した場合の処理
                Toast.makeText(context, "登録成功", Toast.LENGTH_SHORT).show()
                // ここに登録処理を追加
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
        private val existingIds = mutableListOf<String>()
        private val existingEmails = mutableListOf<String>()
        private val existingFirstNames = mutableListOf<String>()
        private val existingLastNames = mutableListOf<String>()
        private val existingSections = mutableListOf<String>()
        private val existingGenders = mutableListOf<String>()
    /**
     * バリデーションチェックする
     */
    private fun validateInputs(): Boolean {
        val id = view?.findViewById<EditText>(R.id.IDholder)?.text.toString()
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


        when {
            id.isEmpty() -> showToast("社員IDを入力してください")
            firstName.isEmpty() -> showToast("社員名（姓）を入力してください")
            lastName.isEmpty() -> showToast("社員名（名）を入力してください")
            section == "選択してください" -> showToast("所属セクションを正しく選択してください")
            email.isEmpty() -> showToast("メールアドレスを入力してください")
            gender.isEmpty() -> showToast("性別を入力してください")
            id.length != 10 -> showToast("社員IDは10桁で入力してください")
            firstName.length > 25 -> showToast("社員名（姓）は25文字以内で入力してください")
            lastName.length > 25 -> showToast("社員名（名）は25文字以内で入力してください")
            email.length > 256 -> showToast("メールアドレスは256文字以内で入力してください")
            !Regex("^YZ\\d{8}$").matches(id) -> showToast("社員IDはYZを含む8桁の数字で入力してください")
            !isValidEmail(email) -> showToast("メールアドレスを正しく入力してください")
            section !in validSections -> showToast("所属セクションを正しく選択してください")
            !listOf("男性", "女性").contains(gender) -> showToast("性別を正しく入力してください")
            existingFirstNames.contains(firstName) -> showToast("社員名（姓）は重複しています")
            existingLastNames.contains(lastName) -> showToast("社員名（名）は重複しています")
            existingSections.contains(section) -> showToast("所属セクションは重複しています")
            existingGenders.contains(gender) -> showToast("性別は重複しています")
            else -> {
                // 新しいデータをリストに追加
                existingIds.add(id)
                existingEmails.add(email)
                existingFirstNames.add(firstName)
                existingLastNames.add(lastName)
                existingSections.add(section)
                existingGenders.add(gender)
                return true
            }
        }
        return false
    }
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return Regex(emailPattern).matches(email)
    }
    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}