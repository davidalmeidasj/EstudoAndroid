package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import java.util.*

@Entity(
        tableName = "pessoas"
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
        var usuario: Usuario?,
        @ColumnInfo(name = "criado_em")
        var criadoEm: Date?,
        @ColumnInfo(name = "modificado_em")
        var modificadoEm: Date?
) {
    constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
    )
}
