package com.example.mcf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button btn_agregar,btn_buscar,btn_eliminar,btn_mostrar_clientes,btn_marcar_pedido,btn_home_user,btn_home_admin;
    EditText et_nombre,et_direccion,et_telefono;



    private static final String TAG = "Main";
    private boolean superUserStatus = false;

    private boolean getUserStatus() {
        Intent intent = getIntent();
        boolean status = intent.getBooleanExtra("superUserStatus",false);
        return  status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        superUserStatus = getUserStatus();

        superUserStatus = Loggin.credencialesUsuario;

        btn_marcar_pedido = findViewById(R.id.btn_marcar_pedido);

        btn_agregar = findViewById(R.id.btn_agregar);
        btn_buscar = findViewById(R.id.btn_buscar);
        btn_eliminar = findViewById(R.id.btn_eliminar);
        btn_home_user = findViewById(R.id.btn_home_user);
        btn_home_admin = findViewById(R.id.btn_home_admin);
        et_nombre = findViewById(R.id.NombreCliente);
        et_direccion = findViewById(R.id.DireccionCliente);
        et_telefono = findViewById(R.id.TelefonoCliente);


        if(!superUserStatus){
            //Toast.makeText(getApplicationContext(), "Junior", Toast.LENGTH_SHORT).show();
            btn_eliminar.setVisibility(View.GONE);
            btn_home_admin.setVisibility(View.GONE);
        }

        if(superUserStatus){
            btn_home_user.setVisibility(View.GONE);
        }




        btn_marcar_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MarcarPedido.class);
                intent.putExtra("nombreCliente",et_nombre.getText().toString());
                intent.putExtra("direccionCliente",et_direccion.getText().toString());
                intent.putExtra("telefonoCliente",et_telefono.getText().toString());
                //intent.putExtra("superUserStatus",superUserStatus);

                startActivity(intent);
            }
        });

        btn_home_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OpcionesUser.class);
                startActivity(intent);
            }
        });

        btn_home_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OpcionesAdmin.class);
                startActivity(intent);
            }
        });


        btn_agregar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                ModeloCliente modeloCliente = null;

                String mensajeError = "Faltan Datos";
                try{

                    //Datos correctos
                    if(et_nombre.getText().toString().length() > 0 && et_direccion.getText().toString().length() > 0 && et_telefono.getText().toString().length() > 0  ){
                        String nombre = et_nombre.getText().toString();
                        String dir = et_direccion.getText().toString();
                        int tel = Integer.parseInt(et_telefono.getText().toString());
                        if(Integer.toString(tel).length() < 9 && Integer.toString(tel).length() > 0){
                            mensajeError+=", Teléfono Incorrecto";
                        }else{

                            modeloCliente= new ModeloCliente(-1,nombre,dir,tel);
                            DataBaseOperation dataBaseOperation = new DataBaseOperation(MainActivity.this);
                            boolean exitoso = dataBaseOperation.agregarCliente(modeloCliente);
                            String mensaje ="Cliente existente";
                            if(exitoso){
                                mensaje = "Cliente guardado";
                                //limpiar();
                            }
                            Toast.makeText(MainActivity.this,mensaje ,Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(et_nombre.getText().toString().length() < 1 || et_direccion.getText().toString().length() < 1 || et_telefono.getText().toString().length() < 1 ){
                        Toast.makeText(MainActivity.this,"Faltan datos" ,Toast.LENGTH_SHORT).show();
                    }

                }catch(Exception e){
                    Toast.makeText(MainActivity.this,"Error creando cliente "+ e.getMessage() ,Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_buscar.setOnClickListener(new View.OnClickListener() {

            ModeloCliente modeloCliente;
            public void onClick(View v) {

                DataBaseOperation dataBaseOperation = new DataBaseOperation(MainActivity.this);
                String telefono = et_telefono.getText().toString();
                modeloCliente = dataBaseOperation.getCliente(telefono);
                if(modeloCliente != null){

//                    Toast.makeText(MainActivity.this,modeloCliente.toString() ,Toast.LENGTH_SHORT).show();
                    et_nombre.setText(modeloCliente.getNombre());
                    et_direccion.setText(modeloCliente.getDireccion());
                    String aux = ""+modeloCliente.getTelefono();
                    et_telefono.setText(aux);
                }else if(modeloCliente == null){
                    Toast.makeText(MainActivity.this,"No existe, compruebe teléfono" ,Toast.LENGTH_SHORT).show();
                }


            }
        });


        btn_eliminar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                DataBaseOperation dataBaseOperation = new DataBaseOperation(MainActivity.this);
                String telefono = et_telefono.getText().toString();
                boolean salida = dataBaseOperation.eliminarCliente(telefono);
                Toast.makeText(MainActivity.this,""+salida ,Toast.LENGTH_SHORT).show();
                if(salida){
                    limpiar();
                }




            }
        });



        et_nombre.addTextChangedListener(textWatcher);
        et_direccion.addTextChangedListener(textWatcher);
        et_telefono.addTextChangedListener(textWatcher);
        checkFieldsForEmptyValues();





    }


    @Override
    public void onBackPressed() {
        //muere el boton back
        super.onBackPressed();

    }



    public void limpiar(){
        et_nombre.setText("");
        et_telefono.setText("");
        et_direccion.setText("");
        et_telefono.setText("");
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
        Button btn = (Button) findViewById(R.id.btn_marcar_pedido);

        String nombre = et_nombre.getText().toString();
        String dirr = et_direccion.getText().toString();
        String telefono = et_telefono.getText().toString();

        if (nombre.length() > 0 && dirr.length() > 0 && telefono.length() > 0) {
            btn.setEnabled(true);
        } else {
            btn.setEnabled(false);
        }
    }

}