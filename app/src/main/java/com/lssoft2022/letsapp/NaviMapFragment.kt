package com.lssoft2022.letsapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


class NaviMapFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView= inflater.inflate(R.layout.fragment_navi_map, container, false)

        val mapView: net.daum.android.map.MapView = net.daum.android.map.MapView(requireContext())
        val mapViewContainer:ViewGroup=rootView.findViewById(R.id.map_view)
        mapViewContainer.addView(mapView)

        return rootView
    }
}