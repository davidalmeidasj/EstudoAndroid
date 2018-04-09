package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface UsuarioDao {

    @Query("select * from usuarios")
    fun getUsuarios(): Array<Usuario>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(token: Usuario)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUsuario(token: Usuario)

    @Delete
    fun deleteUsuario(token: Usuario)

    @Query("SELECT * FROM usuarios WHERE id = :id")
    fun getUsuario(id: Long): Flowable<Usuario>
}