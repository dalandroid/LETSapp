package com.lssoft2022.letsapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NaviListFragment : Fragment() {

    var items:MutableList<Item> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var switch: Switch

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var rootView = inflater.inflate(R.layout.fragment_navi_list, container, false)

        val gridLayoutManager: GridLayoutManager = GridLayoutManager(requireContext(),3,GridLayoutManager.VERTICAL,false)
        val linearLayoutManager:LinearLayoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        val switch:SwitchCompat=rootView.findViewById(R.id.swit)


        recyclerView=rootView.findViewById(R.id.recycler_view)as RecyclerView

        recyclerView.layoutManager=linearLayoutManager
        recyclerView.adapter=MainAdapter_list(requireContext(),items)

        switch.setOnCheckedChangeListener { compoundButton, onSwitch ->
            if(onSwitch){
                recyclerView.layoutManager=gridLayoutManager
                recyclerView.adapter=MainAdapter(requireContext(),items)
            }
            else{
                recyclerView.layoutManager=linearLayoutManager
                recyclerView.adapter=MainAdapter_list(requireContext(),items)
            }
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadDB()
    }

    private fun loadDB(){
        items.clear()
        recyclerView.adapter?.notifyDataSetChanged()

        items.add(Item(R.drawable.pic1,"축구장"))
        items.add(Item(R.drawable.pic2,"풋살장"))
        items.add(Item(R.drawable.pic3,"족구장"))
        items.add(Item(R.drawable.pic4,"야구장"))
        items.add(Item(R.drawable.pic5,"테니스장"))
        items.add(Item(R.drawable.pic6,"농구장"))
        items.add(Item(R.drawable.pic7,"배구장"))
        items.add(Item(R.drawable.pic8,"경기장"))
        items.add(Item(R.drawable.pic6,"운동장"))
        items.add(Item(R.drawable.pic6,"체육관"))
        items.add(Item(R.drawable.pic6,"배드민턴장"))
        items.add(Item(R.drawable.pic6,"탁구장"))
        items.add(Item(R.drawable.pic6,"교육시설"))
        items.add(Item(R.drawable.pic6,"수영장"))
        items.add(Item(R.drawable.pic6,"골프장"))

        recyclerView.adapter?.notifyDataSetChanged()
    }

}