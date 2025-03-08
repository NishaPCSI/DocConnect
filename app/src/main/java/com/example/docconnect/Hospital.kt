package com.example.docconnect

import ApiService
import Hospital
import HospitalAdapter
import HospitalResponse
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class HospitalActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var hospitalAdapter: HospitalAdapter
    private val hospitalsList = mutableListOf<Hospital>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hospital)

        recyclerView = findViewById(R.id.recyclerView) // Make sure your XML has a RecyclerView with this ID
        recyclerView.layoutManager = LinearLayoutManager(this)
        hospitalAdapter = HospitalAdapter(hospitalsList)
        recyclerView.adapter = hospitalAdapter

        fetchHospitals()
    }

    private fun fetchHospitals() {
        val retrofit = Retrofit.Builder()
//            .baseUrl("http://192.168.1.41:3000/") // Replace with your actual API base URL
            .baseUrl("http://192.168.1.24:3000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getHospitals().enqueue(object : Callback<HospitalResponse> {
            override fun onResponse(call: Call<HospitalResponse>, response: Response<HospitalResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val hospitalData = response.body()!!.doctors.hospital
                    hospitalsList.clear()
                    hospitalsList.addAll(hospitalData)
                    hospitalAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this@HospitalActivity, "Failed to fetch hospitals", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<HospitalResponse>, t: Throwable) {
                Toast.makeText(this@HospitalActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("HospitalActivity", "API Call Failed", t)
            }
        })
    }

    fun goToHome(view: View) {
        val intent = Intent(this, Homepage::class.java)
        startActivity(intent)
        finish()
    }
}