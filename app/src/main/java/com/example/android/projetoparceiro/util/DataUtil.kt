package com.example.android.projetoparceiro.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DataUtil {
    private const val DATA_SIMPLES = "dd/MM/yyyy"
    private const val HORA = "HH:mm"

    private val formatadorSimples = SimpleDateFormat(DATA_SIMPLES)
    private val formatadorHora = SimpleDateFormat(HORA)
    private val formatadorDataHora = SimpleDateFormat(DATA_SIMPLES + " " + HORA)

    fun formatarData(data: Date): String =
            formatadorSimples.format(data)

    fun dateStrToDate(data: String): Date =
            try {
                formatadorSimples.parse(data)
            } catch (_: ParseException) {
                Date()
            }

    fun dataValida(data: String): Boolean =
            try {
                formatadorSimples.parse(data)
                true
            } catch (_: ParseException) {
                false
            }

    fun formatarHora(data: Date): String =
            formatadorHora.format(data)

    fun timeStrToDate(hora: String): Date =
            try {
                formatadorHora.parse(hora)
            } catch (_: ParseException) {
                Date()
            }

    fun horaValida(hora: String): Boolean =
            try {
                formatadorHora.parse(hora)
                true
            } catch (_: ParseException) {
                false
            }

    fun dateTimeStrToDate(data: String): Date =
            try {
                formatadorDataHora.parse(data)
            } catch (_: ParseException) {
                Date()
            }
}