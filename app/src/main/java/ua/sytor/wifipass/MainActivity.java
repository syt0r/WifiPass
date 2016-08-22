package ua.sytor.wifipass;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int PICKFILE_RESULT_CODE = 878;

    boolean rawDataAcces = false;

    LinearLayout processingLayout;
    LinearLayout errorLayout;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ListAdapter adapter;

    String rawData;
    List<NetworkObj> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        processingLayout = (LinearLayout)findViewById(R.id.processing);
        errorLayout = (LinearLayout)findViewById(R.id.error);
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        list = new ArrayList<>();

        CheckPassDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.backup:
                BackupDialog();
                break;
            case R.id.key:
                startActivity(new Intent(this, PasswordActivity.class));
                break;
            case R.id.raw:
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(this);
                builder.setTitle("wpa_supplicant.conf");
                builder.setMessage(rawData);
                builder.show();
                break;
            case R.id.about:
                startActivity(new Intent(this,AboutActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        (menu.getItem(0)).setEnabled(rawDataAcces);
        return super.onPrepareOptionsMenu(menu);
    }

    void CheckPassDialog(){
        SharedPreferences sharedPreferences = getSharedPreferences("settings",MODE_PRIVATE);
        final String id = sharedPreferences.getString("id","0");
        if(!id.equals("0")){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(this);
            builder.setTitle(R.string.password);
            final View v = LayoutInflater.from(this).inflate(R.layout.dialog_key,null);
            builder.setView(v);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (!id.equals(((EditText) v.findViewById(R.id.editText)).getText().toString())){
                        finish();
                    }
                    else new Processing().execute();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        }else new Processing().execute();
    }

    void RecyclerInit(){
        adapter = new ListAdapter(this,list);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    void Fade(View fadeIn, final View fadeout){
        int duration = 1000;

        fadeIn.setAlpha(0f);
        fadeIn.setVisibility(View.VISIBLE);

        fadeIn.animate()
                .alpha(1f)
                .setDuration(duration)
                .setListener(null);

        fadeout.animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fadeout.setVisibility(View.GONE);
                    }
                });

    }

    void BackupDialog(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setTitle(R.string.backup_manager);
        builder.setPositiveButton(R.string.backup, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(!list.isEmpty()){
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                            ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                        onRequestPermissionsResult(200,new String[]{""},new int[]{PackageManager.PERMISSION_GRANTED});
                    else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},200);

                }
            }
        });
        builder.setNegativeButton(R.string.restore, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent,PICKFILE_RESULT_CODE);
            }
        });
        builder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(list.isEmpty()) Toast.makeText(this, R.string.empty_list , Toast.LENGTH_SHORT).show();
        else if(requestCode==200 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            try {
                Utils.createFile(list);
                Toast.makeText(this, R.string.on_backup_create, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class Processing extends AsyncTask{

        private static final int SUCCESS = 509;
        private static final int NO_FILE = 941;
        private static final int NO_ROOT = 963;
        private static final int UNKNOWN = 978;

        private static final String root_message = "java.lang.StringIndexOutOfBoundsException: length=4096; regionStart=0; regionLength=-1";

        int status;
        String error;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            recyclerView.setVisibility(View.GONE);
            errorLayout.setVisibility(View.GONE);
            processingLayout.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            status = SUCCESS;
            try{
                Process p = Runtime.getRuntime().exec(new String[]{"su"});
                if (Utils.checkFile(p)){
                    String data = Utils.execCommand("cat /data/misc/wifi/wpa_supplicant.conf",p);
                    rawData = data;
                    Utils.parseData(MainActivity.this, data, list);
                }
                else
                    status = NO_FILE;
            }catch (Exception e){
                if(e.toString().contains(root_message))
                    status = NO_ROOT;
                else status = UNKNOWN;
                error = e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            switch (status){
                case SUCCESS:
                    RecyclerInit();
                    Fade(recyclerView,processingLayout);
                    rawDataAcces = true;
                    invalidateOptionsMenu();
                    break;
                case NO_FILE:
                    //TODO choose file
                    Fade(errorLayout,processingLayout);
                    ((TextView)errorLayout.findViewById(R.id.textView2)).setText(R.string.file_not_found);
                    break;
                case NO_ROOT:
                    Fade(errorLayout,processingLayout);
                    ((TextView)errorLayout.findViewById(R.id.textView2)).setText(R.string.no_root);
                    break;
                case UNKNOWN:
                    Fade(errorLayout,processingLayout);
                    ((TextView)errorLayout.findViewById(R.id.textView2))
                            .setText(getString(R.string.error)+"\n"+error);
                    break;
            }
        }
    }

        /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK){
            String filePath = data.getData().getPath();
            try {
                List<NetworkObj> sList = Utils.fromJson(Utils.readFile(filePath,p));
                NetworkObj obj;
                for (int i = 0; i<sList.size(); i++){
                    obj = sList.get(i);
                    WifiConfiguration wifi = new WifiConfiguration();
                    wifi.SSID = obj.ssid;
                    int bitSet = 0;
                    if(obj.key_mgmt=="WEP") {
                        bitSet = WifiConfiguration.KeyMgmt.NONE;
                        wifi.wepKeys[0] = obj.psk;
                    }
                    if(obj.key_mgmt=="WPA_PSK") {
                        bitSet = WifiConfiguration.KeyMgmt.WPA_PSK;
                        wifi.preSharedKey = obj.psk;
                    }
                    wifi.allowedKeyManagement.set(bitSet);
                    ((WifiManager)getSystemService(WIFI_SERVICE)).addNetwork(wifi);
                    Log.wtf("",obj.ssid);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    */
}
