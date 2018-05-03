package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import java.util.*

@Entity(
        tableName = "local"
)
class Local(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_local")
        var idLocal: Long?,
        var id: Long?,
        var nome: String?,
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
            null
    )
}
