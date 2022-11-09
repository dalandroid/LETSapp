package com.lssoft2022.letsapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import com.lssoft2022.letsapp.databinding.ActivityEditBinding
import java.lang.reflect.Field
import java.util.*

class EditActivity : AppCompatActivity() {

    var dateString=""
    var timeString=""

    val binding:ActivityEditBinding by lazy { ActivityEditBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val area =intent.getStringExtra("area")
        val place =intent.getStringExtra("place")

        binding.etPlace.setText("$area $place")
        binding.btnCancel.setOnClickListener{finish()}

        binding.spinner.adapter= ArrayAdapter.createFromResource(this,R.array.num,R.layout.spinner_selected)
            .also { adapter -> adapter.setDropDownViewResource(R.layout.spinner_dropdown)
                binding.spinner.adapter=adapter}

        val popup: Field = AppCompatSpinner::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val popupWindow = popup[binding.spinner] as
                androidx.appcompat.widget.ListPopupWindow
        popupWindow.height = 500

        binding.spinner.dropDownVerticalOffset=100

        binding.tvDate.setOnClickListener {
            val cal=Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString="${year}년 ${month+1}월 ${dayOfMonth}일"
                binding.tvDate.text=dateString
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.tvTime.setOnClickListener {
            val cal=Calendar.getInstance()
            val timeSetListener=TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                timeString="${hourOfDay}시 ${minute}분"
                binding.tvTime.text=timeString
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

    }
}