package com.lssoft2022.letsapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lssoft2022.letsapp.databinding.RecyclerMainItemBinding

class MainAdapter constructor(val context: Context, var items:MutableList<Item>): RecyclerView.Adapter<MainAdapter.VH>() {

    inner class VH constructor(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding:RecyclerMainItemBinding = RecyclerMainItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater= LayoutInflater.from(context)
        var itemView:View =layoutInflater.inflate(R.layout.recycler_main_item,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.recyclerIv.setImageResource(items[position].imgId)
        holder.binding.recyclerTv.text=items[position].name

        holder.binding.cardView.setOnClickListener{clickedCategory(position)}
    }

    override fun getItemCount(): Int =items.size

    private fun clickedCategory(category: Int) {
        val intent: Intent = Intent(context, ListActivity::class.java)
        intent.putExtra("category", category)
        ContextCompat.startActivity(context, intent, Bundle())
    }


}