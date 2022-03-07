package com.example.mcf;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Loggin extends AppCompatActivity {

    public static int superUserStatus = -1;
    public static boolean credencialesUsuario = false;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loggin);

        usernameEditText = findViewById(R.id.activity_main_usernameEditText);
        passwordEditText = findViewById(R.id.activity_main_passwordEditText);
        loginButton = findViewById(R.id.activity_main_loginButton);




        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEditText.getText().length() > 0 && passwordEditText.getText().length() > 0) {
                    String toastMessage = "Usuario: " + usernameEditText.getText().toString() + ", Contraseña: " + passwordEditText.getText().toString();
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
                    //Por comodidad no creo base de datos para los usuarios, comrpuebo los campos y asigno 1 si es admin en caso cotontrario algo diferente a 1
                    //mcadmin    admin2022     a a
                    //mcjunior   2022

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
