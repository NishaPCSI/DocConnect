package com.example.appointmentapp.adapter

import Appointment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docconnect.R

class AppointmentAdapter(private val appointmentList: List<Appointment>) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewDoctorName: TextView = itemView.findViewById(R.id.doctorName)
        private val textViewSpecialty: TextView = itemView.findViewById(R.id.doctorProfession)
        private val textViewDate: TextView = itemView.findViewById(R.id.date)
        private val textViewTime: TextView = itemView.findViewById(R.id.time)

        fun bind(appointment: Appointment) {
            textViewDoctorName.text = appointment.Doctor.doctorName
            textViewSpecialty.text = appointment.Doctor.doctorProfession
            textViewDate.text = appointment.date
            textViewTime.text = appointment.time
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        holder.bind(appointmentList[position])
    }

    override fun getItemCount(): Int = appointmentList.size
}
