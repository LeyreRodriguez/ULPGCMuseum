package com.example.ulpgcmuseum.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ulpgcmuseum.Adapter.MostVisitedAdapter
import com.example.ulpgcmuseum.Item
import com.example.ulpgcmuseum.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var itemList:ArrayList<Item>

    private lateinit var myAdapter: MostVisitedAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var idDocument : ArrayList<String>

    private var db = Firebase.firestore

    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        init()

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_id)

        setSupportActionBar(toolbar)

        val menu = navigationView.menu
        navigationView.getHeaderView(0)
        navigationView.bringToFront()

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.navigation_open,
            R.string.navigation_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener (this)


        /*Nueva modificacion*/

        val seventy = findViewById<Button>(R.id.setenta)
        seventy.setOnClickListener {
            val seventyActivity = Intent(this, SeventyActivity::class.java)

            startActivity(seventyActivity)

        }


        val eighty=findViewById<Button>(R.id.ochenta)
        eighty.setOnClickListener {
            val eightyActivity = Intent(this, EightyActivity::class.java)

            startActivity(eightyActivity)

        }

        val ninety=findViewById<Button>(R.id.noventa)
        ninety.setOnClickListener {
            val ninetyActivity = Intent(this, NinetyActivity::class.java)

            startActivity(ninetyActivity)

        }

        val qrButton = findViewById<ImageView>(R.id.qrButton)
        qrButton.setOnClickListener {
            val qrActivity = Intent (this, QrActivity::class.java)

            startActivity(qrActivity)

        }

    }



    private fun init(){
        recyclerView = findViewById(R.id.inventoryList)
        recyclerView.setHasFixedSize(true)


        recyclerView.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        recyclerView.setHasFixedSize(true)

        itemList = arrayListOf()
        idDocument = arrayListOf()
        myAdapter = MostVisitedAdapter(itemList,idDocument)
        recyclerView.adapter = myAdapter

        EventChangeListener()

    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            /*R.id.inicio -> {
                val mainActivity = Intent (this, MainActivity::class.java)
                startActivity(mainActivity)
            }*/
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
            R.id.ajustes -> {
                val ajustesActivity = Intent (this, SettingsActivity::class.java)

                startActivity(ajustesActivity)

            }

        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        Log.e("Le das", "pa tras")
        if(drawerLayout.isDrawerOpen((GravityCompat.START))){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else{
            super.onBackPressed()
        }

    }

    private fun EventChangeListener(){


        val configuration = resources.configuration
        val idiomaActual = configuration.locale.language

        when(idiomaActual){
            "es" -> {

                db = FirebaseFirestore.getInstance()

                db.collection("Inventory").orderBy("mostVisited", Query.Direction.DESCENDING).
                addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {

                        if (error != null) {
                            Log.e("Firestore Error", error.message.toString())
                        }

                        for (dc: DocumentChange in value?.documentChanges!!) {
                            itemList.add(dc.document.toObject(Item::class.java))
                            idDocument.add(dc.document.id)

                        }


                        myAdapter.notifyDataSetChanged()

                    }

                })
            }
            "en" -> {

                db = FirebaseFirestore.getInstance()

                db.collection("InventoryEn").orderBy("mostVisited", Query.Direction.DESCENDING).
                addSnapshotListener(object : EventListener<QuerySnapshot> {
                    override fun onEvent(
                        value: QuerySnapshot?,
                        error: FirebaseFirestoreException?
                    ) {

                        if (error != null) {
                            Log.e("Firestore Error", error.message.toString())
                        }

                        for (dc: DocumentChange in value?.documentChanges!!) {
                            itemList.add(dc.document.toObject(Item::class.java))
                            idDocument.add(dc.document.id)
                        }



                        myAdapter.notifyDataSetChanged()

                    }

                })
            }
        }









    }






}