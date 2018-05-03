package com.example.android.projetoparceiro

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.android.projetoparceiro.model.ErrorResponse
import com.example.android.projetoparceiro.model.User
import com.example.android.projetoparceiro.network.NetworkUtil
import kotlinx.android.synthetic.main.activity_registrar.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson

class Registrar : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        tv_fazer_login.setOnClickListener { closeRegistrar() }
        bt_cadastrar.setOnClickListener { attempRegister() }
        et_password.addTextChangedListener(textChangeListener(ti_senha))
        et_confirmar_senha.addTextChangedListener(textChangeListener(ti_confirmar_senha))
    }

    private fun textChangeListener(editText: TextInputLayout): TextWatcher? {
        return object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editText.isPasswordVisibilityToggleEnabled = true
            }

        }
    }

    private fun showSnackBarMessage(message: String?) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun attempRegister() {

        // Reset errors.
        et_nome.error = null
        et_email.error = null
        et_password.error = null
        et_confirmar_senha.error = null

        // Store values at the time of the login attempt.

        var cancel = false
        var focusView: View? = null

        val nomeString = et_nome.text.toString()
        val emailString = et_email.text.toString()
        val senhaString = et_password.text.toString()
        val confirmarSenhaString = et_confirmar_senha.text.toString()

        //showSnackBarMessage(senhaString)

        if (TextUtils.isEmpty(confirmarSenhaString)){
            cancel = true
            focusView = et_confirmar_senha
            ti_confirmar_senha.isPasswordVisibilityToggleEnabled = false
            et_confirmar_senha.error = getString(R.string.error_field_required)
        }

        if (TextUtils.isEmpty(senhaString)){
            cancel = true
            focusView = et_password
            ti_senha.isPasswordVisibilityToggleEnabled = false
            et_password.error = getString(R.string.error_field_required)
        }

        if (TextUtils.isEmpty(emailString)){
            et_email.error = getString(R.string.error_field_required)
            cancel = true
            focusView = et_email
        }

        if (TextUtils.isEmpty(nomeString)){
            et_nome.error = getString(R.string.error_field_required)
            cancel = true
            focusView = et_nome
        }

        if (!TextUtils.isEmpty(confirmarSenhaString) && !TextUtils.isEmpty(senhaString) && senhaString != confirmarSenhaString) {
            cancel = true
            focusView = et_confirmar_senha
            ti_confirmar_senha.isPasswordVisibilityToggleEnabled = false
            et_confirmar_senha.error = getString(R.string.error_not_equals_password)
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true)

            val user = User()
            user.firstName = nomeString
            user.password  = senhaString
            user.email = emailString

            doRegister(user)
        }
    }

    private fun doRegister(user: User) {

        val response: Call<Void> = NetworkUtil().getRetrofit().register(user)
        response.enqueue(object: Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                showProgress(false)
                //Nullpointer
                showSnackBarMessage("Erro de conexão, tente novamente.")
                call?.cancel()
                showProgress(false)
            }

            override fun onResponse(call: Call<Void>?, response: retrofit2.Response<Void>?) {
                val responseCode = response?.code()


                if (responseCode == 200) {
                    showSnackBarMessage("Cadastro realizado com sucesso!")
                    finish()
                } else {

                    try {
                        val jObjError = JSONObject(response?.errorBody()?.string())
                        val jsonString: String = jObjError.get("messages").toString()

                        val gson = Gson()

                        val collectionType = object : TypeToken<List<ErrorResponse>>() {

                        }.type

                        val listError: List<ErrorResponse> = gson.fromJson(jsonString, collectionType)

                        listError.forEach {

                            setError(it.fieldName, it.errors)

                        }

                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                    }
                }
                showProgress(false)
            }

            private fun setError(fieldName: String?, errors: List<String>?) {
                when (fieldName) {
                    "password" ->
                        setErrorPassword(errors)
                    "email" ->
                        setErrorEmail(errors)
                    "name" ->
                        setErrorNome(errors)
                    else ->
                        println("Este não é um dia válido!")
                }
            }

            private fun setErrorEmail(errors: List<String>?) {
                et_email.error = errors?.joinToString(" \n ")

                et_email.requestFocus()
            }

            private fun setErrorNome(errors: List<String>?) {
                et_nome.error = errors?.joinToString(" \n ")

                et_nome.requestFocus()
            }

            private fun setErrorPassword(errors: List<String>?) {
                et_password.error = errors?.joinToString(" \n ")

                ti_senha.isPasswordVisibilityToggleEnabled = false
                et_password.error = getString(R.string.error_field_required)

                et_password.requestFocus()
            }

        })
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private fun showProgress(show: Boolean) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            sv_form.visibility = if (show) View.GONE else View.VISIBLE
            sv_form.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 0 else 1).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            sv_form.visibility = if (show) View.GONE else View.VISIBLE
                        }
                    })

            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            login_progress.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 1 else 0).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            login_progress.visibility = if (show) View.VISIBLE else View.GONE
                        }
                    })
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            login_progress.visibility = if (show) View.VISIBLE else View.GONE
            sv_form.visibility = if (show) View.GONE else View.VISIBLE
        }
    }

    private fun closeRegistrar() {
        finish()
    }
}
