package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*

@Dao interface LocalDao {
    @Query("select * from local")
    fun getLocal(): Local?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocal(vararg local: Local?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLocal(local: Local)

    @Delete
    fun deleteLocal(local: Local)

    @Query("SELECT * FROM local WHERE id = :id")
    fun getLocal(id: Long?): Local

    @Query("SELECT * FROM local WHERE id = null")
    fun getLocalNaoEnviados(): Array<Local?>

    @Query("SELECT * FROM local WHERE id_local = :idLocal")
    fun getLocalLocal(idLocal: Long): Local

    @Query("DELETE FROM local")
    fun delete()
}
