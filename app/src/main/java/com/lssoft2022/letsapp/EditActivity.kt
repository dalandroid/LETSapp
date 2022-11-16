package com.lssoft2022.letsapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import com.google.firebase.firestore.FirebaseFirestore
import com.lssoft2022.letsapp.databinding.ActivityEditBinding
import java.lang.reflect.Field
import java.util.*

class EditActivity : AppCompatActivity() {

    var dateString=""
    var timeString=""

    lateinit var nickname:String
    lateinit var level:String

    val binding:ActivityEditBinding by lazy { ActivityEditBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val area =intent.getStringExtra("area")
        val place =intent.getStringExtra("place")
        val title =intent.getStringExtra("title")
        val category=intent.getStringExtra("category")


        // fire board DB
        val firebaseFirestore = FirebaseFirestore.getInstance()
        val boardRef = firebaseFirestore.collection("board")
        // fire board DB

        // fire user DB
        val userRef =firebaseFirestore.collection("User")

        // fire user DB

        // shared DB
        val sharedPreferences=getSharedPreferences("account", MODE_PRIVATE)
        val email:String=sharedPreferences.getString("email",null).toString()
        // shared DB

        userRef.document(email).get().addOnSuccessListener { snapshot ->
            if(snapshot.exists()){
                nickname=snapshot.getString("nickname").toString()
                level=snapshot.getString("level").toString()
            }
        }

        binding.etPlace.setText("$area $place")
        binding.btnCancel.setOnClickListener{finish()}

        //-------------------num spinner-----------------------------//
        binding.numSpinner.adapter= ArrayAdapter.createFromResource(this,R.array.num,R.layout.spinner_selected)
            .also { adapter -> adapter.setDropDownViewResource(R.layout.spinner_dropdown)
                binding.numSpinner.adapter=adapter}

        val popup: Field = AppCompatSpinner::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val popupWindow = popup[binding.numSpinner] as
                androidx.appcompat.widget.ListPopupWindow
        popupWindow.height = 500

        binding.numSpinner.dropDownVerticalOffset=100
        //-------------------num spinner-----------------------------//

        //-------------------num spinner-----------------------------//
        binding.categorySpinner.adapter= ArrayAdapter.createFromResource(this,R.array.event,R.layout.spinner_selected)
            .also { adapter -> adapter.setDropDownViewResource(R.layout.spinner_dropdown)
                binding.categorySpinner.adapter=adapter}

        val popup2: Field = AppCompatSpinner::class.java.getDeclaredField("mPopup")
        popup2.isAccessible = true
        val popupWindow2 = popup[binding.categorySpinner] as
                androidx.appcompat.widget.ListPopupWindow
        popupWindow2.height = 500

        binding.numSpinner.dropDownVerticalOffset=100
        //-------------------num spinner-----------------------------//


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
        // ------------------------------------작성완료 버튼------------------------------------------------------
        binding.btnComplete.setOnClickListener {

            val db_title:String=binding.etTitle.text.toString()
            val db_place:String=binding.etPlace.text.toString()
            val db_date:String=binding.tvDate.text.toString()
            val db_time:String=binding.tvTime.text.toString()
            val db_maxnum:String=binding.numSpinner.selectedItem.toString()
            val db_category:String=binding.categorySpinner.selectedItem.toString()

            val level_int:Int=level.toInt()+1
            level=level_int.toString()
            userRef.document(email).update("level",level)

            var item:DBitem= DBitem( db_title,db_place,db_date,db_time,db_maxnum,db_category,area!!,level,nickname,email)

            boardRef.document("party_"+System.currentTimeMillis()).set(item)


            val intent:Intent=Intent(this@EditActivity,MainActivity::class.java)
            intent.putExtra("fragment","party")
            startActivity(intent)
            finish()
        }

    }
}