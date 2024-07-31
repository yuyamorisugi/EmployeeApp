package com.example.employeeapp

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
        // フラグメントのレイアウトをインフレート
        return inflater.inflate(R.layout.fragment_employee_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView の設定
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = EmployeeAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)  // res/values/dimens.xml で定義した値を取得
        recyclerView.addItemDecoration(CustomItemDecoration(spacingInPixels))


// ViewModel からデータを取得して RecyclerView に表示
        employeeViewModel.getAllEmployees { employees ->
            adapter.setEmployees(employees)
        }
    }
    class CustomItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)

            // アイテムの上下左右にスペースを追加
            outRect.left = spacing
            outRect.right = spacing
            outRect.top = spacing / 2  // 上下の隙間を狭くする
            outRect.bottom = spacing / 2  // 上下の隙間を狭くする
        }
    }
}