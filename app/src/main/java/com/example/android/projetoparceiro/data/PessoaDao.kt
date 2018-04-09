package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao interface PessoaDao {

    @Query("select * from pessoas")
    fun getPessoas(): Array<Pessoa>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(token: Pessoa)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePessoa(token: Pessoa)

    @Delete
    fun deletePessoa(token: Pessoa)

    @Query("SELECT * FROM pessoas WHERE id = :id")
    fun getPessoa(id: Long): Flowable<Pessoa>
}
