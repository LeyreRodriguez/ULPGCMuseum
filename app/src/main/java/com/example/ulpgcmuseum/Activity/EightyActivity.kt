package com.example.ulpgcmuseum.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
<<<<<<< Updated upstream
import com.example.ulpgcmuseum.Adapter.EightyAdapter
import com.example.ulpgcmuseum.Item
=======
import com.example.ulpgcmuseum.*
import com.example.ulpgcmuseum.Adapter.AgeAdapter
>>>>>>> Stashed changes
import com.example.ulpgcmuseum.R
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EightyActivity : AppCompatActivity() , EightyAdapter.onItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eightyArrayList : ArrayList<Item>

<<<<<<< Updated upstream
    private lateinit var myAdapter: EightyAdapter

=======
    private lateinit var myAdapter: AgeAdapter
    private lateinit var drawerLayout: DrawerLayout
>>>>>>> Stashed changes
    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_eighty)

        recyclerView = findViewById(R.id.eightyList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        eightyArrayList = arrayListOf()



<<<<<<< Updated upstream
        myAdapter = EightyAdapter(eightyArrayList,this)
=======
        myAdapter = AgeAdapter(eightyArrayList)
>>>>>>> Stashed changes



        recyclerView.adapter = myAdapter

<<<<<<< Updated upstream
        myAdapter.setOnItemClickListener(object : EightyAdapter.onItemClickListener {
            override fun onItemClick(item: Item, position: Int) {
                TODO("Not yet implemented")
            }
        })

        EventChangeListener()
=======


        EventChangeListener()
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
            R.id.ajustes -> {
                val ajustesActivity = Intent (this, SettingsActivity::class.java)

                startActivity(ajustesActivity)

            }

        }
>>>>>>> Stashed changes

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
                    for (i in 1980..1989) {
                        if (dc.document.data.get("Year").toString() == i.toString()){
                            eightyArrayList.add(dc.document.toObject(Item::class.java))
                        }
                    }
                    myAdapter.notifyDataSetChanged()

                }

            }

        })



    }

    override fun onItemClick(item: Item, position: Int) {
        //  Toast.makeText(this, item.Name, Toast.LENGTH_LONG).show()

        val intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("Name", item.Name)
        intent.putExtra("Year", item.Year)
        intent.putExtra("Image", item.Image)
        intent.putExtra("Description", item.Description)
        startActivity(intent)


    }
}