package com.lssoft2022.letsapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.lssoft2022.letsapp.databinding.ActivityRegisterBinding
import java.util.*
import kotlin.collections.HashMap

class RegisterActivity : AppCompatActivity() {

    lateinit var nickname: String
    lateinit var email: String
    lateinit var password: String
    lateinit var checkpassword: String
    var nickname_overlap:Boolean=false

    val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val firebaseFirestore = FirebaseFirestore.getInstance()

        val userRef = firebaseFirestore.collection("User")


        binding.btnNickoverlap.setOnClickListener {

            nickname = binding.etNickname.text.toString()
            if(nickname==null){
                Toast.makeText(this@RegisterActivity, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            }else{
                userRef.whereEqualTo("nickname",nickname).get().addOnSuccessListener { snapshot->
                    if(snapshot.isEmpty){
                        Toast.makeText(this@RegisterActivity, "사용가능한 닉네임 입니다", Toast.LENGTH_SHORT).show()
                        nickname_overlap=true
                    }else{
                        Toast.makeText(this@RegisterActivity, "중복된 닉네임 입니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.btnRegister.setOnClickListener {

            nickname = binding.etNickname.text.toString()
            email = binding.etId.text.toString()
            password = binding.etPw.text.toString()
            checkpassword = binding.etPwcheck.text.toString()

            if(nickname_overlap){

                if (password.equals(checkpassword)) {
                    // 이메일 중복체크
                    userRef.document(email).get().addOnSuccessListener { snapshot->
                        if(snapshot.exists()){
                            Toast.makeText(this@RegisterActivity, "이미 존재하는 이메일입니다", Toast.LENGTH_SHORT).show()
                        }else{
                            var userset:UserSet= UserSet(nickname, email, password)
                            userRef.document(email).set(userset)
                            Toast.makeText(this@RegisterActivity, "저장 완료", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }else{
                    Toast.makeText(this@RegisterActivity, "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this@RegisterActivity, "닉네임 중복체크를 해주세요", Toast.LENGTH_SHORT).show()
            }

        }
    }

}