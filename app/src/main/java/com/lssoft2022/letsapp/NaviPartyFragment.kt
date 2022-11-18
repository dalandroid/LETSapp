package com.lssoft2022.letsapp

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.toObject
import java.lang.reflect.Field
import java.util.*

class NaviPartyFragment : Fragment() {

    lateinit var spinner1: Spinner
    lateinit var spinner2: Spinner
    lateinit var date: TextView
    var date_year: Int = 0
    var date_month: Int = 0
    var date_day: Int = 0
    var fullDate: String = ""

    var sp1_isCheck: Boolean = false
    var sp2_isCheck: Boolean = false

    lateinit var str1: String
    lateinit var str2: String

    lateinit var shared_email:String
    lateinit var shared_nickname:String

    val items: ArrayList<ItemParty> = ArrayList()
    val filter_items: ArrayList<ItemParty> = ArrayList()
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var rootView = inflater.inflate(R.layout.fragment_navi_party, container, false)

        val firebaseFirestore = FirebaseFirestore.getInstance()

        val sharedPreferences = requireActivity().applicationContext.getSharedPreferences("account", Context.MODE_PRIVATE)
        shared_email=sharedPreferences.getString("email",null).toString()
        shared_nickname=sharedPreferences.getString("nickname",null).toString()

        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = PartyAdapter(requireContext(), items,shared_email, shared_nickname)

        val boardRef = firebaseFirestore.collection("board")

        boardRef.get().addOnSuccessListener { result ->
            for(document in result){
                val title: String = document.get("title").toString()
                val place: String = document.get("place").toString()
                val date: String = document.get("date").toString()
                val time: String = document.get("time").toString()
                val category: String = document.get("category").toString()
                val area: String = document.get("area").toString()

                val num: String = document.get("num").toString()
                val num_int: Int = num.substring(0, num.length - 1).toInt()

                val nickname:String= document.get("nickname").toString()
                val level:String= document.get("level").toString()
                val level_int: Int= level.toInt()
                val email:String=document.get("email").toString()
                val boardTitle:String=document.get("boardTitle").toString()
                var count:Int=document.get("count").toString().toInt()

                items.add(ItemParty(title, place, date, time, nickname, level_int, num_int, count, category, area, email,boardTitle))

                recyclerView.adapter?.notifyItemInserted(items.size - 1)
            }
        }

//        boardRef.addSnapshotListener(EventListener<QuerySnapshot?> { value, error ->
//
//            val documentChangeList = value!!.documentChanges
//            for (documentChange in documentChangeList) {
//
//                val snapshot: DocumentSnapshot = documentChange.document
//
//                val dataSet = snapshot.data
//                val title: String = dataSet?.get("title").toString()
//                val place: String = dataSet?.get("place").toString()
//                val date: String = dataSet?.get("date").toString()
//                val time: String = dataSet?.get("time").toString()
//                val category: String = dataSet?.get("category").toString()
//                val area: String = dataSet?.get("area").toString()
//
//                val num: String = dataSet?.get("num").toString()
//                val num_int: Int = num.substring(0, num.length - 1).toInt()
//
//                val nickname:String= dataSet?.get("nickname").toString()
//                val level:String= dataSet?.get("level").toString()
//                val level_int: Int= level.toInt()
//                val email:String=dataSet?.get("email").toString()
//                val boardTitle:String=dataSet?.get("boardTitle").toString()
//                var count:Int=dataSet?.get("count").toString().toInt()
//
//
//
//                items.add(ItemParty(title, place, date, time, nickname, level_int, num_int, count, category, area, email,boardTitle))
//
//                recyclerView.adapter?.notifyItemInserted(items.size - 1)
//
//
//            }
//        })

