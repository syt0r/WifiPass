package ua.sytor.wifipass;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sytor on 5/12/16.
 */
public class Utils {

    public static String execCommand(String command, Process p) throws IOException {
        DataOutputStream stdin = new DataOutputStream(p.getOutputStream());
        stdin.writeBytes(command+"\n");
        InputStream stdout = p.getInputStream();
        int BUFF_LEN = 4096;
        byte[] buffer = new byte[BUFF_LEN];
        int read;
        String out = new String();
        while(true){
            read = stdout.read(buffer);
            out += new String(buffer, 0, read);
            if(read<BUFF_LEN)
                break;
        }
        return out;
    }

    public static void parseData(Context context, String data, List<NetworkObj> list){
        List<String> raw_list = new ArrayList<>();
        int index = 0;
        int first, last;
        while (data.indexOf("{",index)!=-1){
            first = data.indexOf("{",index);
            last = data.indexOf("}",first);
            raw_list.add(data.substring(first+2,last));
            index = last;
        }


        list.clear();

        String curr;
        NetworkObj obj;

        int indexOfkeymgmt,indexOfPass,indexOfPrior;
        /*
         * Old Version
         *
        for (int i = 0; i < raw_list.size(); i++){
            curr = raw_list.get(i);
            obj = new NetworkObj();

            indexOfkeymgmt = curr.indexOf("key_mgmt=");
            obj.key_mgmt = curr.substring(indexOfkeymgmt + 9, curr.indexOf("\n", indexOfkeymgmt));

            if (obj.key_mgmt.equals("NONE")) continue;
            else{
                indexOfPass = curr.indexOf("psk=");
                obj.psk = curr.substring(indexOfPass+5,curr.indexOf("\n",indexOfPass)-1);
            }

            obj.ssid = curr.substring(curr.indexOf("ssid=") + 6, curr.indexOf("\n") - 1);

            indexOfPrior = curr.indexOf("priority=");
            obj.priority = Integer.parseInt(curr.substring(indexOfPrior + 9, curr.indexOf("\n", indexOfPrior)));

            list.add(obj);
        }
        */
        NetworkObj networkObj;
        for (int i = 0; i < raw_list.size(); i++){
            networkObj = new Parser(context, raw_list.get(i)).getNetwork();
            if (networkObj.key_mgmt.equals("NONE") ||
                    networkObj.key_mgmt.equals(context.getString(R.string.unknown))) continue;
            list.add(networkObj);
        }
    }

    public static boolean checkFile(Process p){
        try {
            String ls = execCommand("ls /data/misc/wifi",p);
            String[] arr = ls.split("\\s");
            Log.wtf("my",ls);
            for (String str : arr){
                Log.wtf("str ",str);
                if(str.equals("wpa_supplicant.conf"))
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String toJson(List<NetworkObj> list){
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    public static List<NetworkObj> fromJson(String json){
        Gson gson = new Gson();
        Type listType = new TypeToken<List<NetworkObj>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    public static void createFile(List<NetworkObj> list) throws IOException {
        Log.wtf("lolol", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "wifi_pass.txt");
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"wifi_pass.txt");
        if(file.exists()) file.delete();
        if(file.createNewFile() && file.exists()){
            OutputStream fo = new FileOutputStream(file);
            fo.write(Utils.toJson(list).getBytes());
            fo.close();
        }
    }

}
