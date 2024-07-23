package com.example.uihnhbtch.viewmodel

import android.os.Handler
import android.os.HandlerThread
import android.widget.TextView
import android.widget.Toast
import com.example.uihnhbtch.R
import com.example.uihnhbtch.view.PlayActivity

class GameHandler(
    private val activity: PlayActivity,
) {
    private val CHECK_ANSWER = 0
    private val GAME_OVER = 1
    private var heart = 5
    private var point = 0
    private lateinit var handlerThread: HandlerThread
    private lateinit var handler: Handler

    init {
        handlerThread = HandlerThread("GameHandlerThread")
        handlerThread.start()
        handler =
            Handler(
                handlerThread.looper,
                Handler.Callback { msg ->
                    when (msg.what) {
                        CHECK_ANSWER -> {
                            if (activity.questionManager.checkAnswer()) {
                                activity.runOnUiThread {
                                    Toast.makeText(activity, "Đẳng cấp đấy !", Toast.LENGTH_SHORT).show()
                                    point += 100
                                    activity.findViewById<TextView>(R.id.txt_point).text = point.toString()
                                    activity.uiManager.setAnswerResult(true)
                                }
                            } else {
                                heart--
                                activity.runOnUiThread {
                                    activity.findViewById<TextView>(R.id.txt_heart).text = heart.toString()
                                    if (heart <= 0) {
                                        handler.sendEmptyMessage(GAME_OVER)
                                        return@runOnUiThread
                                    }
                                    Toast.makeText(activity, "Sai rồi bro !", Toast.LENGTH_SHORT).show()
                                    activity.uiManager.setAnswerResult(false)
                                }
                            }
                        }
                        GAME_OVER -> {
                            activity.runOnUiThread {
                                Toast.makeText(activity, "GAME OVER", Toast.LENGTH_SHORT).show()
                                activity.finish()
                            }
                        }
                    }
                    true
                },
            )
    }

    fun checkAnswer() {
        handler.sendEmptyMessage(CHECK_ANSWER)
    }

    fun gameOver() {
        handler.sendEmptyMessage(GAME_OVER)
    }

    fun cleanUp() {
        handlerThread.quitSafely()
    }
}
