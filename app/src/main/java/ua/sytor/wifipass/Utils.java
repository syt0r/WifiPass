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

    public static String execSUCommand(String command) throws IOException {
        Process p = Runtime.getRuntime().exec(new String[]{"su","-c",command});
        InputStream stdout = p.getInputStream();
        int BUFF_LEN = 64;
        byte[] buffer = new byte[BUFF_LEN];
        int read;
        StringBuilder out = new StringBuilder();
        while(true){
            read = stdout.read(buffer);
            out.append(new String(buffer, 0, read));
            if(read<BUFF_LEN)
                break;
        }
        return out.toString();
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

        NetworkObj networkObj;
        for (int i = 0; i < raw_list.size(); i++){
            networkObj = new Parser(context, raw_list.get(i)).getNetwork();
            if (networkObj.key_mgmt.equals("NONE") ||
                    networkObj.key_mgmt.equals(context.getString(R.string.unknown))) continue;
            list.add(networkObj);
        }
    }

    public static boolean checkConfigFile(){
        try {
            String ls = execSUCommand("su -c 'ls /data/misc/wifi'");
            Log.wtf("123","checkfile ls:" + ls);
            String[] arr = ls.split("\\s");
            for (String str : arr){
                Log.wtf("str ",str);
                if(str.equals("wpa_supplicant.conf"))
                    return true;
            }
        } catch (IOException e) {
            Log.wtf("123","checkfile catch");
            e.printStackTrace();
        }
        return false;
    }
}
