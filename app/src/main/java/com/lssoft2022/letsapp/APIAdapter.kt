package com.lssoft2022.letsapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.lssoft2022.letsapp.databinding.RecyclerApilistItemBinding
import com.lssoft2022.letsapp.databinding.RecyclerMainItemBinding
import com.lssoft2022.letsapp.databinding.RecyclerPartyItemBinding
import java.io.Serializable
import java.util.ArrayList

class APIAdapter(val context: Context, var items: MutableList<ApiDto>, var category:String?=null, var sharedNickname:String?, var sharedEmail:String?): RecyclerView.Adapter<APIAdapter.VH>() {

    inner class VH constructor(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding:RecyclerApilistItemBinding=RecyclerApilistItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater:LayoutInflater= LayoutInflater.from(context)
        var itemView:View =layoutInflater.inflate(R.layout.recycler_apilist_item,parent,false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apiTitle.text=items[position].title.toString().replace("/",".")
        holder.binding.apiLoca.text=items[position].area
        holder.binding.apiState.text=items[position].state
        Glide.with(context).load(items[position].imgurl).error(R.drawable.blank).into(holder.binding.apiIv)

        var ischecked:Boolean=false
        val firebaseFirestore=FirebaseFirestore.getInstance()
        val favorRef=firebaseFirestore.collection("favor")
        var count:String="0"

        holder.binding.apiFavor.setImageResource(R.drawable.ic_favor_false)
        holder.binding.apiCount.text="0"

        favorRef.document(items[position].title.toString().replace("/",".")).get().addOnSuccessListener { snapshot ->
            ischecked = snapshot.contains(sharedNickname.toString())

            if (snapshot.exists()) {
                count=snapshot.get("count").toString()
                holder.binding.apiCount.text=count
                if (ischecked) {
                    holder.binding.apiFavor.setImageResource(R.drawable.ic_favor_true)
                }else{
                    holder.binding.apiFavor.setImageResource(R.drawable.ic_favor_false)
                }
            }
        }

        holder.binding.apiFavor.setOnClickListener {
            favorRef.document(items[position].title.toString().replace("/",".")).get().addOnSuccessListener { snapshot ->
                ischecked = snapshot.contains(sharedNickname.toString())

                if(snapshot.exists()){
                    when(ischecked){
                        true->{
                            val updates = hashMapOf<String, Any>(
                                sharedNickname.toString() to FieldValue.delete()
                            )
                            var count=snapshot.getLong("count").toString().toInt()
                            var new_count=count-1

                            favorRef.document(items[position].title.toString().replace("/",".")).update(updates)
                            favorRef.document(items[position].title.toString().replace("/",".")).update("count",new_count)
                            holder.binding.apiFavor.setImageResource(R.drawable.ic_favor_false)
                            holder.binding.apiCount.text=new_count.toString()
                        }
                        false->{
                            val updates= hashMapOf<String, Any>(
                                sharedNickname.toString() to sharedNickname.toString()
                            )

                            var count=snapshot.getLong("count").toString().toInt()
                            var new_count=count+1

                            favorRef.document(items[position].title.toString().replace("/",".")).update(updates)
                            favorRef.document(items[position].title.toString().replace("/",".")).update("count",new_count)
                            holder.binding.apiFavor.setImageResource(R.drawable.ic_favor_true)
                            holder.binding.apiCount.text=new_count.toString()
                        }
                    }
                }else{
                    when(ischecked){
                        true->{
                            val updates = hashMapOf<String, Any>(
                                sharedNickname.toString() to FieldValue.delete()
                            )
                            var count=snapshot.getLong("count").toString().toInt()
                            var new_count=count-1

                            favorRef.document(items[position].title.toString().replace("/",".")).update(updates)
                            favorRef.document(items[position].title.toString().replace("/",".")).update("count",new_count)
                            holder.binding.apiFavor.setImageResource(R.drawable.ic_favor_false)
                            holder.binding.apiCount.text=new_count.toString()

                        }
                        false->{
                            val updates= hashMapOf<String, Any>(
                                sharedNickname.toString() to sharedNickname.toString(),
                                "count" to 1
                            )

                            favorRef.document(items[position].title.toString().replace("/",".")).set(updates)
                            holder.binding.apiFavor.setImageResource(R.drawable.ic_favor_true)
                            holder.binding.apiCount.text="1"

                        }
                    }
                }
            }

        }

        holder.binding.root.setOnClickListener{
            val intent:Intent = Intent(context, ItemSelectActivity::class.java)
            intent.putExtra("title",items[position].title.toString().replace("/","."))
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
            intent.putExtra("imgurl",items[position].imgurl)
            intent.putExtra("ischecked",ischecked)
            intent.putExtra("count",count)
            startActivity(context,intent, Bundle())

        }

    }

    override fun getItemCount(): Int =items.size


}