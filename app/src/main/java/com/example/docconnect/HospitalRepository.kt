import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.docconnect.RetroFile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HospitalRepository {
    private val hospitalLiveData = MutableLiveData<List<Hospital>>()

    fun fetchHospitals(): LiveData<List<Hospital>> {
        RetroFile.RetrofitClient.apiInstance.getHospitals().enqueue(object : Callback<HospitalResponse> {
            override fun onResponse(call: Call<HospitalResponse>, response: Response<HospitalResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    hospitalLiveData.postValue(response.body()?.doctors?.hospital ?: emptyList())
                } else {
                    hospitalLiveData.postValue(emptyList()) // Handle API response failure
                }
            }

            override fun onFailure(call: Call<HospitalResponse>, t: Throwable) {
                hospitalLiveData.postValue(emptyList()) // Handle network failure
            }
        })
        return hospitalLiveData
    }
}
