package com.example.appdog.model

// representa o bd, contendo as info do agendamento em um objeto (agendamento model)
data class AgendamentoModel(
    val id: Int,
    val day: String,
    val month: String,
    val year: String,
    val service: String,
    val petName: String,
    val petBreed: String,
    val petAge: Int,
    val petType: String,
    val petSex: String,
    val petObservation: String,
    val appointmentTime: String
)
