package ua.sytor.wifipass;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {

    LinearLayout menuLayout, passLayout;
    EditText editText;

    boolean isButtonPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        menuLayout = (LinearLayout)findViewById(R.id.menu);
        passLayout = (LinearLayout)findViewById(R.id.pass);
        editText = (EditText)findViewById(R.id.editText);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                if(isButtonPressed){
                    passLayout.setVisibility(View.GONE);
                    menuLayout.setVisibility(View.VISIBLE);
                    isButtonPressed = false;
                }
                else finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(isButtonPressed){
            passLayout.setVisibility(View.GONE);
            menuLayout.setVisibility(View.VISIBLE);
            isButtonPressed = false;
        } else super.onBackPressed();
    }

    public void Click(View v){
        switch (v.getId()){
            case R.id.new_pass:
                menuLayout.setVisibility(View.GONE);
                passLayout.setVisibility(View.VISIBLE);
                isButtonPressed = true;
                break;
            case R.id.disable_pass:
                DisablePass();
                break;
            case R.id.accept:
                NewPass();
        }
    }

    void NewPass(){
        if(editText.getText().toString().length()==4){
            SharedPreferences sharedPref = getSharedPreferences("settings",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("id", editText.getText().toString());
            editor.apply();
            finish();
            Toast.makeText(this, R.string.pass_enabled, Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, R.string.enter_4_nums, Toast.LENGTH_SHORT).show();
    }

    void DisablePass(){
        SharedPreferences sharedPref = getSharedPreferences("settings",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("id", "0");
        editor.apply();
        Toast.makeText(this, R.string.pass_disabled , Toast.LENGTH_SHORT).show();
        finish();
    }

}
