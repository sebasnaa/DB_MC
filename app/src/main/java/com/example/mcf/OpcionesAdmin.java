package com.example.mcf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class OpcionesAdmin extends AppCompatActivity {

    Button btn_edicion,btn_mostra_clientes,btn_mostrar_pedidos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_opciones_admin);


        btn_edicion = findViewById(R.id.btn_edicion_opcion);
        btn_mostra_clientes = findViewById(R.id.btn_mostrar_clientes_opcion);
        btn_mostrar_pedidos = findViewById(R.id.btn_mostrar_pedidos_opcion);


        btn_edicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpcionesAdmin.this, MainActivity.class);
                intent.putExtra("superUserStatus",Loggin.getSuperUserStatus());
                startActivity(intent);
            }
        });

        btn_mostra_clientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpcionesAdmin.this, mostrarClientes.class);
                startActivity(intent);
            }
        });





    }

}
