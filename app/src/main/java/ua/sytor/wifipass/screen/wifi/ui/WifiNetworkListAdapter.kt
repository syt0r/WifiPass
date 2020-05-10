package ua.sytor.wifipass.screen.wifi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_wifi_item.view.*
import ua.sytor.wifipass.R
import ua.sytor.wifipass.core.parser.entity.WifiNetworkData

class WifiNetworkListAdapter : RecyclerView.Adapter<WifiNetworkListAdapter.WifiItemViewHolder>() {

	private val wifiNetworksList = ArrayList<WifiNetworkData>()

	var onDropdownButtonClickListener: ((view: View, wifi: WifiNetworkData) -> Unit)? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiItemViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.adapter_wifi_item, parent, false)
		return WifiItemViewHolder(view)
	}

	override fun onBindViewHolder(holder: WifiItemViewHolder, position: Int) {
		holder.bind()
	}

	override fun getItemCount(): Int {
		return wifiNetworksList.size
	}

	fun setData(data: List<WifiNetworkData>) {
		wifiNetworksList.clear()
		wifiNetworksList.addAll(data)
		notifyDataSetChanged()
	}

	inner class WifiItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		init {
			itemView.dropdown.setOnClickListener {
				onDropdownButtonClickListener?.invoke(
					it,
					wifiNetworksList[adapterPosition]
				)
			}
		}

		fun bind() {
			val (ssid, password) = wifiNetworksList[adapterPosition]
			itemView.ssid.text = ssid
			itemView.pass.text = password
		}

	}

}
