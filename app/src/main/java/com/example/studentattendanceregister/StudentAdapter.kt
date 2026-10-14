package com.example.studentattendanceregister

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

class StudentAdapter(
    private val context: Context,
    private val studentList: ArrayList<Student>,
    private val onAttendanceChanged: () -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = studentList.size

    override fun getItem(position: Int): Any = studentList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_student, parent, false)

        val student = studentList[position]

        val tvStudentName = view.findViewById<TextView>(R.id.tvStudentName)
        val tvStatus = view.findViewById<TextView>(R.id.tvStatus)
        val btnPresent = view.findViewById<Button>(R.id.btnPresent)
        val btnAbsent = view.findViewById<Button>(R.id.btnAbsent)

        tvStudentName.text = student.name

        if (student.isPresent) {
            tvStatus.text = "Present"
            tvStatus.setTextColor(Color.parseColor("#2E7D32")) // Green
            view.setBackgroundColor(Color.parseColor("#E8F5E9"))
        } else {
            tvStatus.text = "Absent"
            tvStatus.setTextColor(Color.parseColor("#C62828")) // Red
            view.setBackgroundColor(Color.parseColor("#FFEBEE"))
        }

        btnPresent.setOnClickListener {
            student.isPresent = true
            notifyDataSetChanged()
            onAttendanceChanged()
        }

        btnAbsent.setOnClickListener {
            student.isPresent = false
            notifyDataSetChanged()
            onAttendanceChanged()
        }

        return view
    }
}