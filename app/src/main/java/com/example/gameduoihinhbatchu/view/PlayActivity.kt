package com.example.gameduoihinhbatchu.view

import android.app.Activity
import android.os.Bundle
import com.example.gameduoihinhbatchu.R
import com.example.gameduoihinhbatchu.viewmodel.GameHandler
import com.example.gameduoihinhbatchu.viewmodel.QuestionManager
import com.example.gameduoihinhbatchu.viewmodel.UIManager

class PlayActivity : Activity() {
    lateinit var gameHandler: GameHandler
    lateinit var questionManager: QuestionManager
    lateinit var uiManager: UIManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        gameHandler = GameHandler(this)
        questionManager = QuestionManager(this, gameHandler)
        uiManager = UIManager(this, questionManager)

        uiManager.initComponents()
        questionManager.loadQuestions()
        questionManager.makeQuestion()
    }

    override fun onDestroy() {
        gameHandler.cleanUp()
        super.onDestroy()
    }
}
