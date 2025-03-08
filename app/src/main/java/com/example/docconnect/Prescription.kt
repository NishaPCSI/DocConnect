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
//class Prescription : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_prescription)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//    fun goToHome(view: View){
//        var intent = Intent(this,  Homepage::class.java)
//        startActivity(intent)
//    }
//}

package com.example.docconnect

import Prescription
import PrescriptionApiResponse
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.docconnect.R
import com.example.docconnect.RetroFile
import com.example.docconnect.PrescriptionAdapter

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Prescription : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var prescriptionAdapter: PrescriptionAdapter
    private val prescriptionList = mutableListOf<Prescription>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)

        recyclerView = findViewById(R.id.recyclerViewPrescriptions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        prescriptionAdapter = PrescriptionAdapter(prescriptionList)
        recyclerView.adapter = prescriptionAdapter

        fetchPrescriptions()
    }

    private fun fetchPrescriptions() {
        val call = RetroFile.RetrofitClient.apiInstance.getPrescriptions()
        call.enqueue(object : Callback<PrescriptionApiResponse> {
            override fun onResponse(
                call: Call<PrescriptionApiResponse>,
                response: Response<PrescriptionApiResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val apiResponse = response.body()
                    if (!apiResponse!!.isError && !apiResponse.prescriptions.isError) {
                        prescriptionList.clear()
                        prescriptionList.addAll(apiResponse.prescriptions.prescriptions)
                        prescriptionAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<PrescriptionApiResponse>, t: Throwable) {
                Log.e("PrescriptionActivity", "Error fetching prescriptions: ${t.message}")
            }
        })
    }
}
