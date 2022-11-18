package com.lssoft2022.letsapp

import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.lssoft2022.letsapp.databinding.ActivityItemSelectBinding

class ItemSelectActivity : AppCompatActivity() {

    val binding:ActivityItemSelectBinding by lazy { ActivityItemSelectBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val firebaseFirestore=FirebaseFirestore.getInstance()
        val favorRef=firebaseFirestore.collection("favor")

        val sharedPreferences=getSharedPreferences("account", MODE_PRIVATE)
        val sharedNickname=sharedPreferences.getString("nickname",null)

        val title=intent.getStringExtra("title")
        val tel=intent.getStringExtra("tel")
        val area=intent.getStringExtra("area")
        val place=intent.getStringExtra("place")
        val v_max=intent.getStringExtra("v_max")
        val v_min=intent.getStringExtra("v_min")
        val pay=intent.getStringExtra("pay")
        val x=intent.getStringExtra("x")
        val y=intent.getStringExtra("y")
        val site=intent.getStringExtra("site")
        val target=intent.getStringExtra("target")
        val category=intent.getStringExtra("category")
        val imgurl=intent.getStringExtra("imgurl")
        val count=intent.getStringExtra("count")
        var ischecked=intent.getBooleanExtra("ischecked",false)

        binding.tvTitle.text=title
        binding.tvTarget.text= "대 상 : $target"
        binding.tvTel.text= "전화번호 : $tel"
        binding.tvRegion.text= "지 역 : $area"
        binding.tvLoca.text= "장 소 : $place"
        binding.tvTime.text= "개장시간 : $v_min ~ $v_max"
        binding.tvPrice.text="예약요금 : $pay"
        binding.tvMap.text="위치보기"
        if(ischecked){
            binding.ivFavor.setImageResource(R.drawable.ic_favor_true)
        }else{
            binding.ivFavor.setImageResource(R.drawable.ic_favor_false)
        }
        binding.tvFavor.text=count

        Glide.with(this@ItemSelectActivity).load(imgurl).error(R.drawable.blank).into(binding.ivImgurl)


        binding.ivBack.setOnClickListener { finish() }
        binding.btnToEdit.setOnClickListener {
            val intent:Intent=Intent(this@ItemSelectActivity,EditActivity::class.java)
            intent.putExtra("area",area)
            intent.putExtra("place",place)
            intent.putExtra("title",title)
            intent.putExtra("category",category)
            startActivity(intent)
        }
        binding.btnToSite.setOnClickListener {
            val intent:Intent=Intent(Intent.ACTION_VIEW, Uri.parse(site))
            startActivity(intent)
        }

        binding.ivFavor.setOnClickListener {
            favorRef.document(title!!).get().addOnSuccessListener { snapshot ->
                ischecked = snapshot.contains(sharedNickname.toString())

                if(snapshot.exists()){
                    when(ischecked){
                        true->{
                            val updates = hashMapOf<String, Any>(
                                sharedNickname.toString() to FieldValue.delete()
                            )
                            var count=snapshot.getLong("count").toString().toInt()
                            var new_count=count-1

                            favorRef.document(title!!).update(updates)
                            favorRef.document(title!!).update("count",new_count)
                            binding.ivFavor.setImageResource(R.drawable.ic_favor_false)
                            binding.tvFavor.text=new_count.toString()
                        }
                        false->{
                            val updates= hashMapOf<String, Any>(
                                sharedNickname.toString() to sharedNickname.toString()
                            )

                            var count=snapshot.getLong("count").toString().toInt()
                            var new_count=count+1

                            favorRef.document(title!!).update(updates)
                            favorRef.document(title!!).update("count",new_count)
                            binding.ivFavor.setImageResource(R.drawable.ic_favor_true)
                            binding.tvFavor.text=new_count.toString()
                        }
                    }
                }else{
                    when(ischecked){
                        true->{
                            val updates = hashMapOf<String, Any>(
                                sharedNickname.toString() to FieldValue.delete()
                            )
                            var count=snapshot.getLong("count").toString().toInt()
                            var new_count=count-1

                            favorRef.document(title!!).update(updates)
                            favorRef.document(title!!).update("count",new_count)
                            binding.ivFavor.setImageResource(R.drawable.ic_favor_false)
                            binding.tvFavor.text=new_count.toString()

                        }
                        false->{
                            val updates= hashMapOf<String, Any>(
                                sharedNickname.toString() to sharedNickname.toString(),
                                "count" to 1
                            )

                            favorRef.document(title!!).set(updates)
                            binding.ivFavor.setImageResource(R.drawable.ic_favor_true)
                            binding.tvFavor.text="1"

                        }
                    }
                }
            }

        }

        }

}
