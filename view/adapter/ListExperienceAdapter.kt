package mvp.implement.kotlin.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mvp.implement.kotlin.Model.Experience
import mvp.implement.kotlin.Model.UserExperience
import mvp.implement.kotlin.R
import kotlinx.android.synthetic.main.item_user_experience.view.*
import java.text.SimpleDateFormat
import java.util.*


interface ListExperienceListener {
    fun onDetailSelected(experience: UserExperience)
}

class ListExperienceAdapter(private val experiences: List<UserExperience>?, private val context: Context) : RecyclerView.Adapter<ListExperienceAdapter.ViewHolder>() {

    var listener: ListExperienceListener = context as ListExperienceListener

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ListExperienceAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_user_experience, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return experiences!!.size
    }

    override fun onBindViewHolder(holder: ListExperienceAdapter.ViewHolder, position: Int) {

        val item = experiences!![position]

        holder.dtLamentacao.text = toDate(item!!.creation_date)
        holder.detalhe.text = item.original_description

        holder.btDetalhe.setOnClickListener {
            listener.onDetailSelected(item)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val detalhe = itemView.fieldDetalhe
        val dtLamentacao = itemView.dtLamentacao
        val btDetalhe = itemView.btShowDetalhe
    }

    fun toDate(date: Long): String {
        val dateFormat = SimpleDateFormat("dd 'de' MMMM 'de' yyyy ',' HH:mm")
        return dateFormat.format(Date(date))
    }
}
