package com.example.favoritapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.favoritapp.clases.Mensajes;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {

    private FirebaseAuth autenticacion;
    private static EditText txtEmail;
    private static EditText txtClave;
    private int SignInEstado = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticacion = FirebaseAuth.getInstance();

        FirebaseUser usuario = autenticacion.getCurrentUser();
        if(usuario!=null)
        {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }

        txtEmail = (EditText) findViewById(R.id.main_edtEmail);
        txtClave = (EditText) findViewById(R.id.main_edClave);
        Button btnAcceder = (Button) findViewById(R.id.main_btnAcceder);
        Button btnRegistro = (Button) findViewById(R.id.main_btnRegistro);
        ImageButton btnGoogle = (ImageButton) findViewById(R.id.login_btnGoogle);

        btnAcceder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(validarCamposVacios())
                {
                    new Mensajes(view.getContext()).alert("Advertencia", "Digite los campos en blanco.");
                }
                else {
                    autenticacion.signInWithEmailAndPassword(
                            txtEmail.getText().toString(),
                            txtClave.getText().toString()).addOnCompleteListener((Activity) view.getContext(),
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        Intent i = new Intent(view.getContext(), MainActivity.class);
                                        startActivity(i);
                                    }
                                }
                            });
                }
            }
        });

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Registro.class));
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.web_client))
                        .requestEmail()
                        .build();

                Intent googleDialog = GoogleSignIn.getClient(v.getContext(), gso).getSignInIntent();
                startActivityForResult(googleDialog, SignInEstado);
            }
        });
    }
    public static boolean validarCamposVacios()
    {
        boolean vacio = true;

        try {
            if(!txtEmail.getText().toString().isEmpty() && !txtClave.getText().toString().isEmpty())
                vacio=false;
        }
        catch (Exception ex)
        {

        }

        return vacio;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Activity actividadActual = this;

        if(requestCode==SignInEstado){

            Task<GoogleSignInAccount> cuentaEsperada = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount cuenta = cuentaEsperada.getResult(ApiException.class);
                AuthCredential credencial = GoogleAuthProvider.getCredential(cuenta.getIdToken(), null);
                autenticacion.signInWithCredential(credencial).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser usuario = autenticacion.getCurrentUser();
                            Intent menu = new Intent(actividadActual, MainActivity.class);
                            menu.putExtra("usuario", usuario.getEmail());
                            startActivity(menu);
                        }
                        else
                        {
                          /*  AlertDialog.Builder msj = new AlertDialog.Builder();
                            msj.setMessage("Advertencia");
                            msj.setTitle("Error al ingresar por medio de Gmail");
                            msj.create();
                            msj.show();*/

                        }
                    }
                });
            }
            catch (Exception ex)
            {

            }
        }
    }
}