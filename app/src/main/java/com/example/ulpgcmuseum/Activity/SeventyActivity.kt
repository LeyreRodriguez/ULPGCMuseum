package com.example.ulpgcmuseum.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ulpgcmuseum.*
import com.example.ulpgcmuseum.Adapter.EightyAdapter
import com.example.ulpgcmuseum.Adapter.NinetyAdapter
import com.example.ulpgcmuseum.Adapter.SeventyAdapter
import com.example.ulpgcmuseum.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SeventyActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var seventyArrayList : ArrayList<Item>
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var myAdapter: SeventyAdapter

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_seventy)

        init()
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
    }

    private fun init(){
        recyclerView = findViewById(R.id.seventyList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        seventyArrayList = arrayListOf()



        myAdapter = SeventyAdapter(seventyArrayList)



        recyclerView.adapter = myAdapter



        EventChangeListener()
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


    private fun EventChangeListener(){
        val configuration = resources.configuration
        val idiomaActual = configuration.locale.language

        when(idiomaActual){
            "es" -> {

                db = FirebaseFirestore.getInstance()



                db.collection("Inventory").addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {

                        if (error != null) {
                            Log.e("Firestore Error", error.message.toString())
                        }

                        for (dc: DocumentChange in value?.documentChanges!!) {
                            for (i in 1970..1979) {
                                if (dc.document.data.get("Year").toString() == i.toString()){
                                    seventyArrayList.add(dc.document.toObject(Item::class.java))
                                }
                            }
                            myAdapter.notifyDataSetChanged()

                        }

                    }

                })
            }
            "en" -> {

                db = FirebaseFirestore.getInstance()



                db.collection("InventoryEn").addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {

                        if (error != null) {
                            Log.e("Firestore Error", error.message.toString())
                        }

                        for (dc: DocumentChange in value?.documentChanges!!) {
                            for (i in 1970..1979) {
                                if (dc.document.data.get("Year").toString() == i.toString()){
                                    seventyArrayList.add(dc.document.toObject(Item::class.java))
                                }
                            }
                            myAdapter.notifyDataSetChanged()

                        }

                    }

                })
            }
        }






    }

}