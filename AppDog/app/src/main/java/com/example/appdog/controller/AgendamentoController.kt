package com.example.appdog.controller

import android.content.Context
import com.example.appdog.model.AgendamentoModel
import com.example.appdog.database.DBHelper

/* classe é responsável por:
encapsular as operação crud
 */

class AgendamentoController(context: Context) {
    private val dbHelper: DBHelper = DBHelper(context)

    fun getAgendamentoById(id: Int): AgendamentoModel? {
        return dbHelper.getAgendamentoById(id)
    }

    fun deleteAgendamento(id: Int) {
        dbHelper.deleteAgendamento(id)
    }

    fun updateAgendamento(agendamento: AgendamentoModel): Int {
        return dbHelper.updateAgendamento(agendamento)
    }
}
