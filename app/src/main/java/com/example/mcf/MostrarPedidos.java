package com.example.mcf;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.LinkedList;

public class MostrarPedidos extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_mostrar_pedidos);

    }


    public void init() {
        TableLayout stk = (TableLayout) findViewById(R.id.table_pedidos);
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setBackgroundColor(Color.parseColor("#2D572C"));
        TextView tv0 = new TextView(this);
        tv0.setText(" Telefono ");
        tv0.setTextColor(Color.WHITE);
        tv0.setGravity(Gravity.CENTER);
        tv0.setPadding(0,0,25,0);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Tipo ");
        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tv1.setPadding(0,0,25,0);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Precio ");
        tv2.setTextColor(Color.WHITE);
        tv2.setGravity(Gravity.CENTER);
        tv2.setPadding(0,0,25,0);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Fecha. ");
        tv3.setTextColor(Color.WHITE);
        tv3.setGravity(Gravity.CENTER);
        tv3.setPadding(0,0,25,0);
        tbrow0.addView(tv3);

        TextView tv4 = new TextView(this);
        tv3.setText(" Metodo Pago. ");
        tv3.setTextColor(Color.WHITE);
        tv3.setGravity(Gravity.CENTER);
        tv3.setPadding(0,0,25,0);
        tbrow0.addView(tv4);

        stk.addView(tbrow0);

        ModeloPedido aux;
        DataBaseOperation db = new DataBaseOperation(MostrarPedidos.this);
        LinkedList<ModeloPedido> listaPedidos = db.getPedidos();
        int colorA = Color.parseColor("#9E9E9E");
        int colorB = Color.parseColor("#FFFFFF");

        int colorLetraBlanco = Color.parseColor("#FFFFFF");
        int colorLetraNegro = Color.parseColor("#000000");

        int colorC,colorLetra;
        for (int i = 0; i < listaPedidos.size(); i++) {


            if(i%2 == 0){
                colorC = colorB;
                colorLetra = colorLetraNegro;
            }else{
                colorC = colorA;
                colorLetra = colorLetraBlanco;
            }


            TableRow tbrow = new TableRow(this);
            TextView t1v = new TextView(this);

            tbrow.setBackgroundColor(colorC);

            t1v.setText("" + listaPedidos.get(i).getTelefono());
            t1v.setTextColor(colorLetra);
            t1v.setGravity(Gravity.CENTER);
            t1v.setPadding(0,0,25,0);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(listaPedidos.get(i).getTipo());
            t2v.setTextColor(colorLetra);
            t2v.setGravity(Gravity.CENTER);
            t2v.setPadding(0,0,25,0);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(""+ listaPedidos.get(i).getPrecio());
            t3v.setTextColor(colorLetra);
            t3v.setGravity(Gravity.CENTER);
            t3v.setPadding(0,0,25,0);
            tbrow.addView(t3v);

            TextView t4v = new TextView(this);
            t4v.setText(listaPedidos.get(i).getFecha());
            t4v.setTextColor(colorLetra);
            t4v.setGravity(Gravity.CENTER);
            t4v.setPadding(0,0,25,0);
            tbrow.addView(t4v);

            TextView t5v = new TextView(this);
            t4v.setText(listaPedidos.get(i).getMetodoPago());
            t4v.setTextColor(colorLetra);
            t4v.setGravity(Gravity.CENTER);
            t4v.setPadding(0,0,25,0);
            tbrow.addView(t5v);


            stk.addView(tbrow);
        }

    }


}
