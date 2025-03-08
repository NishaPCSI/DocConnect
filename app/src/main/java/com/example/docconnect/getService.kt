import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// Data class representing a hospital
data class Hospital(
    val hospitalId: Int,
    val hospitalName: String,
    val hospitalAddress: String,
    val hospitalDistance: String
)

// Data class representing the API response
data class HospitalResponse(
    val isError: Boolean,
    val doctors: Doctors
)

data class Doctors(
    val isError: Boolean,
    val hospital: List<Hospital>
)


//data for doctor near you
data class DoctorResponse(
    val isError: Boolean,
    val doctors: DoctorList
)

data class DoctorList(
    val isError: Boolean,
    val doctor: List<Doctor>
)

data class Doctor(
    val doctorId: Int,
    val doctorName: String,
    val doctorProfession: String,
    val doctorLocation: String,
    val doctorExperience: String,
    val aboutDoctor: String,
    val rating: Double,
    val total_patients: Int
)


//AllAppointment data


data class AppointmentResponse(
    val isError: Boolean,
    val appointment: AppointmentData
)

data class AppointmentData(
    val isError: Boolean,
    val appointment: List<Appointment>
)

data class Appointment(
    val date: String,
    val time: String,
    val doctorId: Int,
    val Doctor: Doctor
)

data class DoctorInfo(
    val doctorName: String,
    val doctorProfession: String,
    val doctorLocation: String
)


//Prescription Data Model

data class PrescriptionApiResponse(
    val isError: Boolean,
    val prescriptions: PrescriptionData
)

data class PrescriptionData(
    val isError: Boolean,
    val prescriptions: List<Prescription>
)

data class Prescription(
    val prescription: String,
    val doctorId: Int,
    val Doctor: DoctorDetails
)

data class DoctorDetails(  // Renamed from "Doctor"
    val doctorName: String,
    val doctorProfession: String,
    val doctorLocation: String
)



// Retrofit API Service interface
interface ApiService {
    @GET("api/hospital/get_all_hospitals") // Replace with actual endpoint
    fun getHospitals(): Call<HospitalResponse>

    @GET("api/doctor/get_all_doctors")  // Replace with your actual endpoint
    fun getDoctors(): Call<DoctorResponse>

    @GET("api/appointment/get_all_appointments")  // Change this endpoint as per your API
    fun getAppointments(): Call<AppointmentResponse>

    @GET("api/prescription/get_all_prescriptions")
    fun getPrescriptions(): Call<PrescriptionApiResponse>

}
