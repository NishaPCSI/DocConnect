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
import android.annotation.SuppressLint
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
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<PrescriptionApiResponse>,
                response: Response<PrescriptionApiResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()

                    // Log the entire response for debugging
                    Log.d("PrescriptionActivity", "Response: ${response.body()}")

                    if (apiResponse != null) {
                        // Check for errors in the response
                        if (!apiResponse.isError && !apiResponse.prescriptions.isError) {
                            Log.d("PrescriptionActivity", "API call successful. Updating UI.")

                            // Clear current list and update with the new data
                            prescriptionList.clear()
                            apiResponse.prescriptions.prescriptions.let { prescriptions ->
                                prescriptionList.addAll(prescriptions)
                            }

                            // Notify adapter that the data has changed
                            prescriptionAdapter.notifyDataSetChanged()

                        } else {
                            Log.e("PrescriptionActivity", "API returned an error: ${apiResponse.isError}")
                        }
                    } else {
                        Log.e("PrescriptionActivity", "Response body is null.")
                    }
                } else {
                    Log.e("PrescriptionActivity", "API call failed: ${response.code()} - ${response.message()}")
                    Log.e("PrescriptionActivity", "Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<PrescriptionApiResponse>, t: Throwable) {
                Log.e("PrescriptionActivity", "API request failed: ${t.message}", t)
            }
        })
    }

}
