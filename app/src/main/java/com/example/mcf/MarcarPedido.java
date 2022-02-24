package com.example.mcf;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MarcarPedido extends AppCompatActivity {



    Button botonMain,btn_confirmar_pedido;
    TextView et_nombre,et_direccion,et_telefono;
    EditText et_importe_pedido;
    RadioButton r_btn_tarjeta,r_btn_efectivo;
    Switch sw_glovo;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_marcar_pedido);

        //Botones
        botonMain = findViewById(R.id.buttonToMainPedido);
        btn_confirmar_pedido = findViewById(R.id.btn_confirmar_pedido);

        //Etiquetas
        et_nombre = findViewById(R.id.nombreClientePedido);
        et_direccion = findViewById(R.id.direccionClientePedido);
        et_telefono = findViewById(R.id.telefonoClientePedido);

        //EditText
        et_importe_pedido = findViewById(R.id.et_importe_pedido);

        //RadioButton
        r_btn_tarjeta = findViewById(R.id.radio_btn_tarjeta);
        r_btn_efectivo = findViewById(R.id.radio_btn_efectivo);

        //switch
        sw_glovo = findViewById(R.id.sw_glovo);


        setDatosPedidoCliente();

        /*ModeloCliente cliente = new ModeloCliente();
        cliente = getDatosPedido();

        et_nombre.setText(cliente.getNombre());
        et_direccion.setText(cliente.getDireccion());*/


        botonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarcarPedido.this,MainActivity.class);
                startActivity(intent);
            }
        });


        btn_confirmar_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String telefono = et_telefono.getText().toString().trim();

                ModeloPedido aux = new ModeloPedido();
                aux.setTelefono(Integer.parseInt(telefono));
                aux.setMetodoPago(getMetodoPago());
                aux.setTipo(getTipoPedido());
                aux.setPrecio(Double.parseDouble(et_importe_pedido.getText().toString()));

                DataBaseOperation dataBaseOperation = new DataBaseOperation(MarcarPedido.this);
                boolean exitoso = dataBaseOperation.agregarPedido(aux);

                Toast.makeText(MarcarPedido.this,"Pedido: " + exitoso ,Toast.LENGTH_SHORT).show();
            }
        });


        et_importe_pedido.addTextChangedListener(textWatcher);
        checkFieldsForEmptyValues();

    }


    public ModeloCliente setDatosPedidoCliente(){
        ModeloCliente cliente = new ModeloCliente();
        Intent intent = getIntent();
        String telefono = intent.getStringExtra("telefonoCliente");
        DataBaseOperation db = new DataBaseOperation(MarcarPedido.this);
        ModeloCliente aux = db.getCliente(telefono);

        et_nombre.setText(aux.getNombre());
        et_direccion.setText(aux.getDireccion());
        et_telefono.setText(telefono);
        return cliente;
    }

    private String getMetodoPago(){

        if(r_btn_tarjeta.isChecked()){
            return r_btn_tarjeta.getText().toString();
        }


        return r_btn_efectivo.getText().toString();


    }

    private String getTipoPedido(){
        if(sw_glovo.isChecked()){
            return sw_glovo.getText().toString();
        }else{
            return "Restaurante";
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private  void checkFieldsForEmptyValues(){
        Button btn = (Button) findViewById(R.id.btn_confirmar_pedido);

        if(et_importe_pedido.getText().toString().length() > 0){
            Double precio = Double.parseDouble(et_importe_pedido.getText().toString());
            if (precio > 0) {
                btn.setEnabled(true);
            } else {
                btn.setEnabled(false);
            }
        }else{
            btn.setEnabled(false);
        }

    }










}
