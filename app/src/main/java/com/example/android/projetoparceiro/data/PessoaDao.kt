package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*

@Dao interface PessoaDao {

    @Query("select * from pessoas")
    fun getPessoas(): Array<Pessoa>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPessoas(vararg pessoa: Pessoa?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePessoa(pessoa: Pessoa)

    @Delete
    fun deletePessoa(pessoa: Pessoa)

    @Query("SELECT * FROM pessoas WHERE id = :id")
    fun getPessoa(id: Long): Pessoa

    @Query("SELECT * FROM pessoas WHERE id = null")
    fun getPessoasNaoEnviados(): Array<Pessoa?>

    @Query("SELECT * FROM pessoas WHERE id_local = :idLocal")
    fun getPessoaLocal(idLocal: Long): Pessoa
}
