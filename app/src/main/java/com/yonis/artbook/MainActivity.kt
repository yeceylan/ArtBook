package com.yonis.artbook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.yonis.artbook.databinding.ActivityDetailBinding
import com.yonis.artbook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var artList: ArrayList<art>
    private lateinit var artAdapter:ArtAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        artList = ArrayList()


        artAdapter= ArtAdapter(artList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter=artAdapter

        try{
            val database = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null)

            val cursor = database.rawQuery("SELECT * FROM arts",null)
            val artNameIX = cursor.getColumnIndex("artname")
            val idIX = cursor.getColumnIndex("id")

            while(cursor.moveToNext()){
                println("asdfd")
                val name =cursor.getString(artNameIX)
                val id = cursor.getInt(idIX)
                val art =art(name,id)
                artList.add(art)

                artAdapter.notifyDataSetChanged()

            }
            cursor.close()

        }catch (e: Exception){

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //inflater
        val menuInflater = getMenuInflater()
        menuInflater.inflate(R.menu.art_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.addItem){
             val intent =Intent(this@MainActivity,DetailActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}