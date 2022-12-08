package com.example.ulpgcmuseum.Activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.ulpgcmuseum.*
import com.example.ulpgcmuseum.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.hdodenhof.circleimageview.CircleImageView


class ItemActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener  {
    private lateinit var item: Item
    private lateinit var name : TextView
    private lateinit var image : ImageView
    private lateinit var year : TextView
    private lateinit var description : TextView
    private lateinit var drawerLayout: DrawerLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        initViews()
        initValues()

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_id)

        setSupportActionBar(toolbar)

        //val menu = navigationView.menu
        navigationView.getHeaderView(0)
        navigationView.bringToFront()

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_open, R.string.navigation_close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener (this)
    }

    private fun initValues() {

        val bundle : Bundle? = intent.extras
        val item : Item = bundle!!.getSerializable("item") as Item


        name.setText(item.Name)
        year.setText(item.Year.toString())
        description.setText(item.Description)
        Glide.with(this).asBitmap().load(item.Image).into(image)
    }

    private fun initViews() {
        name = findViewById(R.id.tvName)
        image = findViewById(R.id.tvImage)
        year = findViewById(R.id.tvYear)
        description = findViewById(R.id.tvDescription)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.inicio -> {
                val mainActivity = Intent (this, MainActivity::class.java)
                finish()
                startActivity(mainActivity)
            }
            R.id.inventory -> {
                val inventoryActivity = Intent (this, InventoryActivity::class.java)
                finish()
                startActivity(inventoryActivity)
            }

            R.id.qr -> {
                val qrActivity = Intent (this, QrActivity::class.java)
                finish()
                startActivity(qrActivity)
            }


            R.id.comentarios -> {
                val interactions = Intent (this, InteractionsActivity::class.java)
                finish()
                startActivity(interactions)
            }
            R.id.noticias -> {
                val uri : Uri = Uri.parse("https://www.ulpgc.es/");
                val intent : Intent = Intent(Intent.ACTION_VIEW, uri);
                finish()
                startActivity(intent);
            }
            R.id.ajustes -> {
                val ajustesActivity = Intent (this, SettingsActivity::class.java)
                finish()
                startActivity(ajustesActivity)
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}

//