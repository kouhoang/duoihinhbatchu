package com.example.gameduoihinhbatchu.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.example.gameduoihinhbatchu.R
import com.example.gameduoihinhbatchu.view.PlayActivity
import com.example.uihnhbtch.model.IDButton
import com.example.uihnhbtch.model.Question
import java.util.Random

class QuestionManager(
    private val activity: PlayActivity,
    private val gameHandler: GameHandler,
) {
    private lateinit var listQuestions: List<Question>
    private val random = Random()
    private var currentIndex = 0
    private var pst = 0
    internal lateinit var answer: String
    private val listChar = mutableListOf<IDButton>()

    fun loadQuestions() {
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

    fun makeQuestion() {
        val question = listQuestions[currentIndex]
        answer = question.context
        val inflater = LayoutInflater.from(activity)

        // Thêm các nút đáp án
        activity.uiManager.addAnswerButtons(answer.length, inflater)

        // Đặt hình ảnh cho câu hỏi
        activity.findViewById<ImageView>(R.id.img_picture).setImageResource(question.id)

        // Thêm các nút ký tự
        activity.uiManager.addCharButtons(answer, inflater, random)
    }

    fun addChar(
        id: Int,
        char: String,
    ) {
        // Duyệt qua các ô đáp án để tìm vị trí trống đầu tiên
        for (i in 16 until 16 + answer.length) {
            val answerButton = activity.findViewById<Button>(i)
            if (answerButton.text.isEmpty()) {
                listChar.add(IDButton(id, i))
                answerButton.text = char
                pst++
                if (pst == answer.length) {
                    gameHandler.checkAnswer()
                }
                return
            }
        }
    }

    fun handleAnswerClick(view: Button) {
        if (view.text.isNotEmpty()) {
            view.text = ""
            listChar.indexOfFirst { it.idAnswer == view.id }.takeIf { it != -1 }?.let { index ->
                val charButton = activity.findViewById<Button>(listChar[index].idPick)
                charButton.visibility = View.VISIBLE
                listChar.removeAt(index)
            }
            pst--
            activity.uiManager.resetAnswerBackground(answer.length)
        }
    }

    fun handleCharClick(view: Button) {
        if (pst < answer.length) {
            addChar(view.id, view.text.toString())
            view.visibility = View.INVISIBLE
        }
    }

    fun checkAnswer(): Boolean {
        val currentAnswer =
            buildString {
                for (i in 16 until answer.length + 16) {
                    append(activity.findViewById<Button>(i).text)
                }
            }
        return currentAnswer.equals(answer, ignoreCase = true)
    }

    fun isLastQuestion(): Boolean = currentIndex >= listQuestions.size - 1

    fun newQuestion() {
        if (currentIndex < listQuestions.size - 1) {
            listChar.clear()
            activity.uiManager.resetForNewQuestion()
            pst = 0
            currentIndex++
            makeQuestion()
        }
    }

    fun restartGame() {
        listChar.clear()
        activity.uiManager.resetForNewQuestion()
        pst = 0
        currentIndex = 0
        activity.uiManager.txtHeart.text = "5"
        activity.uiManager.txtPoint.text = "0"
        makeQuestion()
    }
}
