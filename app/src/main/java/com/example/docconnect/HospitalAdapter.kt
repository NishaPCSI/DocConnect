import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.docconnect.R

class HospitalAdapter(private val hospitals: List<Hospital>) :
    RecyclerView.Adapter<HospitalAdapter.HospitalViewHolder>() {

    class HospitalViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.hospitalName)
        val address: TextView = view.findViewById(R.id.hospitalAddress)
        val distance: TextView = view.findViewById(R.id.hospitalDistance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_hospital, parent, false)
        return HospitalViewHolder(view)
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val hospital = hospitals[position]
        holder.name.text = hospital.hospitalName
        holder.address.text = hospital.hospitalAddress
        holder.distance.text = hospital.hospitalDistance
    }

    override fun getItemCount(): Int {
        return hospitals.size
    }
}
