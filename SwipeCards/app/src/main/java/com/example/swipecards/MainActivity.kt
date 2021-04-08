package com.example.swipecards

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var motionLayout: MotionLayout
    private lateinit var recyclerView: RecyclerView

    private lateinit var viewClickTargetBottom: View
    private lateinit var viewClickTargetTop: View
    private lateinit var background: View
    private val touchRect: Rect = Rect()
    private val location = IntArray(2)

    //private val cardsColors = arrayOf(Color.YELLOW); //с одной картой
    private val cardsColors = arrayOf(Color.YELLOW, Color.BLUE, Color.CYAN, Color.GRAY, Color.MAGENTA);//с двумя картами

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start)
        initViews()
        initSetTransitionListener()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        background = findViewById(R.id.background_rv_click)
        viewClickTargetBottom = findViewById(R.id.viewClickTargetBottom)
        viewClickTargetTop = findViewById(R.id.viewClickTargetTop)
        motionLayout = findViewById(R.id.swipe_cards_scene)
        initRvItemClick()
        initCardColors(cardsColors)
    }

    private fun initRvItemClick() {
        recyclerView.adapter = AdapterN(cardsColors) { color: Int, position: Int ->
            motionLayout.transitionToStart()
            background.setBackgroundColor(color)
        }
    }

    private fun initCardColors(cardsColors: Array<Int>) {
        val cardTop: View = findViewById(R.id.imageViewTop)
        val cardCenter: View = findViewById(R.id.imageViewCenter)
        val cardBottom: View = findViewById(R.id.imageViewBottom)
        if (cardsColors.size == 1) {
            motionLayout.setTransition(R.id.one_card_start, R.id.one_card_end)
            background.setBackgroundColor(cardsColors[0])
        } else {
            cardTop.backgroundTintList = ColorStateList.valueOf(cardsColors[0])
            cardCenter.backgroundTintList = ColorStateList.valueOf(cardsColors[1])
            cardBottom.backgroundTintList = ColorStateList.valueOf(cardsColors[2])
        }
    }

    private fun initSetTransitionListener() {
        motionLayout.setTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout, currentId: Int) {
                recyclerView.scrollToPosition(0)
            }
        })
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (cardsColors.size != 1) {
            val bottomClick = isViewInBounds(viewClickTargetBottom, ev.x.toInt(), ev.y.toInt())
            val topClick = isViewInBounds(viewClickTargetTop, ev.x.toInt(), ev.y.toInt())
            if (bottomClick || topClick) {
                when (ev.action) {
                    MotionEvent.ACTION_UP -> {
                        if (bottomClick) {
                            motionLayout.transitionToEnd()
                        } else if (topClick) {
                            motionLayout.transitionToStart()
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    private fun isViewInBounds(view: View, x: Int, y: Int): Boolean {
        view.getDrawingRect(touchRect)
        view.getLocationOnScreen(location)
        touchRect.offset(location[0], location[1])
        return touchRect.contains(x, y)
    }
}