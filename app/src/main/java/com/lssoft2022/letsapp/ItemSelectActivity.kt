package com.lssoft2022.letsapp

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class ItemSelectActivity : AppCompatActivity() {

    lateinit var back:ImageView
    lateinit var toEdit:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_select)

        back=findViewById<ImageView>(R.id.iv_back)
        back.setOnClickListener{
            finish()
        }
        toEdit=findViewById<Button>(R.id.btn_toEdit)
        toEdit.setOnClickListener{

        }

    }
}