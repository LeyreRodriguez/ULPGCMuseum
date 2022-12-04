package com.example.ulpgcmuseum

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ulpgcmuseum.Activity.InteractionsActivity
import com.example.ulpgcmuseum.Activity.InventoryActivity
import com.google.android.material.navigation.NavigationView


class InventoryActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)


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

        navigationView.setNavigationItemSelectedListener{
            when (it.itemId) {
                R.id.inicio -> {
                    val mainActivity = Intent (this, MainActivity::class.java)
                    startActivity(mainActivity)
                }
                R.id.inventory -> {
                    /*val inventoryActivity = Intent (this, InventoryActivity::class.java)
                    startActivity(inventoryActivity)*/
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
                R.id.ajustes -> {
                    val ajustesActivity = Intent (this, SettingsActivity::class.java)
                    startActivity(ajustesActivity)
                }

            }

            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START))){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else{
            super.onBackPressed()
        }

    }


}



