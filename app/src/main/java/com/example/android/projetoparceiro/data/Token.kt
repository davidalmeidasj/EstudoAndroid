package com.example.android.projetoparceiro.data

import android.annotation.SuppressLint
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = Token.TABLE_NAME)
class Token(@PrimaryKey(autoGenerate = true)
            @ColumnInfo(name = COLUMN_ID)
            var id: Int?,
            @ColumnInfo(name = COLUMN_TOKEN)
            var token: String) {

    @SuppressLint("SimpleDateFormat")
    @ColumnInfo(name = COLUMN_CREATED_AT)
    var createdAt: String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())

    companion object {
        const val TABLE_NAME = "tokens"
        const val COLUMN_ID = "id"
        const val COLUMN_TOKEN = "token"
        const val COLUMN_CREATED_AT = "created_at"
    }

}