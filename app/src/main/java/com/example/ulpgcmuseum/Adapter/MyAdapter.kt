package com.example.ulpgcmuseum.Adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ulpgcmuseum.Activity.InventoryActivity
import com.example.ulpgcmuseum.Activity.ItemActivity
import com.example.ulpgcmuseum.Item
import com.example.ulpgcmuseum.R


class MyAdapter(private val inventoryList: ArrayList<Item>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.inventory_item,
            parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val item : Item = inventoryList[position]
        holder.Name.text = item.Name
        holder.Year.text = item.Year.toString()
        holder.initialize(inventoryList.get(position))

        holder.itemView.setOnClickListener( View.OnClickListener() {

            var intent : Intent = Intent(holder.itemView.context, ItemActivity::class.java)
            intent.putExtra("item", item )
            holder.itemView.context.startActivity(intent)

        })

    }

    override fun getItemCount(): Int {
        return inventoryList.size
    }


    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){


        val Name : TextView = itemView.findViewById(R.id.tvItem)
        val Year : TextView = itemView.findViewById(R.id.tvYear)
        val image : ImageView = itemView.findViewById(R.id.tvImage)

        fun initialize(item: Item){

            Name.text = item.Name
            Year.text = item.Year.toString()
            Glide.with(image.context).load(item.Image).into(image)

        }

    }


}


