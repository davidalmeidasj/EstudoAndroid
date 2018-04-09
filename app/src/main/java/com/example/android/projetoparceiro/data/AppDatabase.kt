/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.projetoparceiro.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * The Room database that contains the Users table
 */
@Database(
        entities = arrayOf(
            Token::class,
            Usuario::class,
            Pessoa::class,
            Conta::class,
            DocumentoAnexo::class,
            Local::class,
            Lancamento::class
        ),
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenDao
    abstract fun lancamentoDao(): LancamentoDao
    abstract fun contaDao(): ContaDao
    abstract fun documentoAnexoDao(): DocumentoAnexoDao
    abstract fun localDao(): LocalDao
    abstract fun pessoaDao(): PessoaDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getTokenDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder<AppDatabase>(context.applicationContext,
                        AppDatabase::class.java,
                        "projeto-parceiro-final-db")
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}
