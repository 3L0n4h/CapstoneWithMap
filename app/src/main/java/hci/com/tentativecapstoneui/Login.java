package hci.com.tentativecapstoneui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {
    private EditText email,password;
    private Button login,register;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ProgressBar mProgress;
    private ProgressDialog loadingBar;
    private TextView forgotpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mProgress = (ProgressBar) findViewById(R.id.progressBar2);
        loadingBar = new ProgressDialog(this);
        forgotpass = (TextView)findViewById(R.id.txtforgotpass);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgotintent = new Intent(Login.this, PasswordActivity.class);
                startActivity(forgotintent);
            }
        });


        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.register);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                password.setTransformationMethod(new PasswordTransformationMethod());

                if(!TextUtils.isEmpty(userEmail)&&!TextUtils.isEmpty(userPassword)){
                    loadingBar.setTitle("Login");
                    loadingBar.setMessage("Connecting... ");
                    loadingBar.show();
                    loadingBar.setCanceledOnTouchOutside(true);
                    mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login Successful...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                checkUserExistence();
                            }else{
                                String message = task.getException().getMessage();
                                Toast.makeText(Login.this, "Error Occurred:  " + message, Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
                }else{
                    Toast.makeText(Login.this, "Complete all fields...  " , Toast.LENGTH_LONG).show();
                    loadingBar.dismiss();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, PreRegister.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
//        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//            startActivity(intent);
//        }
    }

    private void checkUserExistence() {
//        DatabaseReference user_id = FirebaseDatabase.getInstance().getReference().child("tblUsers");
//        final DatabaseReference userVerification = user_id.child("userVerification");
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.child("Students").hasChild(user_id)){
                    Intent mainIntent = new Intent(Login.this, MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }else if (dataSnapshot.child("Admins").hasChild(user_id)){
                    Intent mainIntent = new Intent(Login.this, MainActivity.class);
                    mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }else {
                    Toast.makeText(Login.this, "User not verified!", Toast.LENGTH_SHORT).show();
//                    mProgress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Intent intent = new Intent();
                intent.setClass(Login.this, PasswordActivity.class);
                startActivity(intent);

            }
        });

    }
}
