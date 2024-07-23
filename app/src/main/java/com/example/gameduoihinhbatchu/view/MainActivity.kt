package com.example.gameduoihinhbatchu.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.gameduoihinhbatchu.databinding.ActivityMainBinding

class MainActivity :
    AppCompatActivity(),
    View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlay.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        startActivity(Intent(this, PlayActivity::class.java))
    }
}
