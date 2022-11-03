package com.lssoft2022.letsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class NaviFavorFragment : Fragment() {

    var items:MutableList<ItemAPI> = ArrayList()
    lateinit var recyclerView:RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView=inflater.inflate(R.layout.fragment_navi_favor, container, false)

        recyclerView=rootView.findViewById(R.id.recycler_view)
        recyclerView.adapter=APIAdapter(requireContext(),items)

        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadDB()
    }

    private fun loadDB(){
        items.clear()
        recyclerView.adapter?.notifyDataSetChanged()

        items.add(ItemAPI(R.drawable.pic1,"a","성동구","접수중"))
        items.add(ItemAPI(R.drawable.pic2,"b","성동구","접수마감"))
        items.add(ItemAPI(R.drawable.pic3,"c","성동구","접수마감"))
        items.add(ItemAPI(R.drawable.pic1,"a","성동구","접수중"))
        items.add(ItemAPI(R.drawable.pic2,"b","성동구","접수마감"))
        items.add(ItemAPI(R.drawable.pic3,"c","성동구","접수마감"))

        recyclerView.adapter?.notifyDataSetChanged()
    }
}