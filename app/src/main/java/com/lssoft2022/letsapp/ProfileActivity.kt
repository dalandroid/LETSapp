package com.lssoft2022.letsapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.google.firebase.firestore.FirebaseFirestore
import com.lssoft2022.letsapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    lateinit var db_password:String

    val binding:ActivityProfileBinding by lazy { ActivityProfileBinding.inflate(layoutInflater) }
    lateinit var uri:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val sharedPreferences =getSharedPreferences("account", MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        val email:String=sharedPreferences.getString("email",null).toString()

        val firebaseFirestore=FirebaseFirestore.getInstance()
        val userRef=firebaseFirestore.collection("User")

        uri= sharedPreferences.getString("imgurl",null).toString()

        userRef.document(email).get().addOnSuccessListener { snapshot ->
            if(snapshot.exists()){
                binding.profileEtName.setText(snapshot.getString("nickname").toString())
                binding.profileTvEmail.text=email
                Glide.with(this@ProfileActivity).load(sharedPreferences.getString("imgurl",R.drawable.profile.toString())).error(R.drawable.profile).into(binding.profileCiv)
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
                editor.putString("imgurl",uri)
                editor.commit()
            }else{
                userRef.whereEqualTo("nickname",newNickname).get().addOnSuccessListener { snapshot->
                    if(snapshot.isEmpty){
                        Toast.makeText(this@ProfileActivity, "프로필이 변경되었습니다", Toast.LENGTH_SHORT).show()
                        userRef.document(email).update("nickname",newNickname)
                        editor.putString("nickname",newNickname)
                        editor.putString("imgurl",uri)
                        editor.commit()
                    }else{
                        Toast.makeText(this@ProfileActivity, "중복된 닉네임 입니다", Toast.LENGTH_SHORT).show()
                        editor.putString("imgurl",uri)
                        editor.commit()
                    }
                }
            }
        }

        binding.iv.setOnClickListener { finish() }

        binding.profileCiv.setOnClickListener {
            val intent:Intent=Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            resultLauncher.launch(intent)
        }


    }
    var resultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // 갤러리 앱을 실행한 사용자가 사진을 선택하지 않고 돌아왔을 수도 있어서 확인
        if (result.resultCode != RESULT_CANCELED) {
            // 결과를 가지고 돌아온 Intent 객체를 소환
            val intent = result.data
            // Intent에게 선택한 사진경로 Uri 데이터받기
            uri = intent!!.data.toString()
            // 이미지 load Library사용
            Glide.with(this@ProfileActivity).load(uri).into(binding.profileCiv)
        }
    }

}