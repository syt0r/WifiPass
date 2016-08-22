package ua.sytor.wifipass;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder{

    public TextView ssid;
    public TextView security;
    public TextView password;
    public LinearLayout linearLayout;
    public ImageButton imageButton;

    public ViewHolder(View itemView) {
        super(itemView);
        ssid = (TextView)itemView.findViewById(R.id.ssid);
        security = (TextView)itemView.findViewById(R.id.security);
        password = (TextView)itemView.findViewById(R.id.pass);
        linearLayout = (LinearLayout)itemView.findViewById(R.id.linearLayout);
        imageButton = (ImageButton)itemView.findViewById(R.id.dropdown);
    }
}