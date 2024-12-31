package com.example.sw2024bgr1_edbc

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ContextMenu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BListView : AppCompatActivity() {

    var data = BMemoryDataBase.trainersArray
    var posicionItemSeleccionado = -1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_blist_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val listView = findViewById<ListView>(R.id.lv_listView)
        val adaptor = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            data
        )
        listView.adapter = adaptor
        adaptor.notifyDataSetChanged()
        val btnAddListView = findViewById<Button>(R.id.btn_list_view)
        btnAddListView.setOnClickListener{
            addTrainer(adaptor)
        }
        registerForContextMenu(listView)

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
//        call menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
//        get id
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        var position = info.position
        posicionItemSeleccionado = position
    }

    fun addTrainer(adaptor: ArrayAdapter<BEntrenador>){
        data.add(BEntrenador(4, "Wendy", "d342@.com"))
    }
}