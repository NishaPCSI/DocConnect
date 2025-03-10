package com.example.docconnect

import Doctor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docconnect.R

class DoctorsAdapter(
    private var doctors: List<Doctor>,
    private val onDoctorClick: (Int) -> Unit
) : RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder>() {

    private var filteredDoctors: List<Doctor> = doctors // Copy of the list for filtering

    class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.doctorName)
        val profession: TextView = itemView.findViewById(R.id.doctorSpeciality)
        val location: TextView = itemView.findViewById(R.id.doctorLocation)
        val rating: TextView = itemView.findViewById(R.id.doctorRating)
        val totalPatients: TextView = itemView.findViewById(R.id.total_patients)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = filteredDoctors[position]
        holder.name.text = doctor.doctorName
        holder.profession.text = doctor.doctorProfession
        holder.location.text = doctor.doctorLocation
        holder.rating.text = "Rating: ${doctor.rating}"
        holder.totalPatients.text = "Patients: ${doctor.total_patients}"

        holder.itemView.setOnClickListener {
            onDoctorClick(doctor.doctorId)
        }
    }

    override fun getItemCount(): Int = filteredDoctors.size

    fun updateDoctors(newDoctors: List<Doctor>) {
        doctors = newDoctors
        filteredDoctors = newDoctors
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredDoctors = if (query.isEmpty()) {
            doctors
        } else {
            doctors.filter {
                it.doctorName.contains(query, ignoreCase = true) ||
                        it.doctorProfession.contains(query, ignoreCase = true) ||
                        it.doctorLocation.contains(query, ignoreCase = true)
            }
        }
        notifyDataSetChanged()
    }
}
