package com.example.employeeapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeViewModel (application: Application) : AndroidViewModel(application)  {
    private val repository: EmployeeRepository = EmployeeRepository(
        EmployeeDatabase.getDatabase(application).employeeDao()
    )

    fun getAllEmployees(callback: (List<Employee>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val employees = repository.getAllEmployees()
            withContext(Dispatchers.Main) {
                callback(employees)
            }
        }
    }
    fun getEmployeeById(employeeId: String, callback: (Employee?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val employee = repository.getEmployeeById(employeeId)
            withContext(Dispatchers.Main) {
                callback(employee)
            }
        }
    }
    fun insert(employee: Employee) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(employee)
    }
}