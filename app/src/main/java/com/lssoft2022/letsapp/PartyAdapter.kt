package com.lssoft2022.letsapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.lssoft2022.letsapp.databinding.RecyclerMainItemBinding
import com.lssoft2022.letsapp.databinding.RecyclerPartyItemBinding

class PartyAdapter constructor(val context: Context, var items:MutableList<ItemParty>, var sharedEmail:String, var sharedNIckname:String): RecyclerView.Adapter<PartyAdapter.VH>() {

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
        holder.binding.loca.text="장 소 : "+items[position].place
        holder.binding.date.text="날 짜 : "+items[position].date
        holder.binding.time.text="시 간 : "+items[position].time
        holder.binding.name.text="파티장 : Lv"+items[position].level+" "+items[position].nickname
        holder.binding.btn.text=items[position].count.toString()+"/"+items[position].num.toString()

        var ischecked:Boolean=false
        val firebaseFirestore=FirebaseFirestore.getInstance()
        val boardRef=firebaseFirestore.collection("board")

        if(items[position].count==items[position].num){
            holder.binding.btn.setBackgroundColor(Color.RED)
            holder.binding.btn.isEnabled=false
        }else{
            holder.binding.btn.setBackgroundColor(Color.BLUE)
            holder.binding.btn.isEnabled=true
        }
        boardRef.document(items[position].boardTitle).get().addOnSuccessListener { snapshot ->
            ischecked = snapshot.contains(sharedNIckname)

            if(ischecked){holder.binding.btn.setBackgroundColor(Color.GREEN)           }}


        holder.binding.btn.setOnClickListener {

            boardRef.document(items[position].boardTitle).get().addOnSuccessListener { snapshot ->
                ischecked = snapshot.contains(sharedNIckname)

                // 리스너 쓸때 주의하기. 동기속도 때문에 실행순서 바뀔수도있음
                when(ischecked){
                    true ->{
                        boardRef.document(items[position].boardTitle).get().addOnSuccessListener { snapshot ->
                            var count=snapshot.getLong("count").toString().toInt()
                            var new_count=count-1

                            val updates = hashMapOf<String, Any>(
                                sharedNIckname to FieldValue.delete()
                            )

                            boardRef.document(items[position].boardTitle).update(updates)
                            boardRef.document(items[position].boardTitle).update("count",new_count)
                            holder.binding.btn.setBackgroundColor(Color.BLUE)
                            holder.binding.btn.text=new_count.toString()+"/"+items[position].num.toString()
                        }
                    }

                    false -> {
                        boardRef.document(items[position].boardTitle).get().addOnSuccessListener { snapshot ->
                            var count=snapshot.getLong("count").toString().toInt()
                            var new_count=count+1

                            boardRef.document(items[position].boardTitle).update(sharedNIckname,sharedNIckname)
                            boardRef.document(items[position].boardTitle).update("count",new_count)
                            holder.binding.btn.setBackgroundColor(Color.GREEN)
                            holder.binding.btn.text=new_count.toString()+"/"+items[position].num.toString()

                        }
                    }
                }
            }


        }

        holder.binding.root.setOnLongClickListener {

            if (items[position].email.equals(sharedEmail)){
                AlertDialog.Builder(context)
                    .setTitle("삭제")
                    .setMessage("정말로 삭제하시겠습니까?")
                    .setPositiveButton("네", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            boardRef.document(items[position].boardTitle).delete()
                        }
                    })
                    .setNegativeButton("아니요", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                        }
                    }).create().show()
            }else{
                Toast.makeText(context, "권한이 없습니다", Toast.LENGTH_SHORT).show()
            }
            return@setOnLongClickListener(true)
        }
    }
    override fun getItemCount(): Int =items.size


}