package com.example.appdog.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appdog.database.DBHelper
import com.example.appdog.model.AgendamentoModel
import com.example.appdog.databinding.ActivityMeusAgendamentosBinding
import com.example.appdog.adapter.AgendamentoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MeusAgendamentos : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AgendamentoAdapter
    private lateinit var binding: ActivityMeusAgendamentosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeusAgendamentosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val nome = intent.extras?.getString("nome")
        val txtNomeUsuario = binding.txtNomeUsuario // Aqui obtém o TextView usando o binding

        // Verificar se o nome não é nulo antes de configurar o texto
        nome?.let {
            txtNomeUsuario.text = "Olá, $it! Seus agendamentos estão aqui:"
        }

        recyclerView = binding.recyclerView // Aqui obtém o RecyclerView usando o binding
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Instanciar DBHelper
        val dbHelper = DBHelper(this)

        // Obter todos os agendamentos do banco de dados
        val agendamentosList: ArrayList<AgendamentoModel> = dbHelper.getAllAgendamentos()

        // Inicializar o adapter com a lista de agendamentos e o nome do usuário
        adapter = AgendamentoAdapter(agendamentosList)

        // Definir o adapter no RecyclerView
        recyclerView.adapter = adapter

        // Adicionar um listener de clique ao adapter
        adapter.setOnItemClickListener(object : AgendamentoAdapter.OnItemClickListener {
            override fun onItemClick(agendamento: AgendamentoModel, position: Int) {
                // Quando um agendamento é clicado, abra a atividade de detalhes do agendamento
                val intent = Intent(this@MeusAgendamentos, DetalhesAgendamentoActivity::class.java)
                val agendamentoId = agendamento.id ?: -1 // Usando -1 como valor padrão se o id for nulo
                intent.putExtra("agendamento_id", agendamentoId)
                intent.putExtra("nome", nome) // Passando o nome do usuário
                startActivityForResult(intent, DETALHES_AGENDAMENTO_REQUEST)
            }
        })

        // Adicionar o listener para o botão backHome
        binding.backHome.setOnClickListener {
            val intent = Intent(this, Home::class.java)
            intent.putExtra("nome", nome)  // Passando o nome do usuário para Home
            startActivity(intent)
        }
// ir para Agendamento
        binding.createAgendamento.setOnClickListener {
            val nome = intent.getStringExtra("nome") // Obtém o nome do usuário da intent atual
            val intent = Intent(this@MeusAgendamentos, Agendamento::class.java)
            intent.putExtra("nome", nome) // Passa o nome do usuário para a atividade Agendamento
            startActivity(intent)
        }


    }

    companion object {
        private const val DETALHES_AGENDAMENTO_REQUEST = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == DETALHES_AGENDAMENTO_REQUEST && resultCode == Activity.RESULT_OK) {
            val agendamentoId = data?.getIntExtra("agendamento_id", -1) ?: -1 // Usando -1 como valor padrão
            adapter.removeAgendamento(agendamentoId)
            adapter.notifyDataSetChanged() // Notificar o adaptador sobre a alteração na lista de agendamentos
        }
    }
}
