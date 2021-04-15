package com.example.swipecards

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
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

    private lateinit var viewClickTargetBottom: View
    private lateinit var viewClickTargetTop: View
    private lateinit var background: View
    private val touchRect: Rect = Rect()
    private val location = IntArray(2)

    //private val cardsColors = arrayOf(Color.YELLOW); //с одной картой
    // private val cardsColors = arrayOf(Color.YELLOW, Color.BLUE, Color.CYAN, Color.GRAY, Color.MAGENTA);//с двумя картами
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
        viewClickTargetBottom = findViewById(R.id.viewClickTargetBottom)
        viewClickTargetTop = findViewById(R.id.viewClickTargetTop)
        motionLayout = findViewById(R.id.swipe_cards_scene)
        initRvItemClick()
        initCardColors(cardsColors)
    }

    private fun initRvItemClick() {
        recyclerView.adapter = AdapterN(cardsColors) { color: Int, position: Int ->
            if (motionLayout.progress == EXPANDED_RECYCLER_VIEW) {
                motionLayout.transitionToStart()
                background.background = (ContextCompat.getDrawable(this, color))
            }
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
    companion object {
        private const val MAX_SLIDE = 1f
        private const val EXPANDED_RECYCLER_VIEW = 1f
        private const val PERCENT_CARDS_OVERLAP = 2
    }
}