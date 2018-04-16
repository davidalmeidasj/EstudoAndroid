package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import com.example.android.projetoparceiro.R
import com.example.android.projetoparceiro.util.DateConverter
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
            (ForeignKey(entity = Local::class, parentColumns = arrayOf("id_local"), childColumns = arrayOf("local_id"))),
            (ForeignKey(entity = Pessoa::class, parentColumns = arrayOf("id_local"), childColumns = arrayOf("pessoa_id"))),
            (ForeignKey(entity = Conta::class, parentColumns = arrayOf("id_local"), childColumns = arrayOf("conta_id"))),
            (ForeignKey(entity = Usuario::class, parentColumns = arrayOf("id_local"), childColumns = arrayOf("usuario_id")))

        ]
)
data class Lancamento(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id_local")
        var idLocal: Long?,
        var id: Long?,
        @Ignore
        var local: Local?,
        @Ignore
        var pessoa: Pessoa?,
        @Ignore
        var conta: Conta?,
        @ColumnInfo(name = "data_execucao")
        var dataExecucao: Date?,
        var valor: Float?,
        @Ignore
        var tipo: Int,
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
            0,
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

    fun tipoString(): String {
        return when (tipo) {
            1 -> "Debito"
            2 -> "Crédito"
            else -> { // Note the block
                ""
            }
        }
    }

    fun tipoImage(): Int {
        return when (tipo) {
            1 -> R.drawable.ic_trending_up_black_24dp
            2 -> R.drawable.ic_trending_down_black_24dp
            else -> { // Note the block
                R.drawable.ic_trending_up_black_24dp
            }
        }
    }
}
