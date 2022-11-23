package com.lssoft2022.letsapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import de.hdodenhof.circleimageview.CircleImageView

class NaviListFragment : Fragment() {

    var items:MutableList<Item> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var switch: Switch

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var rootView = inflater.inflate(R.layout.fragment_navi_list, container, false)

        val gridLayoutManager: GridLayoutManager = GridLayoutManager(requireContext(),3,GridLayoutManager.VERTICAL,false)
        val linearLayoutManager:LinearLayoutManager= LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        val switch:SwitchCompat=rootView.findViewById(R.id.swit)

        val tvLevel:TextView=rootView.findViewById(R.id.tv_level)
        val tvNickname:TextView=rootView.findViewById(R.id.tv_nickname)
        val civ:CircleImageView=rootView.findViewById(R.id.civ)
        //val civ2:CircleImageView=rootView.findViewById(R.id.test_civ)

        //fire DB
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val userRef = firebaseFirestore.collection("User")
        //fire DB

        //shared DB
        val sharedPreferences = requireActivity().applicationContext.getSharedPreferences("account", Context.MODE_PRIVATE)

        val email:String?=sharedPreferences.getString("email",null)
        //Shared DB

        userRef.document(email!!).get().addOnSuccessListener { snapshot ->
            if(snapshot.exists()){
                val nickname:String=snapshot.getString("nickname").toString()
                val level:String=snapshot.getString("level").toString()

                tvLevel.text="Level "+level
                tvNickname.text=nickname

            }else{
                tvLevel.text="Level 0"
                tvNickname.text="닉네임을 설정해주세요"
            }
        }

        Glide.with(requireContext()).load(sharedPreferences.getString("imgurl",R.drawable.profile.toString())).error(R.drawable.profile).into(civ)

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
        items.add(Item(R.drawable.pic11,"배드민턴장"))
        items.add(Item(R.drawable.pic12,"탁구장"))
        items.add(Item(R.drawable.pic6,"교육시설"))
        items.add(Item(R.drawable.pic14,"수영장"))
        items.add(Item(R.drawable.pic15,"골프장"))

        recyclerView.adapter?.notifyDataSetChanged()
    }

}