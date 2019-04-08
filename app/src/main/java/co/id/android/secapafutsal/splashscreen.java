package co.id.android.secapafutsal;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        setContentView(R.layout.activity_splashscreen);

        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        int warna = 0xFFFFFFFF;
        pb.getIndeterminateDrawable().setColorFilter(warna, PorterDuff.Mode.SRC_IN);
        pb.getProgressDrawable().setColorFilter(warna, PorterDuff.Mode.SRC_IN);

        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(splashscreen.this, Login.class));
                    finish();
                }
            }
        };
        thread.start();
    }
}
