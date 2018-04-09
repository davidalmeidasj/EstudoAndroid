package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface LancamentoDao {

    @Query("select * from lancamentos")
    fun getLancamentos(): Array<Lancamento?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLancamento(token: Lancamento)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLancamento(token: Lancamento)

    @Delete
    fun deleteLancamento(token: Lancamento)

    @Query("SELECT * FROM lancamentos WHERE id = :id")
    fun getLancamento(id: Long): Flowable<Lancamento>
}