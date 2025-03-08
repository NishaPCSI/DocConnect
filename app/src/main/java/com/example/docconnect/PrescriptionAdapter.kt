package com.example.docconnect

import Prescription
import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PrescriptionAdapter(private val prescriptionList: MutableList<Prescription>) :
    RecyclerView.Adapter<PrescriptionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val doctorName: TextView = view.findViewById(R.id.doctorName)
        val doctorProfession: TextView = view.findViewById(R.id.doctorProfession)
        val doctorLocation: TextView = view.findViewById(R.id.doctorLocation)
        val prescriptionText: TextView = view.findViewById(R.id.prescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_prescription, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prescription = prescriptionList[position]

        // Log doctorDetails to verify if it's null or has valid data
        val doctorDetails = prescription.Doctor
        if (doctorDetails != null) {
            Log.d("PrescriptionAdapter", "Doctor Name: ${doctorDetails.doctorName}")
            Log.d("PrescriptionAdapter", "Doctor Profession: ${doctorDetails.doctorProfession}")
            Log.d("PrescriptionAdapter", "Doctor Location: ${doctorDetails.doctorLocation}")
        } else {
            Log.d("PrescriptionAdapter", "DoctorDetails is null for prescription at position ${prescription.Doctor}")
        }

        // Safely access doctorDetails properties with default values if null
        holder.doctorName.text = doctorDetails?.doctorName ?: "Unknown Doctor"
        holder.doctorProfession.text = doctorDetails?.doctorProfession ?: "Unknown Profession"
        holder.doctorLocation.text = doctorDetails?.doctorLocation ?: "Unknown Location"

        // Handle prescription text
        holder.prescriptionText.text = "Prescription: ${prescription.prescription}"
    }

    override fun getItemCount(): Int = prescriptionList.size

    // Update the data in the adapter (for dynamic data updates)
    fun updateData(newList: List<Prescription>) {
        prescriptionList.clear()
        prescriptionList.addAll(newList)
        notifyDataSetChanged()
    }
}


