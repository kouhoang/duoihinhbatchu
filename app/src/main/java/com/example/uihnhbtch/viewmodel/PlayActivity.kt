package com.thanggun99.duoihinhbatchu

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.uihnhbtch.R
import com.example.uihnhbtch.model.IDButton
import com.example.uihnhbtch.model.Question
import java.util.Random

class PlayActivity : Activity() {
    private val check_answer = 0
    private val game_over = 1
    private var heart = 5
    private var point = 0
    private lateinit var handler: Handler
    private lateinit var btntiep: Button
    private lateinit var txtHeart: TextView
    private lateinit var txtPoint: TextView
    private lateinit var imgPicture: ImageView
    private lateinit var lnAnswer1: LinearLayout
    private lateinit var lnAnswer2: LinearLayout
    private lateinit var lnCh1: LinearLayout
    private lateinit var lnCh2: LinearLayout
    private lateinit var listQuestions: List<Question>
    private val random = Random()
    private var i = 0
    private var pst = 0
    private lateinit var dapan: String
    private val listChar = mutableListOf<IDButton>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        initComponents()
        makeQuestion()

        handler =
            object : Handler() {
                override fun handleMessage(msg: Message) {
                    super.handleMessage(msg)
                    when (msg.what) {
                        check_answer -> {
                            if (checkAnswer()) {
                                Toast.makeText(this@PlayActivity, "Đẳng cấp đấy !", Toast.LENGTH_SHORT).show()
                                point += 100
                                txtPoint.text = point.toString()
                                for (i in 16 until dapan.length + 16) {
                                    findViewById<Button>(i).apply {
                                        setBackgroundResource(R.drawable.ic_tile_true)
                                        isClickable = false
                                    }
                                }
                                btntiep.visibility = View.VISIBLE
                            } else {
                                heart--
                                txtHeart.text = heart.toString()
                                if (heart <= 0) {
                                    handler.sendEmptyMessage(game_over)
                                    return
                                }
                                Toast.makeText(this@PlayActivity, "Sai rồi bro !", Toast.LENGTH_SHORT).show()
                                for (i in 16 until dapan.length + 16) {
                                    findViewById<Button>(i).setBackgroundResource(R.drawable.ic_tile_false)
                                }
                                if (heart <= 0) {
                                    handler.sendEmptyMessage(game_over)
                                }
                            }
                        }
                        game_over -> {
                            Toast.makeText(this@PlayActivity, "GAME OVER", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
            }
    }

    private fun makeQuestion() {
        val qs = listQuestions[i]
        dapan = qs.context
        val inflater = LayoutInflater.from(this)
        if (dapan.length > 8) {
            repeat(8) { index ->
                val view = inflater.inflate(R.layout.item_btn_answer, lnAnswer1, false) as Button
                view.id = 16 + index
                lnAnswer1.addView(view)
                view.setOnClickListener {
                    if (view.text.isNotEmpty()) {
                        view.text = ""
                        listChar.indexOfFirst { it.idAnwser == view.id }.takeIf { it != -1 }?.let { index ->
                            val charButton = findViewById<Button>(listChar[index].idPick)
                            charButton.visibility = View.VISIBLE
                            listChar.removeAt(index)
                        }
                        pst--
                        for (i in 16 until dapan.length + 16) {
                            findViewById<Button>(i).setBackgroundResource(R.drawable.ic_anwser)
                        }
                    }
                }
            }
            repeat(dapan.length - 8) { index ->
                val view = inflater.inflate(R.layout.item_btn_answer, lnAnswer2, false) as Button
                view.id = 16 + index + 8
                lnAnswer2.addView(view)
                view.setOnClickListener {
                    if (view.text.isNotEmpty()) {
                        view.text = ""
                        listChar.indexOfFirst { it.idAnwser == view.id }.takeIf { it != -1 }?.let { index ->
                            val charButton = findViewById<Button>(listChar[index].idPick)
                            charButton.visibility = View.VISIBLE
                            listChar.removeAt(index)
                        }
                        pst--
                        for (i in 16 until dapan.length + 16) {
                            findViewById<Button>(i).setBackgroundResource(R.drawable.ic_anwser)
                        }
                    }
                }
            }
        } else {
            repeat(dapan.length) { index ->
                val view = inflater.inflate(R.layout.item_btn_answer, lnAnswer1, false) as Button
                view.id = 16 + index
                lnAnswer1.addView(view)
                view.setOnClickListener {
                    if (view.text.isNotEmpty()) {
                        view.text = ""
                        listChar.indexOfFirst { it.idAnwser == view.id }.takeIf { it != -1 }?.let { index ->
                            val charButton = findViewById<Button>(listChar[index].idPick)
                            charButton.visibility = View.VISIBLE
                            listChar.removeAt(index)
                        }
                        pst--
                        for (i in 16 until dapan.length + 16) {
                            findViewById<Button>(i).setBackgroundResource(R.drawable.ic_anwser)
                        }
                    }
                }
            }
        }
        imgPicture.setImageResource(qs.id)

        val kt = arrayOf("a", "b", "c", "d", "e", "g", "h", "i", "k", "l", "m", "n", "o", "u", "q", "p", "r", "s", "t", "y", "v", "x")
        val tl = mutableListOf<String>()

        repeat(dapan.length) { index ->
            tl.add(dapan[index].toString())
        }
        repeat(16 - dapan.length) {
            tl.add(kt[random.nextInt(kt.size)])
        }
        tl.shuffle()

        repeat(8) { index ->
            val view = inflater.inflate(R.layout.item_btn, lnCh1, false) as Button
            view.id = index
            view.text = tl[index]
            lnCh1.addView(view)
            view.setOnClickListener {
                if (pst < dapan.length) {
                    addChar(view.id, view.text.toString())
                    view.visibility = View.INVISIBLE
                }
            }
        }
        repeat(8) { index ->
            val view = inflater.inflate(R.layout.item_btn, lnCh2, false) as Button
            view.id = index + 8
            view.text = tl[index + 8]
            lnCh2.addView(view)
            view.setOnClickListener {
                if (pst < dapan.length) {
                    addChar(view.id, view.text.toString())
                    view.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun initComponents() {
        btntiep = findViewById(R.id.btn_next)
        btntiep.setOnClickListener {
            newQuestion()
        }
        imgPicture = findViewById(R.id.img_picture)
        lnAnswer1 = findViewById(R.id.anwser1)
        lnAnswer2 = findViewById(R.id.anwser2)
        lnCh1 = findViewById(R.id.ln_3)
        lnCh2 = findViewById(R.id.ln_4)
        txtHeart = findViewById(R.id.txt_heart)
        txtPoint = findViewById(R.id.txt_point)

        txtHeart.text = heart.toString()
        txtPoint.text = point.toString()

        listQuestions =
            listOf(
                Question(R.drawable.aomua, "aomua"),
                Question(R.drawable.baocao, "baocao"),
                Question(R.drawable.canthiep, "canthiep"),
                Question(R.drawable.cattuong, "cattuong"),
                Question(R.drawable.chieutre, "chieutre"),
                Question(R.drawable.danong, "danong"),
                Question(R.drawable.danhlua, "danhlua"),
                Question(R.drawable.giandiep, "giandiep"),
                Question(R.drawable.giangmai, "giangmai"),
                Question(R.drawable.hoidong, "hoidong"),
                Question(R.drawable.hongtam, "hongtam"),
                Question(R.drawable.khoailang, "khoailang"),
                Question(R.drawable.kiemchuyen, "kiemchuyen"),
                Question(R.drawable.lancan, "lancan"),
                Question(R.drawable.nambancau, "nambancau"),
                Question(R.drawable.oto, "oto"),
                Question(R.drawable.masat, "masat"),
                Question(R.drawable.lancan, "lancan"),
                Question(R.drawable.quyhang, "quyhang"),
                Question(R.drawable.songsong, "songsong"),
                Question(R.drawable.xedapdien, "xedapdien"),
                Question(R.drawable.xakep, "xakep"),
                Question(R.drawable.xaphong, "xaphong"),
                Question(R.drawable.vuonbachthu, "vuonbachthu"),
                Question(R.drawable.vuaphaluoi, "vuaphaluoi"),
                Question(R.drawable.tranhthu, "tranhthu"),
                Question(R.drawable.totien, "totien"),
                Question(R.drawable.tichphan, "tichphan"),
                Question(R.drawable.thattinh, "thattinh"),
                Question(R.drawable.thothe, "thothe"),
                Question(R.drawable.tohoai, "tohoai"),
            ).shuffled()
    }

    private fun newQuestion() {
        if (i < listQuestions.size - 1) {
            listChar.clear()
            btntiep.visibility = View.INVISIBLE
            pst = 0
            i++
            lnAnswer1.removeAllViews()
            lnAnswer2.removeAllViews()
            lnCh1.removeAllViews()
            lnCh2.removeAllViews()
            makeQuestion()
        } else {
            Toast.makeText(this, "Ái chà, đỉnh!", Toast.LENGTH_LONG).show()
        }
    }

    fun addChar(
        id: Int,
        s: String,
    ) {
        for (i in 16 until dapan.length + 16) {
            val button = findViewById<Button>(i)
            if (button.text.isEmpty()) {
                button.text = s
                listChar.add(IDButton(id, i))
                pst++
                if (pst == dapan.length) {
                    handler.sendEmptyMessage(check_answer)
                }
                return
            }
        }
    }

    fun checkAnswer(): Boolean {
        val da =
            (16 until dapan.length + 16).joinToString("") { id ->
                findViewById<Button>(id).text.toString()
            }
        return da == dapan
    }
}
