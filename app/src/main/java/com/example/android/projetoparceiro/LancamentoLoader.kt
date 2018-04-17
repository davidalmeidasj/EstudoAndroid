package com.example.android.projetoparceiro

import android.content.AsyncTaskLoader
import android.content.Context
import android.os.Bundle

class LancamentoLoader(context: Context) : AsyncTaskLoader<Array<String>>(context) {
    override fun loadInBackground(): Array<String> {
        return arrayOf("as", "bc")
    }


}