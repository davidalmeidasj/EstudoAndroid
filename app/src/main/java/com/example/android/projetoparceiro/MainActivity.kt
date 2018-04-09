package com.example.android.projetoparceiro

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.android.projetoparceiro.data.AppDatabase
import com.example.android.projetoparceiro.model.User
import com.example.android.projetoparceiro.network.NetworkUtil
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var tokenString: String = ""

    private lateinit var appDatabase: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }


    fun logout() {

        val mainActivity = Intent(applicationContext, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(mainActivity)

        var tokenString = appDatabase.tokenDao().getToken()

        if (tokenString != null)
            appDatabase.tokenDao().deleteToken(tokenString)

        finish()
    }

    override fun onStart() {
        super.onStart()

        appDatabase = AppDatabase.getTokenDatabase(this)

        var token = appDatabase.tokenDao().getToken()

        if (token != null) {
            tokenString = token.token

            setUserPerfil()
        } else {
            logout()
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

                        val name = user?.name
                        val email = user?.email

                        tv_email_usuario.text = email
                        tv_nome_usuario.text = name
                    }
                    401 -> logout()
                    else -> showSnackBarMessage("Tente novamente mais tarde")
                }
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
}
