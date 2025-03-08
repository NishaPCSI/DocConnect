package com.example.docconnect

import Prescription
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.example.docconnect.Prescription

//import com.example.docconnect.network.Prescription

class PrescriptionAdapter(private val prescriptionList: List<Prescription>) :
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val prescription = prescriptionList[position]
        holder.doctorName.text = prescription.doctorDetails.doctorName  // Updated reference
        holder.doctorProfession.text = prescription.doctorDetails.doctorProfession  // Updated reference
        holder.doctorLocation.text = prescription.doctorDetails.doctorLocation  // Updated reference
        holder.prescriptionText.text = "Prescription: ${prescription.prescription}"
    }

    override fun getItemCount(): Int = prescriptionList.size
}
