package com.example.android.projetoparceiro

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import android.widget.EditText
import com.example.android.projetoparceiro.util.DataUtil
import java.util.*

class DatePickerFragment: DialogFragment(), DatePickerDialog.OnDateSetListener {
    var editText: EditText? = null
    private var ano = calendar.get(Calendar.YEAR)
    private var mes = calendar.get(Calendar.MONTH)
    private var dia = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(activity, this, ano, mes, dia)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val localEditText = editText ?: return
        val cal = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }
        val data = cal.time

        localEditText.setText(DataUtil.formatarData(data))
        localEditText.error = null
    }

    fun setDate(ano: Int, mes: Int, dia: Int) {
        this.ano = ano
        this.mes = mes
        this.dia = dia
    }

    companion object {
        private val calendar: Calendar = Calendar.getInstance()
    }
}
