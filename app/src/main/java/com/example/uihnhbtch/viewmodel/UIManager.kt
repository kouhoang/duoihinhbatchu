package com.example.uihnhbtch.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.uihnhbtch.R
import com.example.uihnhbtch.view.PlayActivity
import java.util.Random

class UIManager(
    private val activity: PlayActivity,
    private val questionManager: QuestionManager,
) {
    lateinit var btntiep: Button
    lateinit var txtHeart: TextView
    lateinit var txtPoint: TextView
    lateinit var imgPicture: ImageView
    lateinit var lnAnswer1: LinearLayout
    lateinit var lnAnswer2: LinearLayout
    lateinit var lnCh1: LinearLayout
    lateinit var lnCh2: LinearLayout

    fun initComponents() {
        btntiep = activity.findViewById(R.id.btn_next)
        btntiep.setOnClickListener { questionManager.newQuestion() }
        imgPicture = activity.findViewById(R.id.img_picture)
        lnAnswer1 = activity.findViewById(R.id.anwser1)
        lnAnswer2 = activity.findViewById(R.id.anwser2)
        lnCh1 = activity.findViewById(R.id.ln_3)
        lnCh2 = activity.findViewById(R.id.ln_4)
        txtHeart = activity.findViewById(R.id.txt_heart)
        txtPoint = activity.findViewById(R.id.txt_point)

        txtHeart.text = "5"
        txtPoint.text = "0"
    }

    fun addAnswerButtons(
        length: Int,
        inflater: LayoutInflater,
    ) {
        if (length > 8) {
            repeat(8) { index ->
                val view = inflater.inflate(R.layout.item_btn_answer, lnAnswer1, false) as Button
                view.id = 16 + index
                lnAnswer1.addView(view)
                view.setOnClickListener { questionManager.handleAnswerClick(view) }
            }
            repeat(length - 8) { index ->
                val view = inflater.inflate(R.layout.item_btn_answer, lnAnswer2, false) as Button
                view.id = 16 + index + 8
                lnAnswer2.addView(view)
                view.setOnClickListener { questionManager.handleAnswerClick(view) }
            }
        } else {
            repeat(length) { index ->
                val view = inflater.inflate(R.layout.item_btn_answer, lnAnswer1, false) as Button
                view.id = 16 + index
                lnAnswer1.addView(view)
                view.setOnClickListener { questionManager.handleAnswerClick(view) }
            }
        }
    }

    fun addCharButtons(
        answer: String,
        inflater: LayoutInflater,
        random: Random,
    ) {
        val chars = arrayOf("a", "b", "c", "d", "e", "g", "h", "i", "k", "l", "m", "n", "o", "u", "q", "p", "r", "s", "t", "y", "v", "x")
        val shuffledChars = mutableListOf<String>()

        shuffledChars.addAll(answer.map { it.toString() })
        repeat(16 - answer.length) {
            shuffledChars.add(chars[random.nextInt(chars.size)])
        }
        shuffledChars.shuffle()

        repeat(8) { index ->
            val view = inflater.inflate(R.layout.item_btn, lnCh1, false) as Button
            view.id = index
            view.text = shuffledChars[index]
            lnCh1.addView(view)
            view.setOnClickListener { questionManager.handleCharClick(view) }
        }
        repeat(8) { index ->
            val view = inflater.inflate(R.layout.item_btn, lnCh2, false) as Button
            view.id = index + 8
            view.text = shuffledChars[index + 8]
            lnCh2.addView(view)
            view.setOnClickListener { questionManager.handleCharClick(view) }
        }
    }

    fun setAnswerResult(isCorrect: Boolean) {
        val answerLength = questionManager.answer.length
        for (i in 16 until answerLength + 16) {
            val button = activity.findViewById<Button>(i)
            if (isCorrect) {
                button.setBackgroundResource(R.drawable.ic_tile_true)
                button.isClickable = false
            } else {
                button.setBackgroundResource(R.drawable.ic_tile_false)
            }
        }
        if (isCorrect) {
            btntiep.visibility = View.VISIBLE
        }
    }

    fun resetAnswerBackground(length: Int) {
        for (i in 16 until length + 16) {
            activity.findViewById<Button>(i).setBackgroundResource(R.drawable.ic_anwser)
        }
    }

    fun resetForNewQuestion() {
        lnAnswer1.removeAllViews()
        lnAnswer2.removeAllViews()
        lnCh1.removeAllViews()
        lnCh2.removeAllViews()
        btntiep.visibility = View.INVISIBLE
    }
}
