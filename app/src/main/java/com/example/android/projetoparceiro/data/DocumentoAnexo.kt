package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import java.util.*

@Entity(
        tableName = "documentos_anexo",

        indices = [
            (Index("lancamento_id"))
        ],
        foreignKeys = [
            (ForeignKey(entity = Lancamento::class, parentColumns = arrayOf("id_local"), childColumns = arrayOf("lancamento_id"), onDelete = ForeignKey.CASCADE))

        ]
)
class DocumentoAnexo (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_local")
    var idLocal: Long?,
    var id: Long?,
    var mimeType: String?,
    var nome: String?,
    var conteudo: String?,
    @ColumnInfo(name = "lancamento_id")
    var lancamentoId: Long?,
    @ColumnInfo(name = "criado_em")
    var criadoEm: Date?,
    @ColumnInfo(name = "modificado_em")
    var modificadoEm: Date?
)