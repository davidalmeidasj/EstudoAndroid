package com.example.android.projetoparceiro.util

import com.example.android.projetoparceiro.data.Conta
import java.util.*

class RecipienteClassesVazias {

    fun getConta(): Conta {
        return Conta(null,
                null,
                null,
                null,
                null,
                null,
                null)
    }

    fun getDate(): Date {
        return Date()
    }
}