package com.example.ulpgcmuseum.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ulpgcmuseum.Adapter.EightyAdapter
import com.example.ulpgcmuseum.Item
import com.example.ulpgcmuseum.R
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EightyActivity : AppCompatActivity() , EightyAdapter.onItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var eightyArrayList : ArrayList<Item>

    private lateinit var myAdapter: EightyAdapter

    private var db = Firebase.firestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_eighty)

        recyclerView = findViewById(R.id.eightyList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        eightyArrayList = arrayListOf()



        myAdapter = EightyAdapter(eightyArrayList,this)



        recyclerView.adapter = myAdapter

        myAdapter.setOnItemClickListener(object : EightyAdapter.onItemClickListener {
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