package ua.sytor.wifipass;

import com.google.gson.annotations.SerializedName;

public class NetworkObj {

    @SerializedName("ssid") public String ssid;
    @SerializedName("psk") public String psk;
    @SerializedName("key_mgmt") public String key_mgmt;
    @SerializedName("priority") public int priority;

}
