package com.example.ulpgcmuseum.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
<<<<<<< Updated upstream
=======
import android.view.MenuItem
>>>>>>> Stashed changes
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.ulpgcmuseum.R
<<<<<<< Updated upstream
=======
import com.google.android.material.navigation.NavigationView
>>>>>>> Stashed changes


class ItemActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        getIncomingIntent()

    }

<<<<<<< Updated upstream
    private fun getIncomingIntent(){
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
>>>>>>> Stashed changes

        if(intent.hasExtra("Name")){

<<<<<<< Updated upstream
            var texto : String? = intent.getStringExtra("Name")
            var imageUrl : String? = intent.getStringExtra("Image")
            var year : Int? = intent.getIntExtra("Year",0)
            var description : String? = intent.getStringExtra("Description")
            if (year != null) {
                setText(texto.toString(), imageUrl.toString(), year.toInt(), description.toString())
=======
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
>>>>>>> Stashed changes
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