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
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.android.projetoparceiro.util.DateConverter


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
            Lancamento::class,
            JsonData::class
        ),
        version = 2,
        exportSchema = false
)

@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tokenDao(): TokenDao
    abstract fun lancamentoDao(): LancamentoDao
    abstract fun contaDao(): ContaDao
    abstract fun documentoAnexoDao(): DocumentoAnexoDao
    abstract fun localDao(): LocalDao
    abstract fun pessoaDao(): PessoaDao
    abstract fun jsonDataDao(): JsonDataDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getTokenDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {

                INSTANCE = Room.databaseBuilder<AppDatabase>(context.applicationContext,
                        AppDatabase::class.java,
                        "profissional-projeto225-db")
                        .allowMainThreadQueries()
                        .build()
            }
            return INSTANCE as AppDatabase
        }
    }
}
