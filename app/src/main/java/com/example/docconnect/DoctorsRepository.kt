import com.example.docconnect.RetroFile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorsRepository {
    fun getDoctors(callback: (List<Doctor>?) -> Unit) {
        RetroFile.RetrofitClient.apiInstance.getDoctors().enqueue(object : Callback<DoctorResponse> {
            override fun onResponse(call: Call<DoctorResponse>, response: Response<DoctorResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()?.doctors?.doctor)
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<DoctorResponse>, t: Throwable) {
                callback(null)
            }
        })
    }
}
