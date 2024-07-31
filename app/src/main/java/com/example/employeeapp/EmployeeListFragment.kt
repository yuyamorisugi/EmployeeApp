package com.example.employeeapp

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EmployeeListFragment : Fragment() {

    private val employeeViewModel: EmployeeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employee_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = EmployeeAdapter { employeeId ->
            val intent = Intent(activity, EmployeeDetailActivity::class.java).apply {
                putExtra(EmployeeDetailActivity.EXTRA_EMPLOYEE_ID, employeeId)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
        recyclerView.addItemDecoration(EmployeeListFragment.CustomItemDecoration(spacingInPixels))

        employeeViewModel.getAllEmployees { employees ->
            adapter.setEmployees(employees)
        }
    }

    class CustomItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.left = spacing
            outRect.right = spacing
            outRect.top = spacing / 2
            outRect.bottom = spacing / 2
        }
    }
}