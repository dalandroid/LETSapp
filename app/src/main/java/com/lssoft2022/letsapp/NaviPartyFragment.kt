package com.lssoft2022.letsapp

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.app.NotificationCompat.getColor
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Field
import java.util.*

class NaviPartyFragment : Fragment() {

    lateinit var spinner1:Spinner
    lateinit var spinner2: Spinner
    lateinit var date:TextView
    var date_year:Int=0
    var date_month:Int=0
    var date_day:Int=0

    val items:ArrayList<ItemParty> = ArrayList()
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var rootView = inflater.inflate(R.layout.fragment_navi_party, container, false)

        // -----------------------------------spinner 1-----------------------------------------
        spinner1=rootView.findViewById(R.id.spinner_1)
        spinner1.adapter=ArrayAdapter.createFromResource(requireContext(),R.array.event,R.layout.spinner_selected)
            .also { adapter -> adapter.setDropDownViewResource(R.layout.spinner_dropdown)
            spinner1.adapter=adapter}

        val popup: Field = AppCompatSpinner::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val popupWindow = popup[spinner1] as
                androidx.appcompat.widget.ListPopupWindow
        popupWindow.height = 700

        spinner1.dropDownVerticalOffset=100

        spinner1.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                    1-> Toast.makeText(requireContext(), ""+spinner1.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        // -----------------------------------spinner 1-----------------------------------------


        // -----------------------------------spinner 2-----------------------------------------
        spinner2=rootView.findViewById(R.id.spinner_2)
        spinner2.adapter=ArrayAdapter.createFromResource(requireContext(),R.array.gu,R.layout.spinner_selected)
            .also { adapter -> adapter.setDropDownViewResource(R.layout.spinner_dropdown)
                spinner2.adapter=adapter}


        val popupWindow2 = popup[spinner2] as
                androidx.appcompat.widget.ListPopupWindow
        popupWindow2.height = 700

        spinner2.dropDownVerticalOffset=100

        spinner2.onItemSelectedListener= object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                    1-> Toast.makeText(requireContext(), ""+spinner2.selectedItem.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        // -----------------------------------spinner 2-----------------------------------------


        // -----------------------------------date dialog---------------------------------------
        date=rootView.findViewById(R.id.tv_date)
        date.setOnClickListener {
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                date.text = "${year}년 ${month+1}월 ${day}일"
                date_year=year
                date_month=month
                date_day=day
            }
            DatePickerDialog(requireContext(), data, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }
        // -----------------------------------date dialog---------------------------------------

        recyclerView=rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter=PartyAdapter(requireContext(),items)



        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadDB()
    }



    private fun loadDB(){
        items.clear()
        recyclerView.adapter?.notifyDataSetChanged()

        items.add(ItemParty("5:5 농구","성동구 농구장","2022년 11월 18일","15시 00분","sam",3,10,6))
        items.add(ItemParty("3:3 농구","응봉동 농구장","2022년 11월 18일","11시 30분","sam",1,6,6))
        items.add(ItemParty("5:5 풋살","중구 농구장","2022년 11월 18일","10시 00분","sam",10,10,3))
        items.add(ItemParty("1:1 배드민턴 치실분","성수동 배드민턴장","2022년 11월 18일","18시 30분","sam",5,2,1))

        recyclerView.adapter?.notifyDataSetChanged()
    }

}