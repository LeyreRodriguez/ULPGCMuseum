package com.example.ulpgcmuseum.Activity

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ulpgcmuseum.Activity.InteractionsActivity
import com.example.ulpgcmuseum.Activity.InventoryActivity
import com.example.ulpgcmuseum.Activity.NinetyActivity
import com.example.ulpgcmuseum.R
import com.google.android.material.navigation.NavigationView
import java.util.*

private lateinit var drawerLayout: DrawerLayout

class SettingsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_id)

        setSupportActionBar(toolbar)

        val menu = navigationView.menu
        navigationView.getHeaderView(0)
        navigationView.bringToFront()

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener (this)

        val interactions=findViewById<Button>(R.id.suggestions)
        interactions.setOnClickListener {
            val interactionsActivity = Intent(this, InteractionsActivity::class.java)
            startActivity(interactionsActivity)
        }

        // Declare the switch from the layout file
        val btn = findViewById<Switch>(R.id.darkMode)

        // set the switch to listen on checked change
        btn.setOnCheckedChangeListener { _, isChecked ->

            // if the button is checked, i.e., towards the right or enabled
            // enable dark mode, change the text to disable dark mode
            // else keep the switch text to enable dark mode
            if (btn.isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                btn.text = "Disable dark mode"
            } else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                btn.text = "Enable dark mode"
            }
        }



        //Idioma

        val idioma = findViewById<Button>(R.id.language)

        idioma.setOnClickListener{
            setLanguage();
            finish();
            startActivity(intent);
        }


    }

    private fun setLanguage() {
        val metrics = resources.displayMetrics
        val configuration = resources.configuration
        val idiomaActual = configuration.locale.language

        when(idiomaActual){
            "es" -> {
                //configura el idioma a ingles
                configuration.setLocale(Locale.forLanguageTag("en"))
            }
            "en" -> {
                //configura el idioma a español
                configuration.setLocale(Locale.forLanguageTag("es"))
            }
        }

        //actualiza el idioma, establece la configuracion
        resources.updateConfiguration(configuration, metrics)
        onConfigurationChanged(configuration)
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.inicio -> {
                val mainActivity = Intent (this, MainActivity::class.java)
                startActivity(mainActivity)
            }
            R.id.inventory -> {
                val inventoryActivity = Intent (this, InventoryActivity::class.java)
                startActivity(inventoryActivity)
            }
            R.id.qr -> {
                val qrActivity = Intent (this, QrActivity::class.java)
                startActivity(qrActivity)
            }
            R.id.comentarios -> {
                val interactions = Intent (this, InteractionsActivity::class.java)
                startActivity(interactions)
            }
            R.id.noticias -> {
                val uri : Uri = Uri.parse("https://www.ulpgc.es/");
                val intent : Intent = Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }


        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START))){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else{
            super.onBackPressed()
        }

    }


}