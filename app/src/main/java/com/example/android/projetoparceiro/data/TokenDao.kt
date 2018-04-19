package com.example.android.projetoparceiro.data

import android.arch.persistence.room.*
import android.database.Cursor
import android.database.Observable
import io.reactivex.Flowable

@Dao interface TokenDao {
    @Query("select * from tokens")
    fun getToken(): Token?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToken(token: Token)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateToken(token: Token)

    @Delete
    fun deleteToken(token: Token)

    @Query("SELECT * FROM tokens WHERE id = :id")
    fun getToken(id: Long): Flowable<Token>

    @Query("SELECT * FROM tokens WHERE token = :token")
    fun getToken(token: String): Token

    @Query("DELETE FROM tokens")
    fun delete()

    /**
     * Counts the number of tokens in the table.
     *
     * @return The number of tokens.
     */
    @Query("SELECT COUNT(*) FROM " + Token.TABLE_NAME)
    fun count(): Int

    /**
     * Inserts a token into the table.
     *
     * @param token A new token.
     * @return The row ID of the newly inserted token.
     */
    @Insert
    fun insert(token: Token): Long

    /**
     * Inserts multiple tokens into the database
     *
     * @param tokens An array of new tokens.
     * @return The row IDs of the newly inserted tokens.
     */
    @Insert
    fun insertAll(tokens: Array<Token>): LongArray

    /**
     * Select all tokens.
     *
     * @return A [Cursor] of all the tokens in the table.
     */
    @Query("SELECT * FROM " + Token.TABLE_NAME)
    fun selectAll(): Cursor

    /**
     * Update the token. The token is identified by the row ID.
     *
     * @param token The token to update.
     * @return A number of tokens updated. This should always be `1`.
     */
    @Update
    fun update(token: Token): Int
}