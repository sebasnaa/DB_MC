package com.example.mcf;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.telephony.TelephonyManager;

public class Loggin extends AppCompatActivity {

    public static int superUserStatus = -1;
    public static boolean credencialesUsuario = false;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private ImageView pegon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loggin);

        usernameEditText = findViewById(R.id.activity_main_usernameEditText);
        passwordEditText = findViewById(R.id.activity_main_passwordEditText);
        loginButton = findViewById(R.id.activity_main_loginButton);

        pegon = findViewById(R.id.gifInicio);

        String id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

// sebas 6492b67f86e8ef3a


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (usernameEditText.getText().length() > 0 && passwordEditText.getText().length() > 0) {
                    String toastMessage = "Usuario: " + usernameEditText.getText().toString() + ", Contraseña: " + passwordEditText.getText().toString();
                    //Por comodidad no creo base de datos para los usuarios, comrpuebo los campos y asigno 1 si es admin en caso cotontrario algo diferente a 1
                    //mcadmin    admin2022     a a
                    //mcjunior   2022

    //usernameEditText.setText(id);/

                    //&& id.equals("6492b67f86e8ef3a")

                    if(usernameEditText.getText().toString().trim().equals("a") && passwordEditText.getText().toString().trim().equals("a") ){

                        setSuperUserStatus(1);

                       // Intent intent = new Intent(Loggin.this, OpcionesAdmin.class);
                        Intent intent = new Intent(Loggin.this, OpcionesAdmin.class);
                        credencialesUsuario = true;
                        //intent.putExtra("superUserStatus",true);
                        startActivity(intent);

                    }else if(usernameEditText.getText().toString().trim().equals("j") && passwordEditText.getText().toString().trim().equals("j")){
                        Toast.makeText(getApplicationContext(), "Bunny logged", Toast.LENGTH_SHORT).show();
                        setSuperUserStatus(404);
                        credencialesUsuario = false;
                        Intent intent = new Intent(Loggin.this, OpcionesUser.class);
                        //intent.putExtra("superUserStatus",false);
                        startActivity(intent);

                    }

//                    if(!id.equals("6492b67f86e8ef3a")){
//                        pegon.setVisibility(View.VISIBLE);
//                        Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
//
//                        new CountDownTimer(5000,1000){
//
//                            @Override
//                            public void onTick(long millisUntilFinished) {
//
//                            }
//
//                            @Override
//                            public void onFinish() {
//                                pegon.setVisibility(View.INVISIBLE);
//
//                            }
//                        }.start();
//                    }

                } else {
                    String toastMessage = "Usuario o contraseña vacios";
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public static boolean getSuperUserStatus(){

        if(Loggin.superUserStatus == 1){
            return true;
        }

        return false;

    }

    private void setSuperUserStatus(int status){
        Loggin.superUserStatus = status;
    }







}
