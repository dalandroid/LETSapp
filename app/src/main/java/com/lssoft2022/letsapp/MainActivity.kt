package com.lssoft2022.letsapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kakao.util.maps.helper.Utility
import com.lssoft2022.letsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var items:MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 키 해시값을 얻어오는 기능을 가진 클래스에게 디버그용 키해시값 얻어오기
        val keyHash = Utility.getKeyHash(this)
        Log.d("TAG", keyHash)

        binding.BNV.setOnItemSelectedListener { item->
            changFramgment(
                when(item.itemId){
                    R.id.btm_list -> {
                        binding.BNV.itemIconTintList=ContextCompat.getColorStateList(this,R.color.color_bnv)
                        binding.BNV.itemTextColor=ContextCompat.getColorStateList(this, R.color.color_bnv)
                        NaviListFragment()
                    }
                    R.id.btm_map -> {
                        binding.BNV.itemIconTintList=ContextCompat.getColorStateList(this,R.color.color_bnv)
                        binding.BNV.itemTextColor=ContextCompat.getColorStateList(this, R.color.color_bnv)
                        NaviMapFragment()
                    }
                    R.id.btm_party -> {
                        binding.BNV.itemIconTintList=ContextCompat.getColorStateList(this,R.color.color_bnv)
                        binding.BNV.itemTextColor=ContextCompat.getColorStateList(this, R.color.color_bnv)
                        NaviPartyFragment()
                    }
                    R.id.btm_favor -> {
                        binding.BNV.itemIconTintList=ContextCompat.getColorStateList(this,R.color.color_bnv)
                        binding.BNV.itemTextColor=ContextCompat.getColorStateList(this, R.color.color_bnv)
                        NaviFavorFragment()
                    }
                    else -> {
                        binding.BNV.itemIconTintList=ContextCompat.getColorStateList(this,R.color.color_bnv)
                        binding.BNV.itemTextColor=ContextCompat.getColorStateList(this, R.color.color_bnv)
                        NaviToolFragment()
                    }
                }
            )
            true
        }
        binding.BNV.selectedItemId=R.id.btm_list

    }

    private fun changFramgment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fl_con,fragment).commit()
    }

    override fun onResume() {
        super.onResume()
        var fraName=intent.getStringExtra("fragment")
        if(fraName.equals("party")){
            supportFragmentManager.beginTransaction().replace(R.id.fl_con,NaviPartyFragment()).commit()
            binding.BNV.itemIconTintList=ContextCompat.getColorStateList(this,R.color.color_bnv)
            binding.BNV.itemTextColor=ContextCompat.getColorStateList(this, R.color.color_bnv)
            binding.BNV.selectedItemId=R.id.btm_party
            intent.removeExtra("fragment")
        }
    }

}