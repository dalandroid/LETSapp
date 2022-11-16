package com.lssoft2022.letsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        var pref=getSharedPreferences("account", MODE_PRIVATE)
        val islogin:Boolean=pref.getBoolean("islogin",false)
        val nickname: String? =pref.getString("nickname",null)

        var handler = Handler()
        handler.postDelayed( {
            if(islogin){
                Toast.makeText(this@IntroActivity, "안녕하세요. "+nickname+"님", Toast.LENGTH_SHORT).show()
                var intent = Intent( this, MainActivity::class.java)
                startActivity(intent)
            }else{
                Toast.makeText(this@IntroActivity, "저장안됨", Toast.LENGTH_SHORT).show()
                var intent = Intent( this, LoginActivity::class.java)
                startActivity(intent)
            }

        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}