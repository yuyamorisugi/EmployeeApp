package com.example.employeeapp

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Dao

@Dao
abstract class EmployeeDao {
    /** データベースの登録 **/
    @Insert
    abstract suspend fun insert(employee: Employee)

    @Query("SELECT * FROM employee WHERE employee_id = :employeeId")
    abstract suspend fun getEmployeeById(employeeId: String): Employee?
    /** データ更新 */
    @Query("SELECT * FROM employee")
    abstract suspend fun selectAll(): List<Employee>
}