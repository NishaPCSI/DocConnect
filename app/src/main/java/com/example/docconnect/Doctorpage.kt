package com.example.docconnect

import Doctor
import DoctorResponse
import DoctorResponsee
import Doctorr
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class Doctorpage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctorpage)

        val doctorId = intent.getIntExtra("DOCTOR_ID", -1)
        Log.d("DEBUG", "Received doctorId: $doctorId")

        if (doctorId != -1) {
            fetchDoctorDetails(doctorId)
        } else {
            Toast.makeText(this, "Invalid Doctor ID", Toast.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bookAppointBtn = findViewById<Button>(R.id.bookappointment)
        bookAppointBtn.setOnClickListener {
            showAppointmentPopup()
        }
    }

    // ----------------------------------------------------------------
    // Fetch Doctor Details
    // ----------------------------------------------------------------
    private fun fetchDoctorDetails(doctorId: Int) {
        Log.d("DEBUG", "Fetching details for doctorId: $doctorId")

        val call = RetroFile.RetrofitClient.apiInstance.getDoctorById(doctorId)

        call.enqueue(object : Callback<DoctorResponsee> {
            override fun onResponse(call: Call<DoctorResponsee>, response: Response<DoctorResponsee>) {
                if (response.isSuccessful && response.body() != null) {
                    val doctor = response.body()?.response?.doctor
                    if (doctor != null) {
                        Log.d("DEBUG", "Doctor fetched: ${doctor.doctorName}")
                        updateUI(doctor)
                    } else {
                        Log.e("DEBUG", "Doctor not found in API response")
                        Toast.makeText(this@Doctorpage, "Doctor not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("DEBUG", "Failed to load doctor details, response: ${response.errorBody()?.string()}")
                    Toast.makeText(this@Doctorpage, "Failed to load doctor details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DoctorResponsee>, t: Throwable) {
                Log.e("DEBUG", "Network error: ${t.message}")
                Toast.makeText(this@Doctorpage, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    // ----------------------------------------------------------------
    // Update UI with Doctor Details
    // ----------------------------------------------------------------
    private fun updateUI(doctor: Doctorr) {
        findViewById<TextView>(R.id.textView24).text = doctor.doctorName
        findViewById<TextView>(R.id.textView25).text = doctor.doctorProfession
        findViewById<TextView>(R.id.textView30).text = doctor.rating.toString()
        findViewById<TextView>(R.id.textView19).text = doctor.total_patients.toString()
        findViewById<TextView>(R.id.textView8).text = doctor.aboutDoctor
    }


    // ----------------------------------------------------------------
    // Show Appointment Popup with Time & Date Selection
    // ----------------------------------------------------------------
    private fun showAppointmentPopup() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.bookappointment_popup, null)
        builder.setView(dialogView)

        val etDate = dialogView.findViewById<EditText>(R.id.etDate)
        etDate.setOnClickListener {
            showDatePicker(etDate)
        }

        val timeButtons = listOf(
            dialogView.findViewById<Button>(R.id.btn10am),
            dialogView.findViewById<Button>(R.id.btn11am),
            dialogView.findViewById<Button>(R.id.btn12pm),
            dialogView.findViewById<Button>(R.id.btn1pm),
            dialogView.findViewById<Button>(R.id.btn2pm),
            dialogView.findViewById<Button>(R.id.btn3pm),
            dialogView.findViewById<Button>(R.id.btn4pm),
            dialogView.findViewById<Button>(R.id.btn5pm),
            dialogView.findViewById<Button>(R.id.btn6pm)
        )

        var selectedTime: String? = null
        timeButtons.forEach { button ->
            button.setOnClickListener {
                timeButtons.forEach {
                    it.setBackgroundResource(R.drawable.timeslot_background)
                    it.setTextColor(resources.getColor(R.color.black, null))
                }
                button.setBackgroundResource(R.drawable.timeslot_selected_background)
                button.setTextColor(resources.getColor(R.color.white, null))
                selectedTime = button.text.toString()
            }
        }

        builder.setPositiveButton("Confirm") { _, _ ->
            if (selectedTime == null || etDate.text.isEmpty()) {
                Toast.makeText(this, "Please select a date and time", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Appointment booked on ${etDate.text} at $selectedTime", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    // ----------------------------------------------------------------
    // Date Picker Function
    // ----------------------------------------------------------------
    private fun showDatePicker(etDate: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "%02d/%02d/%04d".format(selectedDay, selectedMonth + 1, selectedYear)
                etDate.setText(formattedDate)
            },
            year, month, day
        )
        datePicker.show()
    }

    // ----------------------------------------------------------------
    // Navigation Functions
    // ----------------------------------------------------------------
    fun goToHome(view: View) {
        val intent = Intent(this, Homepage::class.java)
        startActivity(intent)
    }

    fun goToBookAppointment(view: View) {
        val intent = Intent(this, Bookappointment::class.java)
        startActivity(intent)
    }
}
