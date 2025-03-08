
package com.example.docconnect

import Doctor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docconnect.R

class DoctorsAdapter(private var doctors: List<Doctor>) :
    RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder>() {

    class DoctorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.doctorName)
        val profession: TextView = itemView.findViewById(R.id.doctorSpeciality)
        val location: TextView = itemView.findViewById(R.id.doctorLocation)
//        val experience: TextView = itemView.findViewById(R.id.doctor_experience)
        val rating: TextView = itemView.findViewById(R.id.doctorRating)
        val totalPatients: TextView = itemView.findViewById(R.id.total_patients)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_doctor, parent, false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val doctor = doctors[position]
        holder.name.text = doctor.doctorName
        holder.profession.text = doctor.doctorProfession
        holder.location.text = doctor.doctorLocation
//        holder.experience.text = "Experience: ${doctor.doctorExperience} years"
        holder.rating.text = "Rating: ${doctor.rating}"
        holder.totalPatients.text = "Patients: ${doctor.total_patients}"
    }

    override fun getItemCount(): Int = doctors.size

    fun updateDoctors(newDoctors: List<Doctor>) {
        doctors = newDoctors
        notifyDataSetChanged()
    }
}
