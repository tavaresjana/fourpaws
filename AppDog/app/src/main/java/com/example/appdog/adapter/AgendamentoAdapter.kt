package com.example.appdog.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appdog.R
import com.example.appdog.model.AgendamentoModel

/* classe é responsável por:
-exibir os dados de agendamentos em uma recycler view (meus agendamentos)
-eventos de click
* */

// Exibe uma lista de agendamentos
class AgendamentoAdapter(private val agendamentosList: MutableList<AgendamentoModel>) : RecyclerView.Adapter<AgendamentoAdapter.AgendamentoViewHolder>() {

    // Interface para eventos do click
    interface OnItemClickListener {
        fun onItemClick(agendamento: AgendamentoModel, position: Int)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    // Referencia aos elementos de cada item
    inner class AgendamentoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val petNameTextView: TextView = itemView.findViewById(R.id.textPetName)
        val serviceTextView: TextView = itemView.findViewById(R.id.textService)
        val appointmentTimeTextView: TextView = itemView.findViewById(R.id.textAppointmentTime)
        val dayTextView: TextView = itemView.findViewById(R.id.day)
        val monthTextView: TextView = itemView.findViewById(R.id.month)
        val yearTextView: TextView = itemView.findViewById(R.id.year)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.onItemClick(agendamentosList[position], position)
                }
            }
        }
    }

    // Cria instâncias
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendamentoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_agendamento, parent, false)
        return AgendamentoViewHolder(itemView)
    }

    // Atualiza elementos de layout de cada item
    override fun onBindViewHolder(holder: AgendamentoViewHolder, position: Int) {
        val currentItem = agendamentosList[position]
        holder.petNameTextView.text = currentItem.petName
        holder.serviceTextView.text = currentItem.service
        holder.appointmentTimeTextView.text = currentItem.appointmentTime
        holder.dayTextView.text = currentItem.day
        holder.monthTextView.text = currentItem.month
        holder.yearTextView.text = currentItem.year
    }

    override fun getItemCount() = agendamentosList.size

    // remove um agendamento da lista
    fun removeAgendamento(agendamentoId: Int) {
        val index = agendamentosList.indexOfFirst { it.id == agendamentoId }
        if (index != -1) {
            agendamentosList.removeAt(index)
            notifyItemRemoved(index)
        }
    }
}
