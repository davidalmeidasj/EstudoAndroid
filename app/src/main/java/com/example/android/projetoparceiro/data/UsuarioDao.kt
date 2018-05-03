package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface UsuarioDao {

    @Query("select * from usuarios")
    fun getUsuario(): Usuario?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUsuario(usuario: Usuario)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUsuario(usuario: Usuario)

    @Delete
    fun deleteUsuario(usuario: Usuario)

    @Query("DELETE FROM usuarios")
    fun delete()
}