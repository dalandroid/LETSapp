package com.lssoft2022.letsapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.lssoft2022.letsapp.databinding.RecyclerMainItemBinding
import com.lssoft2022.letsapp.databinding.RecyclerPartyItemBinding

class PartyAdapter constructor(val context: Context, var items:MutableList<ItemParty>): RecyclerView.Adapter<PartyAdapter.VH>() {

    inner class VH constructor(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding:RecyclerPartyItemBinding=RecyclerPartyItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater= LayoutInflater.from(context)
        var itemView:View =layoutInflater.inflate(R.layout.recycler_party_item,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.title.text=items[position].title
        holder.binding.loca.text="장 소 : "+items[position].loca
        holder.binding.date.text="시 간 : "+items[position].date
        holder.binding.name.text="파티장 : Lv"+items[position].level+" "+items[position].name
        holder.binding.btn.text=items[position].count.toString()+"/"+items[position].maxnum.toString()

        if(items[position].count==items[position].maxnum){
            holder.binding.btn.setBackgroundColor(Color.RED)
            holder.binding.btn.isEnabled=false
        }else{
            holder.binding.btn.setBackgroundColor(Color.BLUE)
            holder.binding.btn.isEnabled=true
        }
        holder.binding.btn.setOnClickListener {
            Toast.makeText(context, "{$position} 클릭", Toast.LENGTH_SHORT).show()
            }
        }
    override fun getItemCount(): Int =items.size


}