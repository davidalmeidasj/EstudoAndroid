package com.example.android.projetoparceiro

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.example.android.projetoparceiro.data.*
import com.example.android.projetoparceiro.network.NetworkUtil
import com.example.android.projetoparceiro.util.DataUtil
import com.example.android.projetoparceiro.util.Helper
import kotlinx.android.synthetic.main.activity_lancamento_form.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class LancamentoFormActivity : AbstractConnect() {

    var idLancamento: Long = 0
    var lancamento: Lancamento = Lancamento()

    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lancamento_form)


        appDatabase = AppDatabase.getAppDatabase(this)

        idLancamento = intent.getLongExtra("LANCAMENTO_ID", 0)

        recoverInputsFromLancamento()

        lancDataPickerBtn.setOnClickListener {
            showDatePickerDialog()
        }

        lancHoraPickerBtn.setOnClickListener {
            showTimePickerDialog()
        }

        lancBotaoOk.setOnClickListener {
            salvarLancamento()
        }

        lancBotaoCancelar.setOnClickListener {
            cancelarLancamento()
        }
    }

    private fun recoverInputsFromLancamento() {

        if (idLancamento > 0) {
            lancamento = appDatabase.lancamentoDao().getLancamento(idLancamento)

            lancData.setText(Helper().getDataUtil().formatarData(lancamento.dataExecucao))
            lancHora.setText(Helper().getDataUtil().formatarHora(lancamento.dataExecucao))
            lancValor.setText(Helper().getNumeroUtil().formatarMoeda(lancamento.valor))

            val pessoaIdLocal = lancamento.pessoaId
            val contaIdLocal = lancamento.contaId

            if (pessoaIdLocal != null) {
                val pessoa = appDatabase.pessoaDao().getPessoaByIdLocal(pessoaIdLocal)
                lancCliente.setText(pessoa.nome)
            }

            if (contaIdLocal != null) {
                val conta = appDatabase.contaDao().getContaLocal(contaIdLocal)
                lancTipo.setText(conta.nome)
            }
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



    private fun salvarLancamento() {
        val activity = this

        val pessoaIdLocal = lancamento.pessoaId
        val contaIdLocal = lancamento.contaId
        val localIdLocal = lancamento.localId

        var pessoa = Pessoa()
        var conta = Conta()
        var local = Local()

        pessoa.criadoEm = Date()
        conta.criadoEm = Date()
        local.criadoEm = Date()

        if (lancamento.idLocal != null) {

            if (pessoaIdLocal != null) {
                pessoa = appDatabase.pessoaDao().getPessoaByIdLocal(pessoaIdLocal)
            }

            if (contaIdLocal != null) {
                conta = appDatabase.contaDao().getContaLocal(contaIdLocal)
            }

            if (localIdLocal != null) {
                local = appDatabase.localDao().getLocalLocal(localIdLocal)
            }

        } else {
            lancamento.criadoEm = Date()
        }


        pessoa.nome = lancCliente.text.toString()
        conta.nome = lancTipo.text.toString()

        lancamento.pessoa = pessoa
        lancamento.local = local
        lancamento.conta = conta

        lancamento.dataExecucao = Helper().getDataUtil().dateTimeStrToDate(lancData.text.toString() + " " + lancHora.text.toString())
        lancamento.valor = Helper().getNumeroUtil().fromString(lancValor.text.toString()).toFloat()

        inserirNovoLancamento()

        activity.setResult(Activity.RESULT_OK, intent)
        activity.finish()
    }

    private fun inserirNovoLancamento() {
        if (isOnline()) {
            val token = appDatabase.tokenDao().getToken()

            token?.let {
                val tokenString = token.token

                val response: Call<Lancamento> = NetworkUtil().getAuthenticatedRetrofit(tokenString).novoLancamento(lancamento)

                response.enqueue(object : Callback<Lancamento> {
                    override fun onFailure(call: Call<Lancamento>?, t: Throwable?) {
                        showSnackBarMessage("Servidor temporariamente indiponível.")
                    }

                    override fun onResponse(call: Call<Lancamento>?, response: Response<Lancamento>) {
                        val responseCode = response.code()
                        when (responseCode) {
                            200 -> {
                                val lancamentoInserido = response.body()

                                if (lancamentoInserido != null) {
                                    inserirLancamentoLocal(lancamentoInserido)
                                }


                                showSnackBarMessage("Lancamento cadastrado. #$responseCode")
                            }
                            else -> {
                                showSnackBarMessage("Conexão com a rede foi perdida #$responseCode.")
                            }
                        }
                    }

                })
            }
        } else {
            inserirLancamentoLocal(lancamento)
        }
    }

    private fun inserirLancamentoLocal(lancamento: Lancamento) {
        lancamento.inserirLancamentoLocal(appDatabase)
    }

    private fun showSnackBarMessage(message: String?) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun cancelarLancamento() {
        val activity = this

        activity.setResult(Activity.RESULT_CANCELED, intent)
        activity.finish()
    }
}
