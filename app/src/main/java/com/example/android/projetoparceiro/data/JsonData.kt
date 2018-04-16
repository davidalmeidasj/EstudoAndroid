package com.example.android.projetoparceiro.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(
        tableName = "json_data"
)
class JsonData (
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = "json_data")
        var jsonData: String
)