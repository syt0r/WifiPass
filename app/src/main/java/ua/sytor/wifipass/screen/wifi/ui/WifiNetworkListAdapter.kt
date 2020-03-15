package ua.sytor.wifipass.screen.wifi.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_wifi_item.view.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import ua.sytor.wifipass.R
import ua.sytor.wifipass.core.parser.WifiNetworkData

class WifiNetworkListAdapter : RecyclerView.Adapter<WifiNetworkListAdapter.WifiItemViewHolder>() {

	private val popupItemClickChannel = Channel<Pair<Int, WifiNetworkData>>()

	private val wifiNetworksList = ArrayList<WifiNetworkData>()

	fun subscribeOnItemSelected(): Flow<Pair<Int, WifiNetworkData>> =
		popupItemClickChannel.consumeAsFlow()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiItemViewHolder {
		return WifiItemViewHolder(
			LayoutInflater.from(parent.context)
				.inflate(R.layout.adapter_wifi_item, parent, false)
		)
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
				val popupMenu = PopupMenu(itemView.context, it)
				popupMenu.inflate(R.menu.popup)
				popupMenu.setOnMenuItemClickListener { menuItem ->
					popupItemClickChannel.offer(
						menuItem.itemId to wifiNetworksList[adapterPosition]
					)
					false
				}
				popupMenu.show()
			}
		}

		fun bind() {
			val (ssid, psk, keyManagementType) = wifiNetworksList[adapterPosition]
			itemView.ssid.text = ssid
			itemView.security.text = keyManagementType
			itemView.pass.text = psk
		}

	}

}
