package com.jayway.techtest.wetweather.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jayway.techtest.wetweather.R
import com.jayway.techtest.wetweather.models.WeatherData
import com.jayway.techtest.wetweather.models.WeatherHistory
import com.jayway.techtest.wetweather.util.convertToDateTime
import com.jayway.techtest.wetweather.util.getProgresDrawable
import com.jayway.techtest.wetweather.util.loadIcon
import kotlinx.android.synthetic.main.weather_item.view.*


class WeatherListAdapter:RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val items = mutableListOf<WeatherData>()


    fun refreshData(items:List<WeatherData>){
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
         return WeatherHistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.weather_item,parent,false))
    }

    override fun getItemCount(): Int {
     Log.d("Size",items.size.toString())
     return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is WeatherHistoryViewHolder->holder.bindData(items[position])
        }
    }


    inner class WeatherHistoryViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        private val date = itemView.dateTextView
        private val weatherStatus = itemView.weatherStatusTV
        private val weatherStatusImage = itemView.weatherStatusImageView

        fun bindData(item:WeatherData?){
            date.text = item?.dt?.convertToDateTime()
            weatherStatus.text = item?.weather?.get(0)?.main
            weatherStatusImage.loadIcon(item?.weather?.get(0)?.icon, getProgresDrawable(itemView.context))
        }

    }
}