package com.example.swipecards

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var motionLayout: MotionLayout
    private lateinit var recyclerView: RecyclerView

    private lateinit var background: View

    private val cardsColors = arrayOf(
        R.drawable.sberpay_payment_screen_background_2,
        R.drawable.sberpay_payment_screen_background_3,
        R.drawable.sberpay_payment_screen_background_4,
        R.drawable.sberpay_payment_screen_background_5,
        R.drawable.sberpay_payment_screen_background_6,
        R.drawable.sberpay_payment_screen_background_7,
        R.drawable.sberpay_payment_screen_background_8,
        R.drawable.sberpay_payment_screen_background_9,
        R.drawable.sberpay_payment_screen_background_10,
        R.drawable.sberpay_payment_screen_background_11,
        R.drawable.sberpay_payment_screen_background_12,
        R.drawable.sberpay_payment_screen_background_13,
        R.drawable.sberpay_payment_screen_background_14,
        R.drawable.sberpay_payment_screen_background_15
    );//с двумя картами

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)
        initViews()
        initSetTransitionListener()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        background = findViewById(R.id.background_rv_click)
        motionLayout = findViewById(R.id.swipe_cards_scene)
        initRvItemClick()
        initCardColors(cardsColors)
    }

    private fun initRvItemClick() {
        recyclerView.adapter = AdapterN(cardsColors) { color: Int, position: Int ->
            motionLayout.transitionToStart()
            background.background = (ContextCompat.getDrawable(this, color))
        }
    }

    private fun initCardColors(cardsColors: Array<Int>) {
        if (cardsColors.size == 1) {
            motionLayout.setTransition(R.id.one_card_start, R.id.one_card_end)
            background.setBackgroundColor(cardsColors[0])
        } else {
            val cardTop: ConstraintLayout = findViewById<View>(R.id.imageViewTop).findViewById(R.id.front_card_content)
            val cardCenter: ConstraintLayout = findViewById<View>(R.id.imageViewCenter).findViewById(R.id.front_card_content)
            val cardBottom: ConstraintLayout = findViewById<View>(R.id.imageViewBottom).findViewById(R.id.front_card_content)
            cardTop.background = ContextCompat.getDrawable(this, cardsColors[0])
            cardCenter.background = ContextCompat.getDrawable(this, cardsColors[1])
            cardBottom.background = ContextCompat.getDrawable(this, cardsColors[2])
        }
    }

    private fun initSetTransitionListener() {
        motionLayout.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                recyclerView.scrollToPosition(0)
            }
        })
    }
}