package com.example.ulpgcmuseum.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ulpgcmuseum.*
import com.example.ulpgcmuseum.Adapter.MyAdapter

import com.example.ulpgcmuseum.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class InventoryActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{
    private lateinit var recyclerView: RecyclerView
    private lateinit var inventoryArrayList : ArrayList<Item>
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var tempArrayList: ArrayList<Item>
    private lateinit var myAdapter: MyAdapter

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_inventory)

        recyclerView = findViewById(R.id.inventoryList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        inventoryArrayList = arrayListOf()
        tempArrayList = arrayListOf()

        myAdapter = MyAdapter(tempArrayList)


        recyclerView.adapter = myAdapter



        EventChangeListener()

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.nav_header_search, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if(searchText.isNotEmpty()){
                    inventoryArrayList.forEach {

                        if (it.Name?.toLowerCase(Locale.getDefault())?.contains(searchText) == true){
                            tempArrayList.add(it)
                        }
                    }

                    recyclerView.adapter!!.notifyDataSetChanged()
                }else{

                    tempArrayList.clear()
                    tempArrayList.addAll(inventoryArrayList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
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

                            inventoryArrayList.add(dc.document.toObject(Item::class.java))

                        }
                        tempArrayList.addAll(inventoryArrayList)
                        myAdapter.notifyDataSetChanged()

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

                            inventoryArrayList.add(dc.document.toObject(Item::class.java))

                        }
                        tempArrayList.addAll(inventoryArrayList)
                        myAdapter.notifyDataSetChanged()

                    }
                })

            }
        }




    }





    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen((GravityCompat.START))){
            drawerLayout.closeDrawer(GravityCompat.START)
        } else{
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.inicio -> {
                val mainActivity = Intent (this, MainActivity::class.java)
                startActivity(mainActivity)
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

}
