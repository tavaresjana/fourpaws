package com.example.appdog.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.appdog.R
import com.example.appdog.controller.AgendamentoController
import com.example.appdog.model.AgendamentoModel
import com.google.android.material.textfield.TextInputEditText
import android.widget.ArrayAdapter

class EditarAgendamentoActivity : AppCompatActivity() {

    private val agendamentoController = AgendamentoController(this)
    private var agendamentoId: Int = -1 // ID do agendamento a ser editado
    private var nome: String? = null // Nome do usuário

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_agendamento) // Defina o layout XML correto

        // Obtenha o ID do agendamento da intent
        agendamentoId = intent.getIntExtra("agendamento_id", -1)

        // Obtenha o nome do usuário da intent
        nome = intent.getStringExtra("nome")

        // Verifique se o ID do agendamento é válido
        if (agendamentoId != -1) {
            // Obtenha os detalhes do agendamento do banco de dados
            val agendamento = agendamentoController.getAgendamentoById(agendamentoId)

            // Preencha os Spinners de dia, mês e ano com as opções disponíveis
            val dayAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.days,
                android.R.layout.simple_spinner_item
            )
            dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            findViewById<Spinner>(R.id.spinnerDay).adapter = dayAdapter

            val monthAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.months,
                android.R.layout.simple_spinner_item
            )
            monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            findViewById<Spinner>(R.id.spinnerMonth).adapter = monthAdapter

            val yearAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.years,
                android.R.layout.simple_spinner_item
            )
            yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            findViewById<Spinner>(R.id.spinnerYear).adapter = yearAdapter

            if (agendamento != null) {
                // Selecionar a data atual do agendamento nos Spinners de dia, mês e ano
                findViewById<Spinner>(R.id.spinnerDay).setSelection(agendamento.day.toInt() - 1)
                findViewById<Spinner>(R.id.spinnerMonth).setSelection(getMonthIndex(agendamento.month))
                val yearPosition = yearAdapter.getPosition(agendamento.year)
                findViewById<Spinner>(R.id.spinnerYear).setSelection(yearPosition)
            }

            // Exiba os detalhes do agendamento nos campos de edição do formulário
            if (agendamento != null) {
                findViewById<Spinner>(R.id.spinnerService).apply {
                    val adapter = ArrayAdapter.createFromResource(
                        this@EditarAgendamentoActivity,
                        R.array.services,
                        android.R.layout.simple_spinner_item
                    )
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    this.adapter = adapter

                    // Selecionar o serviço atual
                    val position = adapter.getPosition(agendamento.service)
                    setSelection(position)
                }

                findViewById<TextInputEditText>(R.id.editTextPetName).setText(agendamento.petName)
                findViewById<TextInputEditText>(R.id.editTextPetBreed).setText(agendamento.petBreed)
                findViewById<TextInputEditText>(R.id.editTextPetAge).setText(agendamento.petAge.toString())
                findViewById<TextInputEditText>(R.id.editTextPetObservation).setText(agendamento.petObservation)

                // Preencher os campos de hora e minuto
                findViewById<EditText>(R.id.editTextHour).setText(agendamento.appointmentTime.substring(0, 2)) // Assumindo que o formato da hora é "HH:mm"
                findViewById<EditText>(R.id.editTextMinute).setText(agendamento.appointmentTime.substring(3, 5))
            }

            // Configurar os radio buttons para selecionar o tipo de animal
            val radioGroupPetType = findViewById<RadioGroup>(R.id.edit_radioGroupPetType)
            val radioButtonDog = findViewById<RadioButton>(R.id.edit_radioButtonDog)
            val radioButtonCat = findViewById<RadioButton>(R.id.edit_radioButtonCat)

            // Configurar os radio buttons para selecionar o sexo do animal
            val radioGroupPetSex = findViewById<RadioGroup>(R.id.edit_radioGroupPetSex)
            val radioButtonMale = findViewById<RadioButton>(R.id.edit_radioButtonMale)
            val radioButtonFemale = findViewById<RadioButton>(R.id.edit_radioButtonFemale)

            // Selecionar o tipo de animal atual
            when (agendamento?.petType) {
                "Cachorro" -> radioButtonDog.isChecked = true
                "Gato" -> radioButtonCat.isChecked = true
            }

            // Selecionar o sexo do animal atual
            when (agendamento?.petSex) {
                "Macho" -> radioButtonMale.isChecked = true
                "Fêmea" -> radioButtonFemale.isChecked = true
            }

            // Configurar o botão de salvar
            val btnSave = findViewById<Button>(R.id.btnSave)
            btnSave.setOnClickListener {
                // Obter os valores editados dos campos de edição
                val editedPetName = findViewById<TextInputEditText>(R.id.editTextPetName).text.toString()
                val editedPetBreed = findViewById<TextInputEditText>(R.id.editTextPetBreed).text.toString()
                val editedPetAge = findViewById<TextInputEditText>(R.id.editTextPetAge).text.toString().toInt()
                val editedPetObservation = findViewById<TextInputEditText>(R.id.editTextPetObservation).text.toString()

                // Obter o serviço selecionado do Spinner
                val spinnerService = findViewById<Spinner>(R.id.spinnerService)
                val editedService = spinnerService.selectedItem.toString()

                // Obter o tipo de animal selecionado
                val selectedPetType = when (radioGroupPetType.checkedRadioButtonId) {
                    radioButtonDog.id -> "Cachorro"
                    radioButtonCat.id -> "Gato"
                    else -> "Desconhecido"
                }

                // Obter o sexo do animal selecionado
                val selectedPetSex = when (radioGroupPetSex.checkedRadioButtonId) {
                    radioButtonMale.id -> "Macho"
                    radioButtonFemale.id -> "Fêmea"
                    else -> "Desconhecido"
                }

                // Obter os valores de hora e minuto
                val editedHour = findViewById<EditText>(R.id.editTextHour).text.toString()
                val editedMinute = findViewById<EditText>(R.id.editTextMinute).text.toString()
                val appointmentTime = "$editedHour:$editedMinute" // Assumindo o formato "HH:mm"

                // Atualizar o objeto de agendamento com os novos valores
                val updatedAgendamento = AgendamentoModel(
                    agendamentoId,
                    findViewById<Spinner>(R.id.spinnerDay).selectedItem.toString(),
                    findViewById<Spinner>(R.id.spinnerMonth).selectedItem.toString(),
                    findViewById<Spinner>(R.id.spinnerYear).selectedItem.toString(),
                    editedService, // Usar o serviço editado
                    editedPetName,
                    editedPetBreed,
                    editedPetAge,
                    selectedPetType,
                    selectedPetSex,
                    editedPetObservation,
                    appointmentTime // Novo campo de hora e minuto
                )

                // Chamar o método de atualização do agendamento no controlador
                agendamentoController.updateAgendamento(updatedAgendamento)

                // Criar uma Intent para retornar à tela de DetalhesAgendamento
                val resultIntent = Intent()
                resultIntent.putExtra("agendamento_id", agendamentoId)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }

            // Configurar o botão de cancelar
            val btnCancel = findViewById<Button>(R.id.buttonCancelar)
            btnCancel.setOnClickListener {
                // Apenas finalizar a atividade, sem passar resultado
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        } else {
            // O ID do agendamento não é válido, terminar a atividade
            finish()
        }
    }

    private fun getMonthIndex(month: String): Int {
        // Obtenha o array de meses do arquivo de recursos (res/values/arrays.xml)
        val monthsArray = resources.getStringArray(R.array.months)

        // Retorne o índice do mês especificado dentro do array de meses
        return monthsArray.indexOf(month)
    }
}
