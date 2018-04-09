package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import java.util.*

@Entity(
        tableName = "lancamentos",
        indices = [
            (Index("local_id")),
            (Index("pessoa_id")),
            (Index("conta_id")),
            (Index("usuario_id"))
        ],
        foreignKeys = [
            (ForeignKey(entity = Local::class, parentColumns = arrayOf("id"), childColumns = arrayOf("local_id"))),
            (ForeignKey(entity = Pessoa::class, parentColumns = arrayOf("id"), childColumns = arrayOf("pessoa_id"))),
            (ForeignKey(entity = Conta::class, parentColumns = arrayOf("id"), childColumns = arrayOf("conta_id"))),
            (ForeignKey(entity = Usuario::class, parentColumns = arrayOf("id"), childColumns = arrayOf("usuario_id")))

        ]
)
data class Lancamento(
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @Ignore
        var local: Local?,
        @Ignore
        var pessoa: Pessoa?,
        @Ignore
        var conta: Conta?,
        var dataExecucao: String?,
        var valor: Float?,
        @Ignore
        var tipo: TipoLancamento?,
        @Ignore
        var usuario: Usuario?,
        @Ignore
        var documentos: ArrayList<DocumentoAnexo>?
) {

    constructor() : this(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
            )

    @ColumnInfo(name = "local_id")
    var localId: Long? = local?.id
    @ColumnInfo(name = "pessoa_id")
    var pessoaId: Long? = pessoa?.id
    @ColumnInfo(name = "conta_id")
    var contaId: Long? = conta?.id
    @ColumnInfo(name = "usuario_id")
    var usuarioId: String? = usuario?.id
}
