import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class HospitalViewModel : ViewModel() {
    private val repository = HospitalRepository()

    fun getHospitals(): LiveData<List<Hospital>> {
        return repository.fetchHospitals()
    }
}
