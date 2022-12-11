package com.example.ulpgcmuseum.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
<<<<<<< Updated upstream
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.ulpgcmuseum.Adapter.NinetyAdapter
import com.example.ulpgcmuseum.Item
=======
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ulpgcmuseum.*
import com.example.ulpgcmuseum.Adapter.AgeAdapter

>>>>>>> Stashed changes
import com.example.ulpgcmuseum.R
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class NinetyActivity : AppCompatActivity() , NinetyAdapter.onItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var ninetyArrayList : ArrayList<Item>

    private lateinit var myAdapter: AgeAdapter

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_eighty)

        recyclerView = findViewById(R.id.eightyList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        ninetyArrayList = arrayListOf()



<<<<<<< Updated upstream
        myAdapter = NinetyAdapter(ninetyArrayList,this)
=======
        myAdapter = AgeAdapter(ninetyArrayList)
>>>>>>> Stashed changes



        recyclerView.adapter = myAdapter

        myAdapter.setOnItemClickListener(object : NinetyAdapter.onItemClickListener {
            override fun onItemClick(item: Item, position: Int) {
                TODO("Not yet implemented")
            }
        })

        EventChangeListener()

    }


    private fun EventChangeListener(){

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
                    for (i in 1990..1999) {
                        if (dc.document.data.get("Year").toString() == i.toString()){
                            ninetyArrayList.add(dc.document.toObject(Item::class.java))
                        }
                    }
                    myAdapter.notifyDataSetChanged()

                }

            }

        })



    }

    override fun onItemClick(item: Item, position: Int) {
        //  Toast.makeText(this, item.Name, Toast.LENGTH_LONG).show()

<<<<<<< Updated upstream
        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("Name", item.Name)
        intent.putExtra("Year", item.Year)
        intent.putExtra("Image", item.Image)
        intent.putExtra("Description", item.Description)
        startActivity(intent)
=======
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
            R.id.ajustes -> {
                val ajustesActivity = Intent (this, SettingsActivity::class.java)

                startActivity(ajustesActivity)
            }
>>>>>>> Stashed changes


    }
}