package com.example.appdog.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appdog.model.AgendamentoModel

/* classe é responsável por:
 manipulação do bd / importar o sql lite / estender
addAgendamento (create)
getAllAgendamentos (read)
getAgendamentoById (read)
updateAgendamento (update)
deleteAgendamento (delete) */



class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // definição de constantes
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "AgendamentoDB"
        private const val TABLE_AGENDAMENTO = "agendamento"

        // Colunas da tabela
        private const val KEY_ID = "id"
        private const val KEY_DAY = "day"
        private const val KEY_MONTH = "month"
        private const val KEY_YEAR = "year"
        private const val KEY_SERVICE = "service"
        private const val KEY_PET_NAME = "pet_name"
        private const val KEY_PET_BREED = "pet_breed"
        private const val KEY_PET_AGE = "pet_age"
        private const val KEY_PET_TYPE = "pet_type"
        private const val KEY_PET_SEX = "pet_sex"
        private const val KEY_PET_OBSERVATION = "pet_observation"
        private const val KEY_APPOINTMENT_TIME = "appointment_time"
    }

    // criação da tabela agendamento
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_AGENDAMENTO_TABLE = ("CREATE TABLE $TABLE_AGENDAMENTO("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_DAY TEXT,"
                + "$KEY_MONTH TEXT,"
                + "$KEY_YEAR TEXT,"
                + "$KEY_SERVICE TEXT,"
                + "$KEY_PET_NAME TEXT,"
                + "$KEY_PET_BREED TEXT,"
                + "$KEY_PET_AGE INTEGER,"
                + "$KEY_PET_TYPE TEXT,"
                + "$KEY_PET_SEX TEXT,"
                + "$KEY_PET_OBSERVATION TEXT,"
                + "$KEY_APPOINTMENT_TIME TEXT"
                + ")")
        db?.execSQL(CREATE_AGENDAMENTO_TABLE)
    }

    // atualiza o db
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_AGENDAMENTO")
        onCreate(db)
    }

    fun addAgendamento(agendamento: AgendamentoModel) {
        val db = this.writableDatabase
        val values = ContentValues()
        // Inserir dados do agendamento na tabela
        values.put(KEY_DAY, agendamento.day)
        values.put(KEY_MONTH, agendamento.month)
        values.put(KEY_YEAR, agendamento.year)
        values.put(KEY_SERVICE, agendamento.service)
        values.put(KEY_PET_NAME, agendamento.petName)
        values.put(KEY_PET_BREED, agendamento.petBreed)
        values.put(KEY_PET_AGE, agendamento.petAge)
        values.put(KEY_PET_TYPE, agendamento.petType)
        values.put(KEY_PET_SEX, agendamento.petSex)
        values.put(KEY_PET_OBSERVATION, agendamento.petObservation)
        values.put(KEY_APPOINTMENT_TIME, agendamento.appointmentTime)
        // Inserir linha
        db.insert(TABLE_AGENDAMENTO, null, values)
        db.close()
    }

    fun updateAgendamento(agendamento: AgendamentoModel): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        // Atualizar os dados do agendamento na tabela
        values.put(KEY_DAY, agendamento.day)
        values.put(KEY_MONTH, agendamento.month)
        values.put(KEY_YEAR, agendamento.year)
        values.put(KEY_SERVICE, agendamento.service)
        values.put(KEY_PET_NAME, agendamento.petName)
        values.put(KEY_PET_BREED, agendamento.petBreed)
        values.put(KEY_PET_AGE, agendamento.petAge)
        values.put(KEY_PET_TYPE, agendamento.petType)
        values.put(KEY_PET_SEX, agendamento.petSex)
        values.put(KEY_PET_OBSERVATION, agendamento.petObservation)
        values.put(KEY_APPOINTMENT_TIME, agendamento.appointmentTime)
        // Atualizar linha
        return db.update(TABLE_AGENDAMENTO, values, "$KEY_ID = ?", arrayOf(agendamento.id.toString()))
    }

    fun deleteAgendamento(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_AGENDAMENTO, "$KEY_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun getAllAgendamentos(): ArrayList<AgendamentoModel> {
        val agendamentosList = ArrayList<AgendamentoModel>()
        val selectQuery = "SELECT  * FROM $TABLE_AGENDAMENTO"

        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val agendamento = AgendamentoModel(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_DAY)),
                    cursor.getString(cursor.getColumnIndex(KEY_MONTH)),
                    cursor.getString(cursor.getColumnIndex(KEY_YEAR)),
                    cursor.getString(cursor.getColumnIndex(KEY_SERVICE)),
                    cursor.getString(cursor.getColumnIndex(KEY_PET_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_PET_BREED)),
                    cursor.getInt(cursor.getColumnIndex(KEY_PET_AGE)),
                    cursor.getString(cursor.getColumnIndex(KEY_PET_TYPE)),
                    cursor.getString(cursor.getColumnIndex(KEY_PET_SEX)),
                    cursor.getString(cursor.getColumnIndex(KEY_PET_OBSERVATION)),
                    cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_TIME))
                )
                agendamentosList.add(agendamento)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return agendamentosList
    }

    @SuppressLint("Range")
    fun getAgendamentoById(id: Int): AgendamentoModel? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_AGENDAMENTO,
            arrayOf(KEY_ID, KEY_DAY, KEY_MONTH, KEY_YEAR, KEY_SERVICE, KEY_PET_NAME, KEY_PET_BREED, KEY_PET_AGE, KEY_PET_TYPE, KEY_PET_SEX, KEY_PET_OBSERVATION, KEY_APPOINTMENT_TIME),
            "$KEY_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
        var agendamento: AgendamentoModel? = null
        if (cursor.moveToFirst()) {
            agendamento = AgendamentoModel(
                cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                cursor.getString(cursor.getColumnIndex(KEY_DAY)),
                cursor.getString(cursor.getColumnIndex(KEY_MONTH)),
                cursor.getString(cursor.getColumnIndex(KEY_YEAR)),
                cursor.getString(cursor.getColumnIndex(KEY_SERVICE)),
                cursor.getString(cursor.getColumnIndex(KEY_PET_NAME)),
                cursor.getString(cursor.getColumnIndex(KEY_PET_BREED)),
                cursor.getInt(cursor.getColumnIndex(KEY_PET_AGE)),
                cursor.getString(cursor.getColumnIndex(KEY_PET_TYPE)),
                cursor.getString(cursor.getColumnIndex(KEY_PET_SEX)),
                cursor.getString(cursor.getColumnIndex(KEY_PET_OBSERVATION)),
                cursor.getString(cursor.getColumnIndex(KEY_APPOINTMENT_TIME))
            )
        }
        cursor.close()
        return agendamento
    }
}
