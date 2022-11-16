package com.lssoft2022.letsapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.lssoft2022.letsapp.databinding.ActivityEasyLoginBinding

class EasyLoginActivity : AppCompatActivity() {

    val binding by lazy { ActivityEasyLoginBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val firebaseFirestore = FirebaseFirestore.getInstance()

        val userRef = firebaseFirestore.collection("User")

        binding.btnLogin.setOnClickListener {

            var email: String = binding.etId.text.toString()
            var password: String = binding.etPw.text.toString()

            userRef.document(email).get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    var pw = snapshot.get("password").toString()
                    if (pw.equals(password)) {

                        var pref:SharedPreferences=getSharedPreferences("account", MODE_PRIVATE)
                        var editor:SharedPreferences.Editor=pref.edit()

                        var nickname=snapshot.get("nickname").toString()

                        editor.putBoolean("islogin",true)
                        editor.putString("email",email)
                        editor.putString("nickname",nickname)
                        editor.commit()

                        Toast.makeText(this@EasyLoginActivity, "안녕하세요. "+nickname+"님", Toast.LENGTH_SHORT).show()
                        val intent: Intent = Intent(this@EasyLoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@EasyLoginActivity, "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(this@EasyLoginActivity, "존재하지않는 계정입니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        binding.tvRegister.setOnClickListener {
            val intent: Intent = Intent(this@EasyLoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

}