package com.example.docconnect

import android.media.session.MediaSession.Token
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Data class for the signup request
data class SignupRequest(
    val email: String,
    val password: String,
    @SerializedName("dateOfBirth") val dob: String,
    val gender: String,
    @SerializedName("phoneNumber") val phone: String
)


// Data class for the Login request
data class LoginRequest(
    val email: String,
    val password: String
)


// Data class for the response
data class SignupResponse(
    val success: Boolean,
    val message: String
)

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String
)

// Retrofit interface
interface AuthService {
    @POST("api/auth/register")
    fun registerUser(@Body request: SignupRequest): Call<SignupResponse>

    @POST("api/auth/login") // Change the endpoint to match your backend API
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>


}

