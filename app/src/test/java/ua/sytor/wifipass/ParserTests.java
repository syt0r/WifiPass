package ua.sytor.wifipass;

import org.junit.Test;

import java.util.List;

import ua.sytor.wifipass.model.WifiNetworkInfo;
import ua.sytor.wifipass.interactor.parser.WifiConfigStoreParser;
import ua.sytor.wifipass.interactor.parser.WpaSupplicantParser;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ParserTests {

    @Test
    public void wpaSupplicantParserTest() throws Exception {

        String data = "" +
                "network={\n" +
                "    ssid=\"example\"\n" +
                "    scan_ssid=1\n" +
                "    key_mgmt=WPA-EAP\n" +
                "    eap=PEAP\n" +
                "    identity=\"user@example.com\"\n" +
                "    password=\"foobar\"\n" +
                "    ca_cert=\"/etc/cert/ca.pem\"\n" +
                "    phase1=\"peaplabel=0\"\n" +
                "    phase2=\"auth=MSCHAPV2\"\n" +
                "}" +
                "" +
                "network={\n" +
                "    ssid=\"example2\"\n" +
                "    scan_ssid=1\n" +
                "    key_mgmt=WPA-EAP\n" +
                "    eap=PEAP\n" +
                "    identity=\"user@example.com\"\n" +
                "    password=\"foobar\"\n" +
                "    ca_cert=\"/etc/cert/ca.pem\"\n" +
                "    phase1=\"peaplabel=0\"\n" +
                "    phase2=\"auth=MSCHAPV2\"\n" +
                "}";

        WpaSupplicantParser parser = new WpaSupplicantParser();

        List<WifiNetworkInfo> list = parser.parseFileContent(data);

        assertEquals("example", list.get(0).getSsid());
        assertEquals("WPA-EAP", list.get(0).getKeyManagementType());

    }

    @Test
    public void wifiConfigStoreParserTest() {

        String data = "" +
                "<?xml version='1.0' encoding='utf-8' standalone='yes' ?>\n" +
                "<WifiConfigStoreData>\n" +
                "<int name=\"Version\" value=\"1\" />\n" +
                "<NetworkList>\n" +
                    "<Network>\n" +
                        "<WifiConfiguration>\n" +
                            "<string name=\"ConfigKey\">&quot;サトル&quot;-WPA_PSK</string>\n" +
                            "<string name=\"SSID\">&quot;サトル&quot;</string>\n" +
                            "<null name=\"BSSID\" />\n" +
                            "<boolean name=\"ShareThisAp\" value=\"false\" />\n" +
                            "<string name=\"PreSharedKey\">&quot;i9041974&quot;</string>\n" +
                            "<null name=\"WEPKeys\" />\n" +
                            "<int name=\"WEPTxKeyIndex\" value=\"0\" />\n" +
                            "<boolean name=\"HiddenSSID\" value=\"false\" />\n" +
                            "<boolean name=\"RequirePMF\" value=\"false\" />\n" +
                            "<byte-array name=\"AllowedKeyMgmt\" num=\"1\">02</byte-array>\n" +
                            "<byte-array name=\"AllowedProtocols\" num=\"1\">0b</byte-array>\n" +
                            "<byte-array name=\"AllowedAuthAlgos\" num=\"1\">01</byte-array>\n" +
                            "<byte-array name=\"AllowedGroupCiphers\" num=\"1\">0f</byte-array>\n" +
                            "<byte-array name=\"AllowedPairwiseCiphers\" num=\"1\">06</byte-array>\n" +
                            "<boolean name=\"Shared\" value=\"true\" />\n" +
                            "<int name=\"Status\" value=\"2\" />\n" +
                            "<null name=\"FQDN\" />\n" +
                            "<null name=\"ProviderFriendlyName\" />\n" +
                            "<null name=\"LinkedNetworksList\" />\n" +
                            "<null name=\"DefaultGwMacAddress\" />\n" +
                            "<boolean name=\"ValidatedInternetAccess\" value=\"true\" />\n" +
                            "<boolean name=\"NoInternetAccessExpected\" value=\"false\" />\n" +
                            "<int name=\"UserApproved\" value=\"0\" />\n" +
                            "<boolean name=\"MeteredHint\" value=\"false\" />\n" +
                            "<int name=\"MeteredOverride\" value=\"0\" />\n" +
                            "<boolean name=\"UseExternalScores\" value=\"false\" />\n" +
                            "<int name=\"NumAssociation\" value=\"1862\" />\n" +
                            "<int name=\"CreatorUid\" value=\"1000\" />\n" +
                            "<string name=\"CreatorName\">android.uid.system:1000</string>\n" +
                            "<string name=\"CreationTime\">time=05-17 21:33:23.829</string>\n" +
                            "<int name=\"LastUpdateUid\" value=\"1000\" />\n" +
                            "<string name=\"LastUpdateName\">android.uid.system:1000</string>\n" +
                            "<int name=\"LastConnectUid\" value=\"1000\" />\n" +
                            "<boolean name=\"IsLegacyPasspointConfig\" value=\"false\" />\n" +
                            "<long-array name=\"RoamingConsortiumOIs\" num=\"0\" />\n" +
                            "<string name=\"RandomizedMacAddress\">02:00:00:00:00:00</string>\n" +
                            "<null name=\"DppConnector\" />\n" +
                            "<null name=\"DppNetAccessKey\" />\n" +
                            "<int name=\"DppNetAccessKeyExpiry\" value=\"-1\" />\n" +
                            "<null name=\"DppCsign\" />\n" +
                        "</WifiConfiguration>\n" +
                        "<NetworkStatus>\n" +
                            "<string name=\"SelectionStatus\">NETWORK_SELECTION_ENABLED</string>\n" +
                            "<string name=\"DisableReason\">NETWORK_SELECTION_ENABLE</string>\n" +
                            "<null name=\"ConnectChoice\" />\n" +
                            "<long name=\"ConnectChoiceTimeStamp\" value=\"-1\" />\n" +
                            "<boolean name=\"HasEverConnected\" value=\"true\" />\n" +
                        "</NetworkStatus>\n" +
                        "<IpConfiguration>\n" +
                            "<string name=\"IpAssignment\">DHCP</string>\n" +
                            "<string name=\"ProxySettings\">NONE</string>\n" +
                        "</IpConfiguration>\n" +
                    "</Network>" +
                "</NetworkList>\n" +
                "</WifiConfigStoreData>\n";

        WifiConfigStoreParser parser = new WifiConfigStoreParser();
        List<WifiNetworkInfo> list = parser.parseFileContent(data);

        assertEquals("サトル", list.get(0).getSsid());
        assertEquals("i9041974", list.get(0).getPsk());

    }

}