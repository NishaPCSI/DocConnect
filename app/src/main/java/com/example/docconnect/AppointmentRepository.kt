package com.example.appointments.repository

import AppointmentResponse
import com.example.docconnect.RetroFile
//import com.example.docconnect.RetroFile.AppointmentResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentRepository {
    fun fetchAppointments(callback: (AppointmentResponse?) -> Unit) {
        val call = RetroFile.RetrofitClient.apiInstance.getAppointments()

        call.enqueue(object : Callback<AppointmentResponse> {
            override fun onResponse(call: Call<AppointmentResponse>, response: Response<AppointmentResponse>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<AppointmentResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}
