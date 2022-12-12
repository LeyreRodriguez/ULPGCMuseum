package com.example.ulpgcmuseum.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ulpgcmuseum.Activity.ItemActivity
import com.example.ulpgcmuseum.Item
import com.example.ulpgcmuseum.R
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MostVisitedAdapter(private val itemList: ArrayList<Item>, private val idDocuments: ArrayList<String>) :
    RecyclerView.Adapter<MostVisitedAdapter.MyViewHolder>(){


    private var db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.mostvisited_item,
            parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item : Item = itemList[position]
        val original : String = idDocuments[position]


        holder.initialize(itemList.get(position))


        holder.itemView.setOnClickListener( View.OnClickListener() {


            var intent : Intent = Intent(holder.itemView.context, ItemActivity::class.java)
            intent.putExtra("item", item )

            item.mostVisited = ((item.mostVisited?.plus(1)))
            Log.e("mostVisited", item.mostVisited.toString())



            val user = hashMapOf(
                "mostVisited" to item.mostVisited
            )




            db.collection("Inventory").document(idDocuments[itemList.indexOf(item)]).update(
                user as Map<String, Any>
            )

            db.collection("InventoryEn").document(idDocuments[itemList.indexOf(item)]).update(
                user as Map<String, Any>
            )

            holder.itemView.context.startActivity(intent)










           // updateItem(item)

        })

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){



        val image : ImageView = itemView.findViewById(R.id.tvImage)
        fun initialize(item: Item){

            Glide.with(image.context).load(item.Image).into(image)

        }





    }


}


