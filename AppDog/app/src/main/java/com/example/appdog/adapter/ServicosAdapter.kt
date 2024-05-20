package com.example.appdog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appdog.databinding.ServicosItemBinding
import com.example.appdog.model.Servicos

/*  classe é responsável por:
- exibir os serviços na view binding / recycler
* */

class ServicosAdapter(private val context: Context, private val listaServicos: MutableList<Servicos>) :
    RecyclerView.Adapter<ServicosAdapter.ServicosViewHolder>() {

    // Método quando o recyclerView precisa criar um novo ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicosViewHolder {

        val itemLista = ServicosItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ServicosViewHolder(itemLista)
    }

    override fun getItemCount() = listaServicos.size

    override fun onBindViewHolder(holder: ServicosViewHolder, position: Int) {
        val servico = listaServicos[position]
        holder.bind(servico)
    }

    // ViewHolder para exibir os itens de serviço
    inner class ServicosViewHolder(private val binding: ServicosItemBinding) : RecyclerView.ViewHolder(binding.root) {

        // referências as img
        private val imgServico = binding.imgServico
        private val txtServico = binding.txtServico

        // Método para associar os dados do serviço ao layout do item de serviço
        fun bind(servico: Servicos) {
            imgServico.setImageResource(servico.img!!)
            txtServico.text = servico.nome
        }
    }
}
