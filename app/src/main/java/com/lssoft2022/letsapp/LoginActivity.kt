package com.lssoft2022.letsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {

    val btn1:Button by lazy { findViewById(R.id.btn1) }
    val btn2:Button by lazy { findViewById(R.id.btn2) }
    val btn3:Button by lazy { findViewById(R.id.btn3) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn1.setOnClickListener { clickButton() }
        btn2.setOnClickListener { clickButton() }
        btn3.setOnClickListener { clickButton() }


    }

    private fun clickButton(){
        val intent:Intent=Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}