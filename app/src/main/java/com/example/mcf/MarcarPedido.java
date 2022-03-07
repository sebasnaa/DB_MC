package com.example.mcf;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MarcarPedido extends AppCompatActivity {

    private double descuentoGlovo;



    Button botonMain,btn_confirmar_pedido;
    TextView et_nombre,et_direccion,et_telefono,et_cambio_calculado,descuentoField;
    EditText et_importe_pedido,et_entrega_dinero,importeField,entregaField,et_descuento_aplicado;
    RadioButton r_btn_tarjeta,r_btn_efectivo;
    Switch sw_glovo,sw_pagado,sw_descuento;




    @SuppressLint("WrongViewCast")
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
        et_cambio_calculado = findViewById(R.id.et_cambio_calculado);

        //EditText
        et_importe_pedido = findViewById(R.id.et_importe_pedido);
        et_entrega_dinero = findViewById(R.id.et_entrega_dinero);
        et_descuento_aplicado = findViewById(R.id.et_descuento_aplicado);
        importeField = findViewById(R.id.importeField);
        entregaField = findViewById(R.id.entregaField);
        descuentoField = findViewById(R.id.descuentoField);

        descuentoField.setVisibility(View.INVISIBLE);
        et_descuento_aplicado.setVisibility(View.INVISIBLE);

        //RadioButton
        r_btn_tarjeta = findViewById(R.id.radio_btn_tarjeta);
        r_btn_efectivo = findViewById(R.id.radio_btn_efectivo);

        //switch
        sw_glovo = findViewById(R.id.sw_glovo);
        sw_pagado = findViewById(R.id.sw_pagado);
        sw_descuento = findViewById(R.id.sw_descuento);
        sw_pagado.setVisibility(View.INVISIBLE);
        sw_descuento.setVisibility(View.INVISIBLE);


        setDatosPedidoCliente();
        et_cambio_calculado.setKeyListener(null);
        entregaField.setKeyListener(null);
        importeField.setKeyListener(null);


        Intent intent = getIntent();
        boolean superUser = intent.getBooleanExtra("superUserStatus",false);

        sw_glovo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sw_pagado.setVisibility(View.VISIBLE);
                    sw_descuento.setVisibility(View.VISIBLE);
                }else{
                    sw_pagado.setVisibility(View.INVISIBLE);
                    sw_descuento.setVisibility(View.INVISIBLE);

                }
            }
        });

        sw_descuento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    descuentoField.setVisibility(View.VISIBLE);
                    et_descuento_aplicado.setVisibility(View.VISIBLE);
                }else{
                    descuentoField.setVisibility(View.INVISIBLE);
                    et_descuento_aplicado.setVisibility(View.INVISIBLE);
                }
            }
        });


        botonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentMod = new Intent(MarcarPedido.this,MainActivity.class);
                //intentMod.putExtra("superUserStatus",superUser);
                startActivity(intentMod);
            }
        });




        btn_confirmar_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tele = Integer.parseInt(et_telefono.getText().toString());
                String tipo = getTipoPedido();
                double importe = Double.parseDouble(et_importe_pedido.getText().toString());
                String metodoPago = getMetodoPago();


                if(sw_pagado.isChecked()){
                    metodoPago = "App";
                }

                ModeloPedido moPedido = new ModeloPedido(-1,tele,tipo,importe,metodoPago,"");


                String tipoPedido = tipo.equals("G") ? "Glovo" : "Restaurante";

                String datosPedido = "Metodo pago -> " + metodoPago + "\n" +
                        "Importe -> " + importe +
                        " € \n" + "Tipo -> " + tipoPedido;
                AlertDialog.Builder builder = new AlertDialog.Builder(MarcarPedido.this);
                builder.setMessage(datosPedido);
                builder.setTitle(getResources().getString(R.string.confirmacion_pedido));
                builder.setCancelable(false);
                builder.setNegativeButton(getResources().getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton(getResources().getString(R.string.confirmar), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        DataBaseOperation dataBaseOperation = new DataBaseOperation(MarcarPedido.this);
                        boolean exitoso = dataBaseOperation.agregarPedido(moPedido);
                        boolean exitosoDescuento = true;
                        if(sw_glovo.isChecked() && sw_descuento.isChecked() && et_descuento_aplicado.getText().toString().length() > 0){
                            double importeDescuento = Double.parseDouble(et_descuento_aplicado.getText().toString());
                            exitosoDescuento = dataBaseOperation.agregarDescuentoPedido(getMetodoPago(),importeDescuento);
                        }


                        if(exitoso && exitosoDescuento){
                            Toast.makeText(MarcarPedido.this, "Pedido Agregado",
                                    Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(MarcarPedido.this,MainActivity.class);
                            //intent.putExtra("superUserStatus",superUser);

                            startActivity(intent);
                        }else{
                            Toast.makeText(MarcarPedido.this, "Error,no agregado",
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                });

                AlertDialog alert = builder.create();
                alert.show();

            }
        });


        et_importe_pedido.addTextChangedListener(textWatcher);
        et_entrega_dinero.addTextChangedListener(textWatcher);
        et_descuento_aplicado.addTextChangedListener(textWatcher);
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
            return "G";
        }else{
            return "R";
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            if(!sw_descuento.isChecked()){
                descuentoGlovo = 0;

            }
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

        if(et_importe_pedido.getText().toString().length() > 0 &&  et_entrega_dinero.getText().toString().length() > 0){
            Double precio = Double.parseDouble(et_importe_pedido.getText().toString());
            Double entregaDinero = Double.parseDouble(et_entrega_dinero.getText().toString());
            setDescuento();
            if (precio > 0 && (precio <= entregaDinero+descuentoGlovo)) {
                double cambio = entregaDinero+descuentoGlovo-precio;
                et_cambio_calculado.setText(""+cambio);
                btn.setEnabled(true);
            } else {
                et_cambio_calculado.setText("");
                btn.setEnabled(false);
            }
        }else{
            btn.setEnabled(false);
        }

    }


    private void setDescuento(){

        descuentoGlovo = 0;

        if( et_descuento_aplicado.getVisibility() == View.VISIBLE && et_descuento_aplicado.getText().toString().length() > 0){
            descuentoGlovo = Double.parseDouble(et_descuento_aplicado.getText().toString());
        }
    }










}
