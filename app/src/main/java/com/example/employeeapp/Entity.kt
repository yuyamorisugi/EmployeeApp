package com.example.employeeapp
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
    @Entity(tableName = "employee")
    data class Employee(

        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "employee_id")
        val idholder: String,

        @ColumnInfo(name = "family_name")
        val firstName: String,

        @ColumnInfo(name = "first_name")
        val secondName: String,

        @ColumnInfo(name = "section_id")
        val sectionItem: Int,

        @ColumnInfo(name = "mail")
        val editTextText: String,

        @ColumnInfo(name = "gender_id")
        val radioGroup: Int
    )
