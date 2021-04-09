package com.example.swipecards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class AdapterN(
    private val cardsColors: Array<Int>,
    private val onItemSelected: (color: Int, position: Int) -> Unit
) : RecyclerView.Adapter<AdapterN.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.sber_pay_payment_card_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cardsColors.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val color = cardsColors[position]
        holder.itemView.setOnClickListener {
            onItemSelected.invoke(color, position)
        }
        holder.bind(cardsColors[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val container: ConstraintLayout = itemView.findViewById(R.id.front_card_content)

        internal fun bind(color: Int) {
            val context = itemView.context
            container.background = ContextCompat.getDrawable(context, color)
        }
    }
}
