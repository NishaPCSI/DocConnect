package com.example.appointments.viewmodel

import AppointmentResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appointments.repository.AppointmentRepository
//import com.example.docconnect.RetroFile.AppointmentResponse  //

class AppointmentViewModel : ViewModel() {
    private val repository = AppointmentRepository()
    private val _appointments = MutableLiveData<AppointmentResponse?>()
    val appointments: LiveData<AppointmentResponse?> get() = _appointments

    fun loadAppointments() {
        repository.fetchAppointments { response ->
            _appointments.postValue(response)
        }
    }
}
