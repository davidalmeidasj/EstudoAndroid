package com.example.android.projetoparceiro

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.EditText
import android.widget.TimePicker
import com.example.android.projetoparceiro.util.DataUtil
import java.util.*

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    var editText: EditText? = null
    private var hora: Int
    private var minuto: Int

    init {
        val calendar: Calendar = Calendar.getInstance()
        hora = calendar.get(Calendar.HOUR_OF_DAY)
        minuto = calendar.get(Calendar.MINUTE)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return TimePickerDialog(activity, this, hora, minuto,
                DateFormat.is24HourFormat(activity))
    }

    fun setTime(hora: Int, minuto: Int) {
        this.hora = hora
        this.minuto = minuto
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val localEditText = editText ?: return
        val cal = Calendar.getInstance().apply {
            val dia = get(Calendar.DAY_OF_MONTH)
            val mes = get(Calendar.MONTH)
            val ano = get(Calendar.YEAR)
            set(ano, mes, dia, hourOfDay, minute)
        }
        val data = cal.time

        localEditText.setText(DataUtil.formatarHora(data))
        localEditText.error = null
    }
}
