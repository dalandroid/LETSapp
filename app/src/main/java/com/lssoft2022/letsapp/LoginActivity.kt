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
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class LoginActivity : AppCompatActivity() {

    val btn1:Button by lazy { findViewById(R.id.btn1) }
    val btn2:ImageView by lazy { findViewById(R.id.btn2) }
    val btn3:Button by lazy { findViewById(R.id.btn3) }

    lateinit var sharedPreferences:SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var userRef:CollectionReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseFirestore=FirebaseFirestore.getInstance()
        userRef=firebaseFirestore.collection("User")

        sharedPreferences=getSharedPreferences("account", MODE_PRIVATE)
        editor=sharedPreferences.edit()

        btn1.setOnClickListener { clickEasyLogin() }
        btn2.setOnClickListener { clickedLogin() }
        btn3.setOnClickListener { clickButton() }


    }

    private fun clickButton(){
        val intent:Intent=Intent(this@LoginActivity,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun clickEasyLogin(){
        val intent:Intent=Intent(this@LoginActivity,EasyLoginActivity::class.java)
        startActivity(intent)
    }

    private fun clickedLogin(){
        // 권장 : 카카오톡 로그인을 먼저 시도하고 불가능할 경우 카카오계정 로그인을 시도

        // 로그인 요청 결과에 반응하는 callback 함수

        if(sharedPreferences.getBoolean("islogin",false)){
            val intent: Intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            if(sharedPreferences.getBoolean("kakaofirst",true)){
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if(token != null){
                        loadUserInfo()
                        editor.putBoolean("kakaofirst",false)
                        editor.commit()
                    }
                }

                // 디바이스에 카톡이 설치되어있는지 확인
                if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){

                    // 카카오톡 로그인 요청
                    UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)

                }else{

                    // 카카오계정 로그인요청
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                }
            }else{
                val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                    if(token != null){
                        UserApiClient.instance.me{user,error ->
                            if(user !=null){
                                val email=user.kakaoAccount?.email
                                val imgurl=user.kakaoAccount?.profile?.profileImageUrl
                                var nickname:String="익명"+System.currentTimeMillis()

                                userRef.document(email!!).get().addOnSuccessListener { snapshot->
                                    nickname=snapshot.get("nickname").toString()
                                    editor.putBoolean("islogin",true)
                                    editor.putString("nickname",nickname)
                                    editor.putString("email",email)
                                    editor.putString("imgurl",imgurl)
                                    editor.commit()


                                    Toast.makeText(this@LoginActivity, "안녕하세요. "+nickname+"님", Toast.LENGTH_SHORT).show()
                                }

                            }
                        }
                    }
                }

                // 디바이스에 카톡이 설치되어있는지 확인
                if(UserApiClient.instance.isKakaoTalkLoginAvailable(this)){

                    // 카카오톡 로그인 요청
                    UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)

                }else{

                    // 카카오계정 로그인요청
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                }
            }

        }



    }
    fun loadUserInfo(){
        UserApiClient.instance.me { user, error ->
            if(user !=null){

                val nickname=user.kakaoAccount?.profile?.nickname
                val email=user.kakaoAccount?.email
                val imgurl=user.kakaoAccount?.profile?.profileImageUrl

                editor.putBoolean("islogin",true)
                editor.putString("nickname",nickname)
                editor.putString("email",email)
                editor.putString("imgurl",imgurl)
                editor.commit()

                var userset:UserSet= UserSet(nickname!!, email!!, " ")
                userRef.document(email).set(userset)

                Toast.makeText(this@LoginActivity, "안녕하세요. "+nickname+"님", Toast.LENGTH_SHORT).show()
            }
        }
    }

}