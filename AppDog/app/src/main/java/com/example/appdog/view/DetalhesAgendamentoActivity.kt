package com.example.appdog.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.appdog.R
import com.example.appdog.controller.AgendamentoController
import com.example.appdog.model.AgendamentoModel


/* classe responsavel por:
agendar o serviço */

class DetalhesAgendamentoActivity : AppCompatActivity() {

    private val agendamentoController = AgendamentoController(this)

    companion object {
        private const val EDITAR_AGENDAMENTO_REQUEST = 2 // Pode ser qualquer número inteiro único
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_agendamento)


        val txtPetName = findViewById<TextView>(R.id.textPetName)
        val txtPetBreed = findViewById<TextView>(R.id.textViewPetBreedDetalhes)
        val txtPetAge = findViewById<TextView>(R.id.textViewPetAgeDetalhes)
        val txtPetType = findViewById<TextView>(R.id.textViewPetTypeDetalhes)
        val txtPetSex = findViewById<TextView>(R.id.textViewPetSexDetalhes)
        val txtPetObservation = findViewById<TextView>(R.id.textViewPetObservationDetalhes)
        val txtService = findViewById<TextView>(R.id.textService)
        val txtAppointmentTime = findViewById<TextView>(R.id.textAppointmentTime)
        val txtDay = findViewById<TextView>(R.id.textViewDay)
        val txtMonth = findViewById<TextView>(R.id.textViewMonth)
        val txtYear = findViewById<TextView>(R.id.textViewYear)

        val btnUpdate = findViewById<Button>(R.id.buttonEditarAgendamento)
        val btnDelete = findViewById<Button>(R.id.buttonDeletarAgendamento)
        val btnVoltar = findViewById<Button>(R.id.buttonVoltarMeusAgendamentos)

        val agendamentoId = intent.getIntExtra("agendamento_id", -1)
        val nome = intent.extras?.getString("nome")

        if (agendamentoId != -1) {
            val agendamento = agendamentoController.getAgendamentoById(agendamentoId)
            // exibe os detalhes do agendamento nos text views
            txtPetName.text = agendamento?.petName
            txtPetBreed.text = agendamento?.petBreed
            txtPetAge.text = agendamento?.petAge.toString()
            txtPetType.text = agendamento?.petType
            txtPetSex.text = agendamento?.petSex
            txtPetObservation.text = agendamento?.petObservation
            txtService.text = agendamento?.service
            txtAppointmentTime.text = agendamento?.appointmentTime
            txtDay.text = agendamento?.day
            txtMonth.text = agendamento?.month
            txtYear.text = agendamento?.year

            btnUpdate.setOnClickListener {
                // Abrir a atividade de atualização do agendamento, passando o ID do agendamento e o nome do usuário
                val intent = Intent(this@DetalhesAgendamentoActivity, EditarAgendamentoActivity::class.java)
                intent.putExtra("agendamento_id", agendamentoId)
                intent.putExtra("nome", nome) // Passando o nome do usuário
                startActivityForResult(intent, EDITAR_AGENDAMENTO_REQUEST)
            }

            btnDelete.setOnClickListener {
                agendamento?.id?.let { id ->
                    agendamentoController.deleteAgendamento(id)
                    val resultIntent = Intent()
                    resultIntent.putExtra("agendamento_id", id)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            }

            // Lógica para o btn voltar
            btnVoltar.setOnClickListener {
                val intent = Intent(this@DetalhesAgendamentoActivity, MeusAgendamentos::class.java)
                intent.putExtra("nome", nome) // Passando o nome do usuário
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDITAR_AGENDAMENTO_REQUEST && resultCode == Activity.RESULT_OK) {
            // att a tela com os dados modificados
            val agendamentoId = intent.getIntExtra("agendamento_id", -1)
            if (agendamentoId != -1) {
                val agendamento = agendamentoController.getAgendamentoById(agendamentoId)
                // att os detalhes do agendamento
                val txtPetName = findViewById<TextView>(R.id.textPetName)
                val txtPetBreed = findViewById<TextView>(R.id.textViewPetBreedDetalhes)
                val txtPetAge = findViewById<TextView>(R.id.textViewPetAgeDetalhes)
                val txtPetType = findViewById<TextView>(R.id.textViewPetTypeDetalhes)
                val txtPetSex = findViewById<TextView>(R.id.textViewPetSexDetalhes)
                val txtPetObservation = findViewById<TextView>(R.id.textViewPetObservationDetalhes)
                val txtService = findViewById<TextView>(R.id.textService)
                val txtAppointmentTime = findViewById<TextView>(R.id.textAppointmentTime)
                val txtDay = findViewById<TextView>(R.id.textViewDay)
                val txtMonth = findViewById<TextView>(R.id.textViewMonth)
                val txtYear = findViewById<TextView>(R.id.textViewYear)

                txtPetName.text = agendamento?.petName
                txtPetBreed.text = agendamento?.petBreed
                txtPetAge.text = agendamento?.petAge.toString()
                txtPetType.text = agendamento?.petType
                txtPetSex.text = agendamento?.petSex
                txtPetObservation.text = agendamento?.petObservation
                txtService.text = agendamento?.service
                txtAppointmentTime.text = agendamento?.appointmentTime
                txtDay.text = agendamento?.day
                txtMonth.text = agendamento?.month
                txtYear.text = agendamento?.year
            }
        }
    }
}
