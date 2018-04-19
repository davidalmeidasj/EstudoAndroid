package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*

@Dao
interface DocumentoAnexoDao {
    @Query("select * from documentos_anexo")
    fun getDocumentosAnexo(): Array<DocumentoAnexo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDocumentos(documento: DocumentoAnexo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDocumentoAnexo(documento: DocumentoAnexo)

    @Delete
    fun deleteDocumentoAnexo(documento: DocumentoAnexo)

    @Query("SELECT * FROM documentos_anexo WHERE id = :id")
    fun getDocumentoAnexo(id: Long): DocumentoAnexo


    @Query("SELECT * FROM documentos_anexo WHERE id = null")
    fun getDocumentosAnexoNaoEnviados(): Array<DocumentoAnexo?>

    @Query("SELECT * FROM documentos_anexo WHERE id_local = :idLocal")
    fun getDocumentoAnexoLocal(idLocal: Long): DocumentoAnexo

    @Query("DELETE FROM documentos_anexo")
    fun delete()
}
