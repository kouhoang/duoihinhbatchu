package com.example.uihnhbtch.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.uihnhbtch.R

class MainActivity :
    AppCompatActivity(),
    View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btn_play).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        startActivity(Intent(this, PlayActivity::class.java))
    }
}
