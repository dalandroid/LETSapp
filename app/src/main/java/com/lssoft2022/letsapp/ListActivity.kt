package com.lssoft2022.letsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import com.lssoft2022.letsapp.databinding.ActivityListBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Field

class ListActivity : AppCompatActivity() {

    var category:Int = 0
    lateinit var binding:ActivityListBinding
    var categoryTitle:Array<String> = arrayOf("축구장", "풋살장", "족구장", "야구장", "테니스장", "농구장", "배구장", "경기장","운동장","체육관",
        "배드민턴장","탁구장","교육시설","수영장","골프장")

    var items:MutableList<ItemAPI> = ArrayList()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent:Intent = intent
        category = intent.getIntExtra("category", 0)
        binding.ivToolbar.setOnClickListener{finish()}

        binding.tvToolbar.text=categoryTitle[category]

        searchItem()

        binding.recyclerView.adapter=APIAdapter(this,items)



        // -----------------------------------spinner 1-----------------------------------------
        binding.spinner1.adapter= ArrayAdapter.createFromResource(this,R.array.gu,R.layout.spinner_selected)
            .also { adapter -> adapter.setDropDownViewResource(R.layout.spinner_dropdown)
                binding.spinner1.adapter=adapter}

        val popup: Field = AppCompatSpinner::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val popupWindow = popup[binding.spinner1] as
                androidx.appcompat.widget.ListPopupWindow
        popupWindow.height = 700

        binding.spinner1.dropDownVerticalOffset=100

        binding.spinner1.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        // -----------------------------------spinner 1-----------------------------------------


        // -----------------------------------spinner 2-----------------------------------------
        binding.spinner2.adapter= ArrayAdapter.createFromResource(this,R.array.state,R.layout.spinner_selected)
            .also { adapter -> adapter.setDropDownViewResource(R.layout.spinner_dropdown)
                binding.spinner2.adapter=adapter}


        val popupWindow2 = popup[binding.spinner2] as
                androidx.appcompat.widget.ListPopupWindow
        popupWindow2.height = 700

        binding.spinner2.dropDownVerticalOffset=100

        binding.spinner2.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        // -----------------------------------spinner 2-----------------------------------------

        // -----------------------------------spinner 3-----------------------------------------
        binding.spinner3.adapter= ArrayAdapter.createFromResource(this,R.array.price,R.layout.spinner_selected)
            .also { adapter -> adapter.setDropDownViewResource(R.layout.spinner_dropdown)
                binding.spinner3.adapter=adapter}


        val popupWindow3 = popup[binding.spinner3] as
                androidx.appcompat.widget.ListPopupWindow
        popupWindow3.height = 700

        binding.spinner3.dropDownVerticalOffset=100

        binding.spinner3.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        // -----------------------------------spinner 2-----------------------------------------

    }

    override fun onResume() {
        super.onResume()
        loadDB()
    }

    private fun searchItem(){
        val retrofit: Retrofit=Retrofit.Builder()
            .baseUrl("http://openAPI.seoul.go.kr:8088")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    private fun loadDB(){
        items.clear()
        binding.recyclerView.adapter?.notifyDataSetChanged()

        items.add(ItemAPI(R.drawable.pic1,"a","성동구","접수중"))
        items.add(ItemAPI(R.drawable.pic2,"b","성동구","접수마감"))
        items.add(ItemAPI(R.drawable.pic3,"c","성동구","접수마감"))
        items.add(ItemAPI(R.drawable.pic1,"a","성동구","접수중"))
        items.add(ItemAPI(R.drawable.pic2,"b","성동구","접수마감"))
        items.add(ItemAPI(R.drawable.pic3,"c","성동구","접수마감"))

        binding.recyclerView.adapter?.notifyDataSetChanged()
    }

}