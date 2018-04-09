package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao interface LocalDao {
    @Query("select * from local")
    fun getLocal(): Local?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(token: Local)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateLocal(token: Local)

    @Delete
    fun deleteLocal(token: Local)

    @Query("SELECT * FROM local WHERE id = :id")
    fun getLocal(id: Long): Flowable<Local>
}
