//package com.example.docconnect
//
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class AllAppointments : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_all_appointments)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//
//    fun goToHome(view: View){
//        var intent = Intent(this,  HospitalActivity::class.java)
//        startActivity(intent)
//    }
//}
package com.example.docconnect

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.appointments.viewmodel.AppointmentViewModel

class AllAppointments : AppCompatActivity() {
    private val appointmentViewModel: AppointmentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_appointments)

        appointmentViewModel.appointments.observe(this, Observer { response ->
            if (response != null) {
                response.appointment.appointment.forEach { appointment ->
                    Log.d("Appointments", "Doctor: ${appointment.Doctor.doctorName}, Date: ${appointment.date}")
                }
            } else {
                Log.e("Appointments", "Failed to load data")
            }
        })

        appointmentViewModel.loadAppointments()
    }
}
