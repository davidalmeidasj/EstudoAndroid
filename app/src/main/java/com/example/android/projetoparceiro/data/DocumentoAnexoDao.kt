package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface DocumentoAnexoDao {
    @Query("select * from documentos_anexo")
    fun getDocumentosAnexo(): Array<DocumentoAnexo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(token: DocumentoAnexo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDocumentoAnexo(token: DocumentoAnexo)

    @Delete
    fun deleteDocumentoAnexo(token: DocumentoAnexo)

    @Query("SELECT * FROM documentos_anexo WHERE id = :id")
    fun getDocumentoAnexo(id: Long): Flowable<DocumentoAnexo>
}
