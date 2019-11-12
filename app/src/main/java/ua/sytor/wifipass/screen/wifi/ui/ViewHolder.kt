package ua.sytor.wifipass.screen.wifi.ui

import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import ua.sytor.wifipass.R

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val ssidView: TextView = itemView.findViewById(R.id.ssid)
    val securityTypeView: TextView = itemView.findViewById(R.id.security)
    val passwordView: TextView = itemView.findViewById(R.id.pass)

    val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)
    val imageButton: ImageButton = itemView.findViewById(R.id.dropdown)

}