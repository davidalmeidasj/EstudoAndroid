package com.example.android.projetoparceiro

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.android.projetoparceiro.data.AppDatabase
import com.example.android.projetoparceiro.data.Lancamento
import com.example.android.projetoparceiro.util.DataUtil
import kotlinx.android.synthetic.main.activity_lancamento_form.*
import java.util.*

class LancamentoFormActivity : AppCompatActivity() {

    lateinit var lancamento: Lancamento
    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lancamento_form)


        appDatabase = AppDatabase.getAppDatabase(this)

        lancamento = appDatabase.lancamentoDao().getLancamento(intent.getLongExtra("LANCAMENTO_ID", 0))

        lancDataPickerBtn.setOnClickListener {
            showDatePickerDialog()
        }

        lancHoraPickerBtn.setOnClickListener {
            showTimePickerDialog()
        }

        lancBotaoOk.setOnClickListener {
            retornarLancamento()
        }

        lancBotaoCancelar.setOnClickListener {
            cancelarLancamento()
        }
    }


    private fun showDatePickerDialog() {
        val dpFragment = DatePickerFragment()
        dpFragment.editText = lancData
        val cal = Calendar.getInstance().apply {
            time = DataUtil.dateStrToDate(lancData.text.toString())
        }
        dpFragment.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
        dpFragment.show(this.supportFragmentManager, "date_picker")
    }

    private fun showTimePickerDialog() {
        val tpFragment = TimePickerFragment()
        tpFragment.editText = lancHora
        val cal = Calendar.getInstance().apply {
            time = DataUtil.timeStrToDate(lancHora.text.toString())
        }
        tpFragment.setTime(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE))
        tpFragment.show(this.supportFragmentManager, "time_picker")
    }



    private fun retornarLancamento() {
        val activity = this

        activity.setResult(Activity.RESULT_OK, intent)
        activity.finish()
    }

    private fun cancelarLancamento() {
        val activity = this

        activity.setResult(Activity.RESULT_CANCELED, intent)
        activity.finish()
    }
}
