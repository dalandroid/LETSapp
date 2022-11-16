package com.lssoft2022.letsapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lssoft2022.letsapp.databinding.RecyclerApilistItemBinding
import com.lssoft2022.letsapp.databinding.RecyclerMainItemBinding
import com.lssoft2022.letsapp.databinding.RecyclerPartyItemBinding
import java.io.Serializable
import java.util.ArrayList

class APIAdapter(val context: Context, var items: MutableList<ApiDto>, var category:String?=null): RecyclerView.Adapter<APIAdapter.VH>() {

    inner class VH constructor(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding:RecyclerApilistItemBinding=RecyclerApilistItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater= LayoutInflater.from(context)
        var itemView:View =layoutInflater.inflate(R.layout.recycler_apilist_item,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apiTitle.text=items[position].title
        holder.binding.apiLoca.text=items[position].area
        holder.binding.apiState.text=items[position].state
        Glide.with(context).load(items[position].imgurl).error(R.drawable.blank).into(holder.binding.apiIv)

//        holder.binding.apiFavor.setOnClickListener {
//            val firebaseFirestore = FirebaseFirestore.getInstance()
//            val db= Firebase.firestore
//            //채팅방 이름으로 컬렉션만들고 이를 참조
//            val favorRef = firebaseFirestore.collection("favor")
//        }

        holder.binding.root.setOnClickListener{
            val intent:Intent = Intent(context, ItemSelectActivity::class.java)
            intent.putExtra("title",items[position].title)
            intent.putExtra("tel",items[position].tel)
            intent.putExtra("area",items[position].area)
            intent.putExtra("place",items[position].place)
            intent.putExtra("v_max",items[position].v_max)
            intent.putExtra("v_min",items[position].v_min)
            intent.putExtra("pay",items[position].pay)
            intent.putExtra("x",items[position].x.toString())
            intent.putExtra("y",items[position].y.toString())
            intent.putExtra("site",items[position].site)
            intent.putExtra("target",items[position].target)
            intent.putExtra("category",category)
            startActivity(context,intent, Bundle())

        }

    }
    override fun getItemCount(): Int =items.size


}