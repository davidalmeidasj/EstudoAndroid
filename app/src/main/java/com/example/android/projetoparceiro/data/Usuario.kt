package com.example.android.projetoparceiro.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "usuarios")
class Usuario(
    @PrimaryKey()
    var id: String,
    var name: String?,
    var email: String?,
    var password: String?,
    val created_at: String?,
    var newPassword: String?,
    var token: String?
)