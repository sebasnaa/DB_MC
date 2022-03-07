package com.example.mcf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class OpcionesUser extends AppCompatActivity {

Button btn_edicion_user,btn_cerrar_sesion_user;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_opciones_user);

        btn_edicion_user = findViewById(R.id.btn_edicion_user);
        btn_cerrar_sesion_user = findViewById(R.id.btn_cerrar_sesion_user);

        btn_edicion_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpcionesUser.this, MainActivity.class);
                //intent.putExtra("superUserStatus",Loggin.getSuperUserStatus());
                startActivity(intent);
            }
        });

        btn_cerrar_sesion_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpcionesUser.this, Loggin.class);
                //intent.putExtra("superUserStatus",Loggin.getSuperUserStatus());
                startActivity(intent);
            }
        });


    }


}
