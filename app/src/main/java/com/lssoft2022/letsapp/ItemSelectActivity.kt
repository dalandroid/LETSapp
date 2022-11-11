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
import com.lssoft2022.letsapp.databinding.ActivityItemSelectBinding

class ItemSelectActivity : AppCompatActivity() {

    val binding:ActivityItemSelectBinding by lazy { ActivityItemSelectBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

        Toast.makeText(this@ItemSelectActivity, ""+category, Toast.LENGTH_SHORT).show()


        binding.tvTitle.text=title
        binding.tvTarget.text= "대 상 : $target"
        binding.tvTel.text= "전화번호 : $tel"
        binding.tvRegion.text= "지 역 : $area"
        binding.tvLoca.text= "장 소 : $place"
        binding.tvTime.text= "개장시간 : $v_min ~ $v_max"
        binding.tvPrice.text="예약요금 : $pay"
        binding.tvMap.text="위치보기"


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

        }

}
