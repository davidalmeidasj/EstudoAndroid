package com.example.android.projetoparceiro

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.LoaderManager.LoaderCallbacks
import android.support.v4.content.AsyncTaskLoader
import android.support.v4.content.Loader
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.android.projetoparceiro.adapter.LancamentoAdapter
import com.example.android.projetoparceiro.data.AppDatabase
import com.example.android.projetoparceiro.data.JsonData
import com.example.android.projetoparceiro.data.Lancamento
import com.example.android.projetoparceiro.data.Usuario
import com.example.android.projetoparceiro.model.User
import com.example.android.projetoparceiro.network.NetworkUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : AbstractConnect(), NavigationView.OnNavigationItemSelectedListener {

    var tokenString: String = ""

    private lateinit var appDatabase: AppDatabase

    lateinit var listView: ListView

    private val FORECAST_LOADER_ID = 0

    private lateinit var adapter: LancamentoAdapter

    lateinit var mLoadingIndicator: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appDatabase = AppDatabase.getAppDatabase(this)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        R.id.nav_logout

        mLoadingIndicator = pb_loading_indicator

        mLoadingIndicator.visibility = View.VISIBLE
    }


    fun logout() {
        //clearDataBase()

        val mainActivity = Intent(applicationContext, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(mainActivity)

        finish()
    }

    private fun clearDataBase() {
        appDatabase.jsonDataDao().delete()
        appDatabase.usuarioDao().delete()
        appDatabase.documentoAnexoDao().delete()
        appDatabase.lancamentoDao().delete()
        appDatabase.contaDao().delete()
        appDatabase.localDao().delete()
        appDatabase.pessoaDao().delete()
    }

    override fun onStart() {
        super.onStart()

        var token = appDatabase.tokenDao().getToken()

        if (token != null) {
            tokenString = token.token

            defineBottomNavigationView()

            supportLoaderManager.initLoader(FORECAST_LOADER_ID, null, mLoaderCallback)
        } else {
            logout()
        }

    }

    private val mLoaderCallback = object: LoaderCallbacks<Array<String>> {

        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Array<String>> {
            return object: AsyncTaskLoader<Array<String>>(this@MainActivity) {

                override fun loadInBackground(): Array<String>? {
                    return arrayOf("", "")
                }

                /**
                 * Subclasses of AsyncTaskLoader must implement this to take care of loading their data.
                 */
                override fun onStartLoading() {

                    setUserPerfil()
                }
            }
        }

        override fun onLoadFinished(loader: Loader<Array<String>>, data: Array<String>?) {
            //atualizar banco
        }

        override fun onLoaderReset(loader: Loader<Array<String>>) {

        }
    }

    private fun popularBancoPrimeiraVez() {

        val jsondata = this@MainActivity.appDatabase.jsonDataDao().getJsonData()

        var jsonDatData = "[]"

        if (jsondata.isNotEmpty()) {
            jsonDatData = jsondata[0].jsonData
        }

        val lancamentos = Gson().fromJson<Array<Lancamento>>(jsonDatData, Array<Lancamento>::class.java)

        lancamentos.forEach { itLancamento ->

            this.appDatabase.contaDao().insertContas(itLancamento.conta)

            val conta = this.appDatabase.contaDao().getConta(itLancamento.conta.id)
            itLancamento.contaId = conta.idLocal

            itLancamento.local?.let {

                this.appDatabase.localDao().insertLocal(it)
                val local = this.appDatabase.localDao().getLocal(it.id)
                itLancamento.localId = local.idLocal
            }

            itLancamento.pessoa?.let {
                this.appDatabase.pessoaDao().insertPessoas(it)
                val pessoa = this.appDatabase.pessoaDao().getPessoa(it.id)
                itLancamento.pessoaId = pessoa.idLocal
            }

            this.appDatabase.lancamentoDao().insertLancamentos(itLancamento)

            val lancamento = this.appDatabase.lancamentoDao().getLancamento(itLancamento.id)

            itLancamento.documentos?.forEach {
                it.lancamentoId = lancamento.idLocal
                this.appDatabase.documentoAnexoDao().insertDocumentos(it)
            }
        }
    }

    private fun mergeDataBase() {
        val lancamentosDb = appDatabase.lancamentoDao().getLancamentos()

        //verifica se está online e se o banco de lançamento esta vazio para popular pela primeira vez
        if (isOnline() && lancamentosDb.isEmpty()) {

            val response: Call<Array<Lancamento>> = NetworkUtil().getAuthenticatedRetrofit(tokenString).getList()

            response.enqueue(object : Callback<Array<Lancamento>> {
                override fun onFailure(call: Call<Array<Lancamento>>?, t: Throwable?) {

                }

                override fun onResponse(call: Call<Array<Lancamento>>?, response: Response<Array<Lancamento>>?) {
                    val responseCode = response?.code()

                    when (responseCode) {
                        200 -> {
                            //recupera todos os lancamentos
                            val lancamentosStringJson = Gson().toJson(response.body())

                            //guarda os lancamentos em array de lancamentos
                            val lancamentos: Array<Lancamento>? = response.body()

                            //json Dao
                            val jsonDataDao = appDatabase.jsonDataDao()

                            // Verificar se o json é diferente ou vaziol
                            val jsonObject: Array<JsonData> = jsonDataDao.getJsonData()

                            if (jsonObject.isEmpty() || (jsonObject.size > 1 && jsonObject[0].jsonData == lancamentosStringJson)) {
                                //Popular banco local

                                //delete todos se existir algum
                                jsonObject.forEach {
                                    jsonDataDao.deleteJsonData(it)
                                }

                                // add o mais recente
                                jsonDataDao.insertJsonData(JsonData(null, lancamentosStringJson))

                            }

                            preencherListViewLancamentos(lancamentos)

                            popularBancoPrimeiraVez()
                        }
                        401 -> {

                            if (isOnline()) {
                                logout()
                            } else {
                                showSnackBarMessage("Não há conexão com a internet.")
                            }
                        }
                        else -> showSnackBarMessage("Tente novamente mais tarde")
                    }
                }

            })
        } else if (isOnline()) {

            Log.d("LANCAMENTOSLANCAMENTOS", "Verificar quais não foram sincronizados e enviar e verificar a edição")

            preencherListViewLancamentos(lancamentosDb)

        } else {
            preencherListViewLancamentos(lancamentosDb)
        }
    }

    private fun preencherListViewLancamentos(lancamentos: Array<Lancamento>?) {
        val arrayListLancamento = ArrayList<Lancamento>()

        lancamentos?.forEach {
            arrayListLancamento.add(it)
        }

        setListView()

        if (lancamentos != null) {
            setLancamentoListViewHome(arrayListLancamento)
        }

        mLoadingIndicator.visibility = View.INVISIBLE
    }


    private fun setListView() {
        listView = lv_list
    }

    private fun setLancamentoListViewHome(lancamentos: ArrayList<Lancamento>) {
        val paramsListView: LinearLayout.LayoutParams = listView.layoutParams as LinearLayout.LayoutParams

        paramsListView.setMargins(
                paramsListView.leftMargin,
                paramsListView.topMargin,
                paramsListView.rightMargin,
                125
        )

        listView.layoutParams = paramsListView

        adapter = LancamentoAdapter(lancamentos, applicationContext)

        listView.adapter = adapter

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val dataModel = lancamentos[position]

            val formLancamentoActivity = Intent(applicationContext, LancamentoFormActivity::class.java)

            val idLancamento = dataModel.id

            formLancamentoActivity.putExtra("LANCAMENTO_ID", idLancamento)

            startActivity(formLancamentoActivity)
        }
    }

    private fun setUserPerfil() {
        val response: Call<User> = NetworkUtil().getAuthenticatedRetrofit(tokenString).getProfile()

        response.enqueue(object : Callback<User> {
            override fun onFailure(call: Call<User>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                val responseCode = response?.code()

                when (responseCode) {
                    200 -> {

                        val user: User? = response.body()

                        val name = user?.firstName + " " + user?.lastName
                        val email = user?.email

                        tv_email_usuario.text = email
                        tv_nome_usuario.text = name

                        var usuario = appDatabase.usuarioDao().getUsuario()
                        if (usuario?.email != user?.email) {

                            clearDataBase()

                            var novoUsuario = Usuario(null, UUID.randomUUID().toString(), user?.firstName + " " + user?.lastName, user?.email, null, null, null, null, null, null)

                            appDatabase.usuarioDao().insertUsuario(novoUsuario)
                        }
                    }

                    401 -> logout()
                    else -> showSnackBarMessage("Tente novamente mais tarde")
                }

                mergeDataBase()
            }

        })
    }


    private fun showSnackBarMessage(message: String?) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
            R.id.nav_logout -> {
                logout()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun defineBottomNavigationView() {
        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //setUsuarioListViewHome()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                //defineListViewDashboard()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                //defineListViewNotification()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}
