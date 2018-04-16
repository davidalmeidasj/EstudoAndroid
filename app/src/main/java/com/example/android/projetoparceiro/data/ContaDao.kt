package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*

@Dao interface ContaDao {


    @Query("select * from contas")
    fun getContas(): Array<Conta>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContas(vararg conta: Conta?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateConta(conta: Conta)

    @Delete
    fun deleteConta(conta: Conta)

    @Query("SELECT * FROM contas WHERE id = :id")
    fun getConta(id: Long): Conta

    @Query("SELECT * FROM contas WHERE id = null")
    fun getContasNaoEnviados(): Array<Conta?>

    @Query("SELECT * FROM contas WHERE id_local = :idLocal")
    fun getContaLocal(idLocal: Long): Conta
}
