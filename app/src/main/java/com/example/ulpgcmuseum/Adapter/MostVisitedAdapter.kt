<<<<<<< Updated upstream
package com.example.ulpgcmuseum.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ulpgcmuseum.Activity.EightyActivity
import com.example.ulpgcmuseum.Item
import com.example.ulpgcmuseum.MainActivity
import com.example.ulpgcmuseum.R

class MostVisitedAdapter(private val itemList: ArrayList<Item>, var clickListener: MainActivity) : RecyclerView.Adapter<MostVisitedAdapter.MyViewHolder>(),
    View.OnClickListener{


    private lateinit var nListener : onItemClickListener

    interface onItemClickListener {

        fun onItemClick(item: Item, position: Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener){
        nListener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.mostvisited_item,
            parent, false)

        return MyViewHolder(itemView, nListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val item : Item = inventoryList[position]
        //holder.Name.text = item.Name

        holder.initialize(itemList.get(position), clickListener)

    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    class MyViewHolder(itemView : View, listener : onItemClickListener) : RecyclerView.ViewHolder(itemView){




        val image : ImageView = itemView.findViewById(R.id.tvImage)
        fun initialize(item: Item, action: MainActivity){

            Glide.with(image.context).load(item.Image).into(image)

            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }
        }

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}
=======
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

class MostVisitedAdapter(private val itemList: ArrayList<Item>) :
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


        holder.initialize(itemList.get(position))

        holder.itemView.setOnClickListener( View.OnClickListener() {


            var intent : Intent = Intent(holder.itemView.context, ItemActivity::class.java)
            intent.putExtra("item", item )
            holder.itemView.context.startActivity(intent)

            updateItem(item)

        })

    }
    fun updateItem(item : Item) {

        db = FirebaseFirestore.getInstance()

        db.collection("Inventory").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {

                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                }

                var contador : Int? = item.mostVisited

                contador = contador?.plus(1)
                var hashMap : Map<String, Int> = mapOf("mostVisited" to contador) as Map<String, Int>



                for (dc: DocumentChange in value?.documentChanges!!) {

                    if(dc.document.data.get("Name") == item.Name){
                        // db.collection("Inventory").document(dc.document.id).update(hashMap)

                    }
                }
            }
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


>>>>>>> Stashed changes
