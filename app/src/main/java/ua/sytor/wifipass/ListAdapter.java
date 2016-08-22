package ua.sytor.wifipass;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ViewHolder> {

    Context context;
    List<NetworkObj> list;

    public ListAdapter(Context context, List<NetworkObj> list){
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final NetworkObj obj = list.get(position);
        holder.ssid.setText(obj.ssid);
        holder.security.setText(obj.key_mgmt);
        if (obj.key_mgmt.equals("NONE"))
            holder.linearLayout.setVisibility(View.GONE);
        else holder.password.setText(obj.psk);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context,v);
                popupMenu.inflate(R.menu.popup);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.copy:
                                ClipboardManager manager = (ClipboardManager)
                                        context.getSystemService(Context.CLIPBOARD_SERVICE);
                                ClipData clipData = ClipData.newPlainText("pass",obj.psk);
                                manager.setPrimaryClip(clipData);
                                Toast.makeText(context,R.string.copied,Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.share:
                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, obj.psk);
                                context.startActivity(Intent.createChooser(sharingIntent,
                                        context.getString(R.string.shareUsing)));
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
