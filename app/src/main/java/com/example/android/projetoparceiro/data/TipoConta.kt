package com.example.android.projetoparceiro.data


enum class TipoConta(val id: Int) {
    RECEITA(1),
    DESPESA(2);

    fun toValue() = this.id
}
