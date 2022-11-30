package com.lssoft2022.letsapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.lssoft2022.letsapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val binding:ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    lateinit var sharedPreferences:SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var userRef:CollectionReference



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseFirestore=FirebaseFirestore.getInstance()
        userRef=firebaseFirestore.collection("User")

        sharedPreferences=getSharedPreferences("account", MODE_PRIVATE)
        editor=sharedPreferences.edit()

        binding.btn1.setOnClickListener { clickEasyLogin() }
        binding.btn2.setOnClickListener { kakaologin() }
        binding.btn3.setOnClickListener { googlelogin() }

        val keyHash:String = Utility.getKeyHash(this)
        Log.d("keyHash", keyHash)

    }

    private fun googlelogin(){
        val intent:Intent=Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clickEasyLogin(){
        val intent:Intent=Intent(this@LoginActivity,EasyLoginActivity::class.java)
        startActivity(intent)
    }

    private fun kakaologin(){

        if (sharedPreferences.getBoolean("kakaologin",false)){
            val intent:Intent=Intent(this@LoginActivity,MainActivity::class.java)
            Toast.makeText(this@LoginActivity, "안녕하세요"+sharedPreferences.getString("nickname","")+"님", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()
        }else{
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if(token != null){
                    Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                    loadUserInfo() //사용자 정보 읽어오기
                }
            }

            // 디바이스에 카톡이 설치되어 있는지 확인..
            if( UserApiClient.instance.isKakaoTalkLoginAvailable(this) ){

                //카카오톡 로그인요청
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)

            }else{

                //카카오계정 로그인요청
                UserApiClient.instance.loginWithKakaoAccount(this, callback= callback)

            }
        }

    }

    fun loadUserInfo(){

        UserApiClient.instance.me { user, error ->
            if( user != null ){
                var nickname= user.kakaoAccount?.profile?.nickname
                val email= user.kakaoAccount?.email
                val imgurl=user.kakaoAccount?.profile?.profileImageUrl

                if(nickname.equals("닉네임을 등록해주세요")){
                    nickname="익명"+System.currentTimeMillis()
                }

                editor.putString("nickname",nickname)
                editor.putString("email",email)
                editor.putString("imgurl",imgurl)
                editor.putBoolean("islogin",true)
                editor.putBoolean("kakaologin",true)
                editor.commit()

                var userSet:UserSet=UserSet(nickname!!,email!!,"")
                userRef.document(email).set(userSet)

            }
        }

    }




}