        // -----------------------------------spinner 1-----------------------------------------
        spinner1 = rootView.findViewById(R.id.spinner_1)
        spinner1.adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.event,
            R.layout.spinner_selected
        )
            .also { adapter ->
                adapter.setDropDownViewResource(R.layout.spinner_dropdown)
                spinner1.adapter = adapter
            }

        val popup: Field = AppCompatSpinner::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val popupWindow = popup[spinner1] as
                androidx.appcompat.widget.ListPopupWindow
        popupWindow.height = 700

        spinner1.dropDownVerticalOffset = 100

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    sp1_isCheck = false
                    if (sp2_isCheck) {
                        makeFilter()
                    } else {
                        changelist()
                    }

                } else {
                    str1 = spinner1.selectedItem.toString()
                    sp1_isCheck = true
                    makeFilter()
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        // -----------------------------------spinner 1-----------------------------------------


        // -----------------------------------spinner 2-----------------------------------------
        spinner2 = rootView.findViewById(R.id.spinner_2)
        spinner2.adapter =
            ArrayAdapter.createFromResource(requireContext(), R.array.gu, R.layout.spinner_selected)
                .also { adapter ->
                    adapter.setDropDownViewResource(R.layout.spinner_dropdown)
                    spinner2.adapter = adapter
                }


        val popupWindow2 = popup[spinner2] as
                androidx.appcompat.widget.ListPopupWindow
        popupWindow2.height = 700

        spinner2.dropDownVerticalOffset = 100

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 == 0) {
                    sp2_isCheck = false
                    if (sp1_isCheck) {
                        makeFilter()
                    } else {
                        changelist()
                    }

                } else {
                    str2 = spinner2.selectedItem.toString()
                    sp2_isCheck = true
                    makeFilter()
                }
                recyclerView.adapter?.notifyDataSetChanged()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        // -----------------------------------spinner 2-----------------------------------------


        // -----------------------------------date dialog---------------------------------------
        date = rootView.findViewById(R.id.tv_date)
        date.setOnClickListener {
            val cal = Calendar.getInstance()
            val data = DatePickerDialog.OnDateSetListener { view, year, month, day ->
                date.text = "${year}년 ${month + 1}월 ${day}일"
                date_year = year
                date_month = month
                date_day = day
                fullDate = "${year}년 ${month + 1}월 ${day}일"
            }
            DatePickerDialog(
                requireContext(),
                data,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        // -----------------------------------date dialog---------------------------------------


        return rootView
    }

    private fun makeFilter() {
        filter_items.clear()
        // -- sp1 만 선택-- //
        if (sp1_isCheck && !sp2_isCheck) {
            for (i in 0 until items.size) {
                if (items[i].category.equals(str1)) {
                    filter_items.add(ItemParty(items[i].title,items[i].place,items[i].date,items[i].time,items[i].nickname,items[i].level,items[i].num,1,items[i].category,items[i].area, items[i].email, items[i].boardTitle))
                }

            }
        }
        // -- sp1 만 선택-- //

        // -- sp2 만 선택-- //
        if (!sp1_isCheck && sp2_isCheck) {
            for (i in 0 until items.size) {
                if (items[i].area.equals(str2)) {
                    filter_items.add(ItemParty(items[i].title,items[i].place,items[i].date,items[i].time,items[i].nickname,items[i].level,items[i].num,1,items[i].category,items[i].area,items[i].email, items[i].boardTitle))
                }

            }
        }
        // -- sp2 만 선택-- //

        // -- sp1,2 선택-- //
        if(sp1_isCheck && sp2_isCheck ){
            for(i in 0 until items.size){
                if (items[i].category.equals(str1) && items[i].area.equals(str2)) {
                    filter_items.add(ItemParty(items[i].title,items[i].place,items[i].date,items[i].time,items[i].nickname,items[i].level,items[i].num,1,items[i].category,items[i].area,items[i].email, items[i].boardTitle))
                }
            }}

        recyclerView.adapter=PartyAdapter(requireContext(),filter_items, shared_email,shared_nickname)
    }


    private fun changelist() {
        if (!sp1_isCheck && !sp2_isCheck) {
            recyclerView.adapter = PartyAdapter(requireContext(), items,shared_email,shared_nickname)
        }


    }
}