package com.example.appdog.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.appdog.R
import com.example.appdog.adapter.ServicosAdapter
import com.example.appdog.database.DBHelper
import com.example.appdog.databinding.ActivityHomeBinding
import com.example.appdog.model.Servicos

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var servicosAdapter: ServicosAdapter
    private val listaServicos: MutableList<Servicos> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val nome = intent.extras?.getString("nome")
        binding.txtNomeUsuario.text = "Bem vindo(a), $nome"

        val recyclerViewServicos = binding.recyclerViewServicos
        recyclerViewServicos.layoutManager = GridLayoutManager(this, 2)
        servicosAdapter = ServicosAdapter(this, listaServicos)
        recyclerViewServicos.setHasFixedSize(true)
        recyclerViewServicos.adapter = servicosAdapter
        getServicos()

        binding.btAgendar.setOnClickListener {
            val intent = Intent(this, Agendamento::class.java)
            intent.putExtra("nome", nome)
            startActivity(intent)
        }

        // Verificar e atualizar a mensagem sobre agendamentos
        checkAgendamentos(nome)
    }

    override fun onResume() {
        super.onResume()
        // Atualizar a mensagem sobre agendamentos quando a atividade é retomada
        val nome = intent.extras?.getString("nome")
        checkAgendamentos(nome)
    }

    private fun getServicos() {
        val servico1 = Servicos(R.drawable.img1, "Veterinário")
        listaServicos.add(servico1)

        val servico2 = Servicos(R.drawable.img2, "Passeios")
        listaServicos.add(servico2)

        val servico3 = Servicos(R.drawable.img3, "Nutrição")
        listaServicos.add(servico3)

        val servico4 = Servicos(R.drawable.img4, "Avaliação pet")
        listaServicos.add(servico4)
    }

    private fun checkAgendamentos(nome: String?) {
        val dbHelper = DBHelper(this)
        val agendamentosList = dbHelper.getAllAgendamentos()

        binding.txtMensagemAgendamentos?.let { textView ->
            if (agendamentosList.isEmpty()) {
                textView.text = "Você não possui agendamentos"
                textView.setOnClickListener(null)
            } else {
                textView.text = "Ir aos meus agendamentos"
                textView.setOnClickListener {
                    val intent = Intent(this, MeusAgendamentos::class.java)
                    intent.putExtra("nome", nome)
                    startActivity(intent)
                }
            }
        }
    }
}
