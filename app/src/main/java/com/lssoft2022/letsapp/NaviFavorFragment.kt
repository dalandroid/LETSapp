package com.lssoft2022.letsapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlinx.coroutines.*

class NaviFavorFragment : Fragment() {

    var items:MutableList<ApiDto> = ArrayList()
    var filteredItems:MutableList<ApiDto> = ArrayList()
    lateinit var recyclerView:RecyclerView
    var categoryTitle:Array<String> = arrayOf("축구장", "풋살장", "족구장", "야구장", "테니스장", "농구장", "배구장", "경기장","운동장","체육관",
        "배드민턴장","탁구장","교육시설","수영장","골프장")

    lateinit var nickname:String

    val firebaseFirestore=FirebaseFirestore.getInstance()
    val favorRef=firebaseFirestore.collection("favor")



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView=inflater.inflate(R.layout.fragment_navi_favor, container, false)

        val sharedPreferences = requireActivity().applicationContext.getSharedPreferences("account", Context.MODE_PRIVATE)
        nickname= sharedPreferences.getString("nickname",null).toString()

        recyclerView=rootView.findViewById(R.id.recycler_view)

        searchItem()

        return rootView
    }



    private fun searchItem(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://openAPI.seoul.go.kr:8088")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitService=retrofit.create(ApiRetrofitService::class.java)
        filteredItems.clear()

        for(category in 0..8){
            // retrofit 파싱
            retrofitService.getApiList(categoryTitle[category]).enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {

                    if(response.body()!=null){
                        val apiResponse=response.body()

                        apiResponse?.apiResult?.ApiList?.let {
                            items= apiResponse?.apiResult?.ApiList!!
                            filteredItems= filteredItems.plus(items) as MutableList<ApiDto>
                        }
                    }else{
                        Toast.makeText(requireContext(), "에러", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                }

            })

        }

        }


}