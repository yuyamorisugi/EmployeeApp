package com.example.employeeapp

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Dao

@Dao
interface EmployeeDao {
    /** データベースの登録 **/
    @Insert
    suspend fun insert(employee: Employee)

    @Query("SELECT * FROM employee WHERE employee_id = :employeeId")
    suspend fun getEmployeeById(employeeId: String): Employee?
    /** データ更新 */
    @Query("SELECT * FROM employee")
    suspend fun selectAll(): List<Employee>

    @Query("SELECT * FROM employee ORDER BY employee_id ASC ")
    suspend fun getAllEmployees(): List<Employee>
}