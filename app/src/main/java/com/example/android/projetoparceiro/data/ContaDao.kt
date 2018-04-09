package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao interface ContaDao {
    @Query("select * from contas")
    fun getContas(): Array<Conta>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(token: Conta)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateConta(token: Conta)

    @Delete
    fun deleteConta(token: Conta)

    @Query("SELECT * FROM contas WHERE id = :id")
    fun getConta(id: Long): Flowable<Conta>
}
