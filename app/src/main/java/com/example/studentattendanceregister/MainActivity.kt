package com.example.studentattendanceregister

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val studentList = ArrayList<Student>()
    private lateinit var adapter: StudentAdapter

    private lateinit var etStudentName: EditText
    private lateinit var btnAddStudent: Button
    private lateinit var btnReset: Button
    private lateinit var lvStudents: ListView
    private lateinit var tvEmptyState: TextView
    private lateinit var tvTotalCount: TextView
    private lateinit var tvPresentCount: TextView
    private lateinit var tvAbsentCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components
        etStudentName = findViewById(R.id.etStudentName)
        btnAddStudent = findViewById(R.id.btnAddStudent)
        btnReset = findViewById(R.id.btnReset)
        lvStudents = findViewById(R.id.lvStudents)
        tvEmptyState = findViewById(R.id.tvEmptyState)
        tvTotalCount = findViewById(R.id.tvTotalCount)
        tvPresentCount = findViewById(R.id.tvPresentCount)
        tvAbsentCount = findViewById(R.id.tvAbsentCount)

        // Set up custom adapter with callback to update stats dynamically
        adapter = StudentAdapter(this, studentList) {
            updateAttendanceSummary()
        }
        lvStudents.adapter = adapter

        // Add Student click listener
        btnAddStudent.setOnClickListener {
            val name = etStudentName.text.toString().trim()
            if (name.isNotEmpty()) {
                studentList.add(Student(name = name, isPresent = false))
                adapter.notifyDataSetChanged()
                etStudentName.text.clear()
                updateAttendanceSummary()
            } else {
                Toast.makeText(this, "Please enter a student name", Toast.LENGTH_SHORT).show()
            }
        }

        // Reset Register click listener with confirmation dialog
        btnReset.setOnClickListener {
            if (studentList.isEmpty()) {
                Toast.makeText(this, "Register is already empty", Toast.LENGTH_SHORT).show()
            } else {
                showResetConfirmationDialog()
            }
        }

        updateAttendanceSummary()
    }

    private fun updateAttendanceSummary() {
        val total = studentList.size
        val present = studentList.count { it.isPresent }
        val absent = total - present

        tvTotalCount.text = "Total: $total"
        tvPresentCount.text = "Present: $present"
        tvAbsentCount.text = "Absent: $absent"

        // Toggle empty state view
        if (total == 0) {
            tvEmptyState.visibility = View.VISIBLE
            lvStudents.visibility = View.GONE
        } else {
            tvEmptyState.visibility = View.GONE
            lvStudents.visibility = View.VISIBLE
        }
    }

    private fun showResetConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Reset Register")
            .setMessage("Are you sure you want to clear all student records?")
            .setPositiveButton("Reset") { _, _ ->
                studentList.clear()
                adapter.notifyDataSetChanged()
                updateAttendanceSummary()
                Toast.makeText(this, "Register cleared", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}