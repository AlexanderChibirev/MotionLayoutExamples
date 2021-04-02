package com.example.swipecards

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class AdapterN(
    private val cardsColors: Array<Int>,
    private val onItemSelected: (color: Int, position: Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false))
    }

    override fun getItemCount(): Int {
        return cardsColors.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.card_background)
        val color = cardsColors[position]
        holder.itemView.backgroundTintList = ColorStateList.valueOf(color)
        holder.itemView.setOnClickListener {
            onItemSelected.invoke(color, position)
        }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}