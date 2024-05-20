package com.example.appdog.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.appdog.R
import com.example.appdog.databinding.ActivityAgendamentoBinding
import com.example.appdog.database.DBHelper
import com.example.appdog.model.AgendamentoModel
import android.widget.ArrayAdapter
import android.widget.Toast

/* classe responsavel por:
agendar o serviço */


class Agendamento : AppCompatActivity() {
    private lateinit var binding: ActivityAgendamentoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAgendamentoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura os adapters dos Spinners
        val days = (1..30).map { it.toString() }
        val months = resources.getStringArray(R.array.months).toList()
        val years = resources.getStringArray(R.array.years).toList()
        val services = resources.getStringArray(R.array.services).toList()

        val dayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days)
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDay.adapter = dayAdapter

        val monthAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, months)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMonth.adapter = monthAdapter

        val yearAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerYear.adapter = yearAdapter

        val serviceAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, services)
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerService.adapter = serviceAdapter

        // lógica do btn  agendar
        binding.buttonAgendar.setOnClickListener {
            if (validateInputs()) {
                val agendamento = createAgendamento()
                val dbHelper = DBHelper(this)
                dbHelper.addAgendamento(agendamento)

                // Passa o nome do usuário como um extra para MeusAgendamentos
                val nome = intent.extras?.getString("nome")
                val intent = Intent(this, MeusAgendamentos::class.java)
                intent.putExtra("nome", nome)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

        // lógica do btb cancelar
        binding.buttonCancelar.setOnClickListener {
            val nome = intent.extras?.getString("nome")  // Obtém o nome do Intent
            val intent = Intent(this, Home::class.java)
            intent.putExtra("nome", nome)  // Passa o nome para a Home
            startActivity(intent)
        }
    }

    private fun validateInputs(): Boolean {
        return binding.editTextPetName.text.isNotEmpty() &&
                binding.editTextPetBreed.text.isNotEmpty() &&
                binding.editTextPetAge.text.isNotEmpty() &&
                binding.radioGroupPetType.checkedRadioButtonId != -1 &&
                binding.radioGroupPetSex.checkedRadioButtonId != -1 &&
                binding.editTextPetObservation.text.isNotEmpty() &&
                binding.editTextHour.text.isNotEmpty() &&
                binding.editTextMinute.text.isNotEmpty()
    }

    private fun createAgendamento(): AgendamentoModel {
        val selectedDay = binding.spinnerDay.selectedItem.toString()
        val selectedMonth = binding.spinnerMonth.selectedItem.toString()
        val selectedYear = binding.spinnerYear.selectedItem.toString()
        val selectedService = binding.spinnerService.selectedItem.toString()
        val petName = binding.editTextPetName.text.toString()
        val petBreed = binding.editTextPetBreed.text.toString()
        val petAge = binding.editTextPetAge.text.toString().toIntOrNull() ?: 0
        val selectedPetType = when (binding.radioGroupPetType.checkedRadioButtonId) {
            R.id.radioButtonDog -> "Cachorro"
            R.id.radioButtonCat -> "Gato"
            else -> "Unknown"
        }
        val selectedPetSex = when (binding.radioGroupPetSex.checkedRadioButtonId) {
            R.id.radioButtonMale -> "Macho"
            R.id.radioButtonFemale -> "Fêmea"
            else -> "Unknown"
        }
        val petObservation = binding.editTextPetObservation.text.toString()
        val hour = binding.editTextHour.text.toString()
        val minute = binding.editTextMinute.text.toString()
        val appointmentTime = "$hour:$minute"


        val id = 0

        return AgendamentoModel(
            id = id,
            day = selectedDay,
            month = selectedMonth,
            year = selectedYear,
            service = selectedService,
            petName = petName,
            petBreed = petBreed,
            petAge = petAge,
            petType = selectedPetType,
            petSex = selectedPetSex,
            petObservation = petObservation,
            appointmentTime = appointmentTime
        )
    }
}
