package com.example.docconnect
//
//import Hospital
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class com.example.docconnect.com.example.docconnect.Homepage : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_homepage)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//
//    fun goToPrescription (view: View){
//        var intent = Intent (this, Prescription::class.java)
//        startActivity(intent)
//    }
//
//    fun goToHospital (view: View){
//        var intent = Intent (this, HospitalActivity::class.java)
//        startActivity(intent)
//    }
//
//    fun goToHomepage (view: View){
//        var intent = Intent (this, Ambulance::class.java)
//        startActivity(intent)
//    }
//
//    fun goToAllapoint (view: View){
//        var intent = Intent (this, AllAppointments::class.java)
//        startActivity(intent)
//    }
//
//    fun goToDocDetail (view: View){
//        var intent = Intent (this, Doctorpage::class.java)
//        startActivity(intent)
//    }
//}
//

import DoctorsAdapter
import DoctorsViewModel
import Prescription
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Homepage : AppCompatActivity() {
    private val viewModel: DoctorsViewModel by viewModels()
    private lateinit var adapter: DoctorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = DoctorsAdapter(listOf())
        recyclerView.adapter = adapter

        viewModel.fetchDoctors()

        viewModel.doctors.observe(this) { DoctorList ->
            if (DoctorList != null) {
                adapter.updateDoctors(DoctorList)
            } else {
                Toast.makeText(this, "Failed to load doctors", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun goToPrescription (view: View){
        var intent = Intent (this, Prescription::class.java)
        startActivity(intent)
    }

    fun goToHospital (view: View){
        var intent = Intent (this, HospitalActivity::class.java)
        startActivity(intent)
    }

    fun goToHomepage (view: View){
        var intent = Intent (this, Ambulance::class.java)
        startActivity(intent)
    }

    fun goToAllapoint (view: View){
        var intent = Intent (this, AllAppointments::class.java)
        startActivity(intent)
    }

    fun goToDocDetail (view: View){
        var intent = Intent (this, Doctorpage::class.java)
        startActivity(intent)
    }
}
