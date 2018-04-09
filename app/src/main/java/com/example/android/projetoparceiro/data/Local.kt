package com.example.android.projetoparceiro.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(
        tableName = "local",
        indices = [
            (Index("usuario_id"))
        ],
        foreignKeys = [
                (android.arch.persistence.room.ForeignKey(entity = com.example.android.projetoparceiro.data.Usuario::class, parentColumns = kotlin.arrayOf("id"), childColumns = kotlin.arrayOf("usuario_id")))

        ]
)
class Local(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        var nome: String?,
        @Ignore
        var usuario: Usuario?
) {
    constructor() : this(
            null,
            null,
            null
    )
        var usuario_id: String? = usuario?.id
}
