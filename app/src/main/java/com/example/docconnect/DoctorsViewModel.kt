import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DoctorsViewModel : ViewModel() {
    private val repository = DoctorsRepository()
    private val _doctors = MutableLiveData<List<Doctor>>()
    val doctors: LiveData<List<Doctor>> get() = _doctors

    fun fetchDoctors() {
        repository.getDoctors { doctorList ->
            _doctors.postValue(doctorList)
        }
    }
}
