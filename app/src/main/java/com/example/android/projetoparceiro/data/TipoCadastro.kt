package com.example.android.projetoparceiro.data

enum class TipoCadastro(val id: Int) {
    CPF(1),
    CNPJ(2);

    fun toValue() = this.id
}
