package com.lssoft2022.letsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.lssoft2022.letsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var items:MutableList<Item> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

}