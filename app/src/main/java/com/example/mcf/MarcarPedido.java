package com.example.mcf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MarcarPedido extends AppCompatActivity {



    Button botonMain;
    TextView et_nombre,et_direccion,et_telefono;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_marcar_pedido);

        //Botones
        botonMain = findViewById(R.id.buttonToMainPedido);


        //Etiquetas
        et_nombre = findViewById(R.id.nombreClientePedido);
        et_direccion = findViewById(R.id.direccionClientePedido);
        et_telefono = findViewById(R.id.telefonoClientePedido);



        ModeloCliente cliente = new ModeloCliente();
        cliente = getDatosPedido();

        et_nombre.setText(cliente.getNombre());
        et_direccion.setText(cliente.getDireccion());


        botonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarcarPedido.this,MainActivity.class);
                startActivity(intent);
            }
        });








    }


    public ModeloCliente getDatosPedido(){
        ModeloCliente cliente = new ModeloCliente();
        Intent intent = getIntent();
        cliente.setNombre(intent.getStringExtra("nombreCliente"));
        cliente.setDireccion(intent.getStringExtra("direccionCliente"));
        String telefono = intent.getStringExtra("telefonoCliente");
       et_telefono.setText(telefono);
        return cliente;


    }

}
