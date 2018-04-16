package com.example.android.projetoparceiro.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "documentos_anexo")
class DocumentoAnexo (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_local")
    var idLocal: Long?,
    var id: Long?,
    var mimeType: String?,
    var nome: String?,
    var conteudo: String?
)