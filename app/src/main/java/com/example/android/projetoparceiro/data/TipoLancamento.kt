package com.example.android.projetoparceiro.data


enum class TipoLancamento(val id: Int) {
    DEBITO(1),
    CREDITO(2);

    fun toValue() = this.id
}
