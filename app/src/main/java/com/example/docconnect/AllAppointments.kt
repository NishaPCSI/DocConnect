package com.example.docconnect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appointments.viewmodel.AppointmentViewModel
import com.example.appointmentapp.adapter.AppointmentAdapter
import com.example.docconnect.R

class AllAppointments : AppCompatActivity() {

    private val appointmentViewModel: AppointmentViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var appointmentAdapter: AppointmentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_appointments)

        // Set up the RecyclerView
        recyclerView = findViewById(R.id.recyclerViewAppointments)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe appointments LiveData from ViewModel
        appointmentViewModel.appointments.observe(this, Observer { response ->
            if (response != null) {
                // Create an AppointmentAdapter and set it to the RecyclerView
                appointmentAdapter = AppointmentAdapter(response.appointment.appointment)
                recyclerView.adapter = appointmentAdapter
            } else {
                Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })

        // Load appointments
        appointmentViewModel.loadAppointments()
    }

    fun goToHome(view: View) {
        // Navigate to the home activity
        var intent = Intent(this, HospitalActivity::class.java)
        startActivity(intent)
    }
}
