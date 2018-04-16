package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*

@Entity(
        tableName = "pessoas",
        indices = [
            Index("usuario_id")
        ],
        foreignKeys = [
            (android.arch.persistence.room.ForeignKey(entity = com.example.android.projetoparceiro.data.Usuario::class, parentColumns = kotlin.arrayOf("id_local"), childColumns = kotlin.arrayOf("usuario_id")))

        ]
)
class Pessoa(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_local")
        var idLocal: Long?,
        var id: Long?,
        var nome: String?,
        var cadastro: String?,
        @Ignore
        var tipoCadastro: TipoCadastro?,
        @Ignore
        var usuario: Usuario?
) {
    constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null
    )
    var usuario_id: String? = usuario?.id
}
