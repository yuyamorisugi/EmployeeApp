package com.example.employeeapp

import androidx.lifecycle.LiveData

class EmployeeRepository (private val employeeDao: EmployeeDao){
    suspend fun getAllEmployees(): List<Employee> {
        return employeeDao.getAllEmployees()
    }

    suspend fun insert(employee: Employee) {
        employeeDao.insert(employee)
    }
    suspend fun getEmployeeById(employeeId: String): Employee? {
        return employeeDao.getEmployeeById(employeeId)
    }
}