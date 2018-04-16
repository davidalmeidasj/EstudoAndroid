package com.example.android.projetoparceiro.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat

object NumeroUtil {
    private val regex: Regex

    init {
        val sep = if (getSeparadorDecimal() == '.') "\\." else getSeparadorDecimal()
        val exp = "^-?\\d+$sep?\\d*"
        regex = Regex(exp)
    }

    fun formatarMoeda(valor: Number): String {
        val formatador = NumberFormat.getCurrencyInstance()
        return formatador.format(valor)
    }

    fun formatarMoedaSemSimbolo(valor: Number): String =
            retornarFormatadorSemSimbolo().format(valor)

    fun validar(num: String): Boolean {
        return regex.matches(num)
    }

    fun fromString(num: String): Number =
            retornarFormatadorSemSimbolo().parse(num)


    fun getSeparadorDecimal(): Char =
            DecimalFormatSymbols.getInstance().decimalSeparator

    private fun retornarFormatadorSemSimbolo(): DecimalFormat {
        val formatador: DecimalFormat = NumberFormat.getCurrencyInstance() as DecimalFormat
        val simbolo = formatador.decimalFormatSymbols.apply {
            currencySymbol = ""
        }
        formatador.decimalFormatSymbols = simbolo
        return formatador
    }
}