package com.lssoft2022.letsapp

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import com.lssoft2022.letsapp.databinding.ActivityItemSelectBinding

class ItemSelectActivity : AppCompatActivity() {

    val binding:ActivityItemSelectBinding by lazy { ActivityItemSelectBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener { finish() }
        binding.btnToEdit.setOnClickListener {
            val intent:Intent=Intent(this@ItemSelectActivity,EditActivity::class.java)
            startActivity(intent)
        }

        }

}
