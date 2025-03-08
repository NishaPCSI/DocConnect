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
//class Signup : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_signup)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//
//    fun onSignClicked (view: View) {
//        val intent = Intent(this, Login::class.java)
//        startActivity(intent)
//    }
//
//    fun goToLogin (view: View){
//        val intent = Intent(this, Login::class.java)
//        startActivity(intent)
//    }
//}

package com.example.docconnect

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
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Signup : AppCompatActivity() {

    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var dobInput: EditText
    private lateinit var phoneInput: EditText
    private lateinit var maleRadio: RadioButton
    private lateinit var femaleRadio: RadioButton
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI elements (Make sure IDs match your XML layout)
        emailInput = findViewById(R.id.editTextTextEmailAddress2)
        passwordInput = findViewById(R.id.editTextNumberPassword)
        dobInput = findViewById(R.id.editTextDate)
        phoneInput = findViewById(R.id.editTextPhone2)
        maleRadio = findViewById(R.id.radiobutton)
        femaleRadio = findViewById(R.id.radiobutton2)
        signupButton = findViewById(R.id.button2)

        // Set up date picker for Date of Birth field
        dobInput.setOnClickListener { showDatePicker() }

        // Set up signup button click
        signupButton.setOnClickListener { onSignClicked(it) }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            dobInput.setText(String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear))
        }, year, month, day)

        datePicker.show()
    }

    fun onSignClicked(view: View) {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val dob = dobInput.text.toString().trim()
        val phone = phoneInput.text.toString().trim()
        val gender = when {
            maleRadio.isChecked -> "Male"
            femaleRadio.isChecked -> "Female"
            else -> ""
        }

        // Validate inputs
        if (email.isEmpty() || password.isEmpty() || dob.isEmpty() || phone.isEmpty() || gender.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        // Create request object with correct field names
        val request = SignupRequest(email, password, dob, gender, phone)

        // Log the request object to verify the format
        Log.d("SignupDebug", Gson().toJson(request))

        // Make API call
        RetroFile.RetrofitClient.instance.registerUser(request)
            .enqueue(object : Callback<SignupResponse> {
                override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                    Log.d("SignupDebug", "Response Code: ${response.code()}") // Log status code

                    if (response.isSuccessful) {
                        response.body()?.let {
                            Log.d("SignupDebug", "Response Body: ${Gson().toJson(it)}") // Log response body

                            if (it.success) {
//                                runOnUiThread {
//                                    Toast.makeText(this@Signup, "Signup Successful!", Toast.LENGTH_SHORT).show()
//                                    startActivity(Intent(this@Signup, Login::class.java))
//                                    finish()
//                                     goToLogin(view: View) {
                                startActivity(Intent(this@Signup, Login::class.java))



                            } else {
                                startActivity(Intent(this@Signup, Login::class.java))

                                Toast.makeText(this@Signup, it.message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        val errorBody = response.errorBody()?.string() // Get error response
                        Log.e("SignupDebug", "Signup Failed - Error Body: $errorBody") // Log error
                        Toast.makeText(this@Signup, "Signup Failed: $errorBody", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                    Toast.makeText(this@Signup, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
    }


    fun goToLogin(view: View) {
        startActivity(Intent(this, Login::class.java))
    }
}
