package com.lssoft2022.letsapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.lssoft2022.letsapp.databinding.FragmentNaviToolBinding

class NaviToolFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView= inflater.inflate(R.layout.fragment_navi_tool, container, false)

        val ivToProfile:ImageView=rootView.findViewById(R.id.tool_toprofile)
        val ivToNotice:ImageView=rootView.findViewById(R.id.tool_tonotice)
        val ivToSetting:ImageView=rootView.findViewById(R.id.tool_tosetting)

        ivToProfile.setOnClickListener { startActivity(Intent(requireContext(),ProfileActivity::class.java)) }
        // 액티비티 바꾸기
        ivToNotice.setOnClickListener { startActivity(Intent(requireContext(),ProfileActivity::class.java)) }

        return rootView
    }
}