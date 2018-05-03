package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*

@Dao
interface JsonDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJsonData(jsonData: JsonData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateJsonData(jsonData: JsonData)

    @Delete
    fun deleteJsonData(jsonData: JsonData)

    @Query("SELECT * FROM json_data")
    fun getJsonData(): Array<JsonData>


    @Query("DELETE FROM json_data")
    fun delete()
}