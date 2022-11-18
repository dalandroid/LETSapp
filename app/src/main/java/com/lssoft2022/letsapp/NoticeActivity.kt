package com.lssoft2022.letsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lssoft2022.letsapp.databinding.ActivityNoticeBinding

class NoticeActivity : AppCompatActivity() {

    val binding by lazy { ActivityNoticeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}