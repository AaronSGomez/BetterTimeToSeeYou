package com.example.bettertimetoseeyou

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bettertimetoseeyou.model.HourWeather

class HourWeatherAdapter (
    private val items: MutableList<HourWeather> = mutableListOf()

) : RecyclerView.Adapter<HourWeatherAdapter.VH>() {
    class  VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
        val tvHour: TextView = itemView.findViewById(R.id.tvHour)
        val tvTemp: TextView= itemView.findViewById(R.id.tvTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH{
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                resource = R.layout.activity_hour,
                root = parent,
                attachToRoot = false
            )
         return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.ivIcon.setImageResource(item.iconRes)
        holder.tvHour.text = item.hour
        holder.tvTemp.text = "${item.tempC}ºC"

}