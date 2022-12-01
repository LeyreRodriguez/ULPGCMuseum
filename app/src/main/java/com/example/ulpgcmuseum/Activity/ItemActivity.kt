package com.example.ulpgcmuseum.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.ulpgcmuseum.R


class ItemActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        getIncomingIntent()

    }

    private fun getIncomingIntent(){

        if(intent.hasExtra("Name")){

            var texto : String? = intent.getStringExtra("Name")
            var imageUrl : String? = intent.getStringExtra("Image")
            var year : Int? = intent.getIntExtra("Year",0)
            var description : String? = intent.getStringExtra("Description")
            if (year != null) {
                setText(texto.toString(), imageUrl.toString(), year.toInt(), description.toString())
            }
        }

    }

    private fun setText(texto: String, Image: String, Year: Int, Description: String){
        val name : TextView = findViewById(R.id.tvName)
        val image : ImageView = findViewById(R.id.tvImage)
        val year : TextView = findViewById(R.id.tvYear)
        val description : TextView = findViewById(R.id.tvDescription)
        name.setText(texto)
        year.setText(Year.toString())
        description.setText(Description)


        Glide.with(this).asBitmap().load(Image).into(image)

    }
}