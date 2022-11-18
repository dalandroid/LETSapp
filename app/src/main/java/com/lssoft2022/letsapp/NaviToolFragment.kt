package com.lssoft2022.letsapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.lssoft2022.letsapp.databinding.FragmentNaviToolBinding
import de.hdodenhof.circleimageview.CircleImageView

class NaviToolFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView= inflater.inflate(R.layout.fragment_navi_tool, container, false)

        val ivToProfile:ImageView=rootView.findViewById(R.id.tool_toprofile)
        val ivToNotice:ImageView=rootView.findViewById(R.id.tool_tonotice)
        val ivToSetting:ImageView=rootView.findViewById(R.id.tool_tosetting)
        val ivTologout:ImageView=rootView.findViewById(R.id.tool_tologout)

        val tvLevel:TextView=rootView.findViewById(R.id.tool_tv_level)
        val tvNickname:TextView=rootView.findViewById(R.id.tool_tv_name)
        val civ:CircleImageView=rootView.findViewById(R.id.tool_civ)

        val sharedPreferences = requireActivity().applicationContext.getSharedPreferences("account", Context.MODE_PRIVATE)
        var editor: SharedPreferences.Editor=sharedPreferences.edit()

        val email:String=sharedPreferences.getString("email",null).toString()

        val firestore=FirebaseFirestore.getInstance()
        val userRef=firestore.collection("User")

        userRef.document(email).get().addOnSuccessListener { snapshot->
            if(snapshot.exists()){
                tvLevel.text="Level "+snapshot.getString("level").toString()
                tvNickname.text=snapshot.getString("nickname").toString()
                Glide.with(requireContext()).load(sharedPreferences.getString("imgurl",R.drawable.profile.toString())).error(R.drawable.profile).into(civ)
            }
        }

        ivToProfile.setOnClickListener { startActivity(Intent(requireContext(),ProfileActivity::class.java)) }
        // 액티비티 바꾸기
        ivToNotice.setOnClickListener { startActivity(Intent(requireContext(),NoticeActivity::class.java)) }

        ivTologout.setOnClickListener {
            val intent:Intent=Intent(requireContext(),LoginActivity::class.java)
            editor.putString("imgurl",null)
            editor.putBoolean("islogin",false)
            editor.putString("email",null)
            editor.putString("nickname",null)
            editor.commit()
            startActivity(intent)
        }

        return rootView
    }
}