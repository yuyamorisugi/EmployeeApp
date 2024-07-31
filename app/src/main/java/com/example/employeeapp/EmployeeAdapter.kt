package com.example.employeeapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeAdapter(private val onItemClick: (String) -> Unit) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>(){
    private var employees = emptyList<Employee>()

    /** リストアイテムのビューの保持(TextViewから参照) **/
    class EmployeeViewHolder(itemView: View, private val onItemClick: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
        val idholder: TextView = itemView.findViewById(R.id.idholder)
        val firstName: TextView = itemView.findViewById(R.id.firstName)

        init {
            itemView.setOnClickListener {
                onItemClick(idholder.text.toString())
            }
        }
    }
    /** インフレートして新しいViewを返す **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_employee, parent, false)
        return EmployeeViewHolder(itemView, onItemClick)
    }
    /** 特定の位置のデータをViewにバインドする **/
    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val currentEmployee = employees[position]
        holder.idholder.text = "${currentEmployee.idholder}"
        holder.firstName.text = "${currentEmployee.firstName} ${currentEmployee.secondName}"
    }
    /** リスト内のアイテム数を取得 **/
    override fun getItemCount() = employees.size
    /** RecycleViewの更新をして再描画 **/
    internal fun setEmployees(employees: List<Employee>) {
        this.employees = employees
        Log.d("EmployeeAdapter", "Employees updated: $employees")
        notifyDataSetChanged()
    }
}