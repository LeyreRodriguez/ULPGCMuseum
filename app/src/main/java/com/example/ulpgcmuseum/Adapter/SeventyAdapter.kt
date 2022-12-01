package com.example.ulpgcmuseum.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ulpgcmuseum.Activity.EightyActivity
import com.example.ulpgcmuseum.Activity.SeventyActivity
import com.example.ulpgcmuseum.Item
import com.example.ulpgcmuseum.R

class SeventyAdapter(private val seventyList: ArrayList<Item>, var clickListener: SeventyActivity) : RecyclerView.Adapter<SeventyAdapter.MyViewHolder>(),
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
            R.layout.inventory_item,
            parent, false)

        return MyViewHolder(itemView, nListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //val item : Item = inventoryList[position]
        //holder.Name.text = item.Name

        holder.initialize(seventyList.get(position), clickListener)

    }

    override fun getItemCount(): Int {
        return seventyList.size
    }


    class MyViewHolder(itemView : View, listener : onItemClickListener) : RecyclerView.ViewHolder(itemView){


        val Name : TextView = itemView.findViewById(R.id.tvItem)
        val Year : TextView = itemView.findViewById(R.id.tvYear)

        val image : ImageView = itemView.findViewById(R.id.tvImage)
        fun initialize(item: Item, action: SeventyActivity){
            Name.text = item.Name
            Year.text = item.Year.toString()
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