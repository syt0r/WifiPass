package ua.sytor.wifipass.screen.wifi.ui

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import ua.sytor.wifipass.R
import ua.sytor.wifipass.model.WifiNetworkInfo

class WifiNetworkListAdapter : RecyclerView.Adapter<ViewHolder>() {

    private val wifiNetworksList = ArrayList<WifiNetworkInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.wifi_adapter_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val (ssid, psk, keyManagementType) = wifiNetworksList[position]
        holder.ssidView.text = ssid
        holder.securityTypeView.text = keyManagementType
        holder.passwordView.text = psk

        //        final WifiNetworkInfo obj = wifiNetworksList.get(position);
        //        holder.ssid.setText(obj.ssid);
        //        holder.security.setText(obj.key_mgmt);
        //        if (obj.key_mgmt.equals("NONE"))
        //            holder.linearLayout.setVisibility(View.GONE);
        //        else holder.password.setText(obj.psk);
        //        holder.imageButton.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                PopupMenu popupMenu = new PopupMenu(context,v);
        //                popupMenu.inflate(R.menu.popup);
        //                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
        //                    @Override
        //                    public boolean onMenuItemClick(MenuItem item) {
        //                        switch (item.getItemId()){
        //                            case R.id.copy:
        //                                ClipboardManager manager = (ClipboardManager)
        //                                        context.getSystemService(Context.CLIPBOARD_SERVICE);
        //                                ClipData clipData = ClipData.newPlainText("pass",obj.psk);
        //                                manager.setPrimaryClip(clipData);
        //                                Toast.makeText(context,R.string.copied,Toast.LENGTH_SHORT).show();
        //                                break;
        //                            case R.id.share:
        //                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        //                                sharingIntent.setType("text/plain");
        //                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, obj.psk);
        //                                context.startActivity(Intent.createChooser(sharingIntent,
        //                                        context.getString(R.string.shareUsing)));
        //                                break;
        //                        }
        //                        return false;
        //                    }
        //                });
        //                popupMenu.show();
        //            }
        //        });


    }

    override fun getItemCount(): Int {
        return wifiNetworksList.size
    }

    fun setData(data: List<WifiNetworkInfo>) {
        wifiNetworksList.clear()
        wifiNetworksList.addAll(data)
        notifyDataSetChanged()
    }

}
