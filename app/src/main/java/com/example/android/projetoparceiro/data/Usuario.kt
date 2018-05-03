package com.example.android.projetoparceiro.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "usuarios")
class Usuario(
    @PrimaryKey()
    @ColumnInfo(name = "id_local")
    var idLocal: Long?,
    var id: String,
    var name: String?,
    var email: String?,
    var password: String?,
    val created_at: Date?,
    var newPassword: String?,
    var token: String?,
    @ColumnInfo(name = "criado_em")
    var criadoEm: Date?,
    @ColumnInfo(name = "modificado_em")
    var modificadoEm: Date?
)