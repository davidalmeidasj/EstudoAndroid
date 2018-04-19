package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import java.util.*

@Entity(
        tableName = "local",
        indices = [
            (Index("usuario_id"))
        ],
        foreignKeys = [
                (android.arch.persistence.room.ForeignKey(entity = com.example.android.projetoparceiro.data.Usuario::class, parentColumns = kotlin.arrayOf("id_local"), childColumns = kotlin.arrayOf("usuario_id")))

        ]
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
        @ColumnInfo(name = "editado_em")
        var editadoEm: Date?
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
