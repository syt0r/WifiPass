package ua.sytor.wifipass;

import android.content.Context;

/**
 * Created by sytor on 16.07.16.
 */
public class Parser {

    Context context;
    String input_data;

    public Parser(Context context, String input_data){
        this.context = context;
        this.input_data = input_data;
    }

    public String getSsid(){
        int indexOfSsid = input_data.indexOf("ssid=\"");
        if (indexOfSsid == -1) return context.getString(R.string.unknown);
        return input_data.substring(input_data.indexOf("ssid=\"") + 6, input_data.indexOf("\n") - 1);
    }

    public int getPriority(){
        try{
            int indexOfPrior = input_data.indexOf("priority=");
            return Integer.parseInt(input_data.substring(indexOfPrior + 9, input_data.indexOf("\n", indexOfPrior)));
        }catch (Exception e){
            return -1;
        }
    }

    public String getKeyMgmt(){
        int indexOfkeymgmt = input_data.indexOf("key_mgmt=");
        return indexOfkeymgmt!=-1 ?
                input_data.substring(indexOfkeymgmt + 9, input_data.indexOf("\n", indexOfkeymgmt)) :
                context.getString(R.string.unknown);
    }

    public String getPsk(String key_mgmt){
        if (key_mgmt.equals("NONE") || key_mgmt.equals(context.getString(R.string.unknown)))
            return "None";
        int indexOfPass = input_data.indexOf("psk=\"");
        if(indexOfPass == -1) return context.getString(R.string.encrypted);
        return input_data.substring(indexOfPass+5,input_data.indexOf("\n",indexOfPass)-1);

    }

    public NetworkObj getNetwork(){
        NetworkObj networkObj = new NetworkObj();
        networkObj.ssid = getSsid();
        networkObj.key_mgmt = getKeyMgmt();
        networkObj.psk = getPsk(networkObj.key_mgmt);
        networkObj.priority = getPriority();
        return networkObj;
    }

}
