package com.lssoft2022.letsapp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.lssoft2022.letsapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    lateinit var db_password:String

    val binding:ActivityProfileBinding by lazy { ActivityProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPreferences =getSharedPreferences("account", MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        val email:String=sharedPreferences.getString("email",null).toString()

        val firebaseFirestore=FirebaseFirestore.getInstance()
        val userRef=firebaseFirestore.collection("User")

        userRef.document(email).get().addOnSuccessListener { snapshot ->
            if(snapshot.exists()){
                binding.profileEtName.setText(snapshot.getString("nickname").toString())
                binding.profileTvEmail.text=email
                db_password=snapshot.getString("password").toString()
            }
        }

        binding.profileBtnChange.setOnClickListener {
            val newpassword:String=binding.profileEtNewPW.text.toString()
            val currentpassword:String=binding.profileEtCurrentPW.text.toString()
            if(newpassword=="" || currentpassword==""){
                Toast.makeText(this@ProfileActivity, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }else{
                if(currentpassword.equals(db_password)){
                    if(currentpassword.equals(newpassword)){
                        Toast.makeText(this@ProfileActivity, "기존과 다른 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                    }else{
                        userRef.document(email).update("password",newpassword)
                        Toast.makeText(this@ProfileActivity, "비밀번호가 변경되었습니다", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@ProfileActivity, "현재 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.tvSave.setOnClickListener {
            val newNickname:String=binding.profileEtName.text.toString()
            if(newNickname==""){
                Toast.makeText(this@ProfileActivity, "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show()
            }else{
                userRef.whereEqualTo("nickname",newNickname).get().addOnSuccessListener { snapshot->
                    if(snapshot.isEmpty){
                        Toast.makeText(this@ProfileActivity, "프로필이 변경되었습니다", Toast.LENGTH_SHORT).show()
                        userRef.document(email).update("nickname",newNickname)
                        editor.putString("nickname",newNickname)
                        editor.commit()
                    }else{
                        Toast.makeText(this@ProfileActivity, "중복된 닉네임 입니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.iv.setOnClickListener { finish() }


    }
}