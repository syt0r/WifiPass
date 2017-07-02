package ua.sytor.wifipass;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class AboutActivity extends AppCompatActivity implements BillingProcessor.IBillingHandler{

    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bp = new BillingProcessor(this, getString(R.string.license_key), this);
        boolean isAvailable = BillingProcessor.isIabServiceAvailable(this);
        if(!isAvailable) {
            findViewById(R.id.donate_small).setVisibility(View.INVISIBLE);
            findViewById(R.id.donate_medium).setVisibility(View.INVISIBLE);
            findViewById(R.id.donate_large).setVisibility(View.INVISIBLE);
            Toast.makeText(this,"To donate please update Google Play Services",Toast.LENGTH_SHORT).show();
        }
    }

    public void Donate(View v){
        switch (v.getId()){
            case R.id.donate_small:
                bp.purchase(this,"small");
                break;
            case R.id.donate_medium:
                bp.purchase(this,"medium");
                break;
            case R.id.donate_large:
                bp.purchase(this,"large");
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        if (bp != null)
            bp.release();

        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Log.wtf("purchase","purchased "+productId);
        Toast.makeText(this,R.string.thanks,Toast.LENGTH_LONG).show();
        bp.consumePurchase(productId);
    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Log.wtf("purchase","error "+errorCode);
        Toast.makeText(this,"Purchase error: " + errorCode,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBillingInitialized() {

    }
}
