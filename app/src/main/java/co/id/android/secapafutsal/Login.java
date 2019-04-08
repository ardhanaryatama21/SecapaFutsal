package co.id.android.secapafutsal;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    static final int GOOGLE_SIGN = 123;
    FirebaseAuth mAuth;
    Button btnLogin, btnMasuk, btnKeluar;
    TextView txtSelamatDatang,txtSilahkan;
    GoogleSignInClient mGoogleSignInClient;
    ProgressBar progressBar2;
    TextView text;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        ab.hide();

        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnMasuk = findViewById(R.id.btnMasuk);
        btnKeluar = findViewById(R.id.btnKeluar);
        txtSelamatDatang = findViewById(R.id.txtSelamatDatang);
        txtSilahkan = findViewById(R.id.txtSilahkan);
        progressBar2 = findViewById(R.id.progressBar2);
        text = findViewById(R.id.textView);
        image = findViewById(R.id.imageView2);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

            mGoogleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
            btnLogin.setOnClickListener(v -> SignInGoogle());
            if(mAuth.getCurrentUser() != null){
                FirebaseUser user = mAuth.getCurrentUser();
                startActivity(new Intent(Login.this, menuUtama.class));
                finish();
            }
    }
    void SignInGoogle(){
        Intent signIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent, GOOGLE_SIGN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn
                    .getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null)firebaseAuthWithGoogle(account);
            }catch (ApiException e){
                e.printStackTrace();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d("TAG","firebaseAutaWithGoogle: " + account.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task-> {
                if(task.isSuccessful()){
                    progressBar2.setVisibility(View.VISIBLE);
                    Log.d("TAG","Login Sukses");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);

                }else{
                    progressBar2.setVisibility(View.INVISIBLE);
                    Log.w("TAG","Login Gagal", task.getException());
                    Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show();
                    updateUI(null);

                }
        });

    }

    private void updateUI(FirebaseUser user) {
        if(user !=  null){
            startActivity(new Intent(Login.this, menuUtama.class));

            String name = user.getDisplayName();
            String email = user.getEmail();
            String photo =  String.valueOf(user.getPhotoUrl());

            text.append("Info : \n");
            text.append(name + "\n");
            text.append(email);

            Picasso.get().load(photo).into(image);
            finish();
        }
    }

}
