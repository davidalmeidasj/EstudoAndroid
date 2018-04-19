package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*

@Dao
interface LancamentoDao {

    @Query("select * from lancamentos")
    fun getLancamentos(): Array<Lancamento?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLancamentos(lancamento: Lancamento)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLancamento(lancamento: Lancamento)

    @Delete
    fun deleteLancamento(lancamento: Lancamento)

    @Query("SELECT * FROM lancamentos WHERE id = :id")
    fun getLancamento(id: Long?): Lancamento

    @Query("SELECT * FROM lancamentos WHERE id = null")
    fun getLancamentosNaoEnviados(): Array<Lancamento?>

    @Query("SELECT * FROM lancamentos WHERE id_local = :idLocal")
    fun getLancamentoLocal(idLocal: Long): Lancamento

    @Query("DELETE FROM lancamentos")
    fun delete()
}