package co.id.android.secapafutsal;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class menuUtama extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowHomeEnabled(true);

        /*
        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(true);
        ab.setTitle("SECAPA FUTSAL");
        ab.setHomeButtonEnabled(true);
        ab.setIcon(R.drawable.ic_back);
        */

        setContentView(R.layout.activity_menu_utama);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbarmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.akun)
        {
            startActivity(new Intent(menuUtama.this, akun.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
