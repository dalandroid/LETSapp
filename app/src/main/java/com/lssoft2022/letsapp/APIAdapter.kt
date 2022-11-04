package com.lssoft2022.letsapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lssoft2022.letsapp.databinding.RecyclerApilistItemBinding
import com.lssoft2022.letsapp.databinding.RecyclerMainItemBinding
import com.lssoft2022.letsapp.databinding.RecyclerPartyItemBinding

class APIAdapter constructor(val context: Context, var items:MutableList<ItemAPI>): RecyclerView.Adapter<APIAdapter.VH>() {

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
        holder.binding.apiLoca.text=items[position].loca
        holder.binding.apiState.text=items[position].state
        Glide.with(context).load(items[position].imgId).into(holder.binding.apiIv)

        holder.binding.root.setOnClickListener{

        }

    }
    override fun getItemCount(): Int =items.size


}