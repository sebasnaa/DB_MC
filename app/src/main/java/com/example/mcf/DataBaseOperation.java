package com.example.mcf;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.Date;
import java.util.LinkedList;

public class DataBaseOperation extends SQLiteOpenHelper {

    public static  final String TABLA_CLIENTES = "TABLA_CLIENTES";
    public static  final String TABLA_PEDIDOS = "TABLA_PEDIDOS";
    public static final String COLUMNA_NOMBRE_CLIENTE = "NOMBRE_CLIENTE";
    public static final String COLUMNA_DIRECCION_CLIENTE = "DIRECCION_CLIENTE";
    public static final String COLUMNA_TELEFONO_CLIENTE = "TELEFONO_CLIENTE";
    public static final String COLUMNA_TIPO_PEDIDO = "TIPO_PEDIDO";
    public static final String COLUMNA_PRECIO_PEDIDO = "PRECIO_PEDIDO";
    public static final String COLUMNA_FECHA_PEDIDO = "FECHA_PEDIDO";
    public static final String COLUMNA_METODO_PAGO = "METODO_PAGO";

    public DataBaseOperation(@Nullable Context context) {
        super(context, "clientes.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLA_CLIENTES + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMNA_NOMBRE_CLIENTE + " TEXT, " + COLUMNA_DIRECCION_CLIENTE + " TEXT, " + COLUMNA_TELEFONO_CLIENTE + " INT)";


        String createTablePedidos = "CREATE TABLE " + TABLA_PEDIDOS + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMNA_TELEFONO_CLIENTE + " INT, "  + COLUMNA_TIPO_PEDIDO + " TEXT, " + COLUMNA_PRECIO_PEDIDO + " REAL ," + COLUMNA_METODO_PAGO + " TEXT ," +
                COLUMNA_FECHA_PEDIDO + " DEFAULT CURRENT_TIMESTAMP)";



        db.execSQL(createTableStatement);
        db.execSQL(createTablePedidos);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
/*        onCreate(db);*/

    }


    public void eliminarTabla(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLA_CLIENTES);
    }


    public boolean agregarCliente(ModeloCliente modeloCliente){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contenido = new ContentValues();

        if(!existeCliente(modeloCliente.getTelefonoString())){
            contenido.put(COLUMNA_NOMBRE_CLIENTE,modeloCliente.getNombre());
            contenido.put(COLUMNA_DIRECCION_CLIENTE,modeloCliente.getDireccion());
            contenido.put(COLUMNA_TELEFONO_CLIENTE,modeloCliente.getTelefono());

            long insert = db.insert(TABLA_CLIENTES, null, contenido);
            if(insert == -1){
                return false;
            }else{
                return true;
            }
        }else{
            return false;
        }

    }

    public boolean agregarPedido(ModeloPedido modeloPedido){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contenido = new ContentValues();


        contenido.put(COLUMNA_TELEFONO_CLIENTE,modeloPedido.getTelefono());
        contenido.put(COLUMNA_TIPO_PEDIDO,modeloPedido.getTipo());
        contenido.put(COLUMNA_PRECIO_PEDIDO,modeloPedido.getPrecio());
        contenido.put(COLUMNA_METODO_PAGO,modeloPedido.getMetodoPago());

        long insert = db.insert(TABLA_PEDIDOS, null, contenido);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }


    public boolean existeCliente(String telefono){

        String p_tel = telefono+"";
        String[] parametros = new String[1];
        parametros[0] = telefono;
        String query = "SELECT * FROM TABLA_CLIENTES  WHERE TELEFONO_CLIENTE = ? ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,parametros);

        if(!cursor.moveToFirst()){
            cursor.close();
            return false;
        }
        int contador = cursor.getInt(0);
        cursor.close();
        return contador > 0;
    }

    public LinkedList<ModeloCliente> getClientes(){
        LinkedList<ModeloCliente> listaRes = new LinkedList<>();

        String query = "SELECT * FROM TABLA_CLIENTES";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                int clienteId = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String direccion = cursor.getString(2);
                int telefono = cursor.getInt(3);
                ModeloCliente aux = new ModeloCliente(clienteId,nombre,direccion,telefono);
                listaRes.add(aux);
                cursor.moveToNext();
            }
        }
    return listaRes;
    }

    public LinkedList<ModeloPedido> getPedidos(){
        LinkedList<ModeloPedido> listaRes = new LinkedList<>();

        String query = "SELECT * FROM TABLA_PEDIDOS";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            while(!cursor.isAfterLast()){

                int clienteId = cursor.getInt(0);
                int telefono = cursor.getInt(1);
                String tipo = cursor.getString(2);
                double precio = cursor.getDouble(3);
                String metodoPago = cursor.getString(4);
                String fecha = cursor.getString(5);
                ModeloPedido aux = new ModeloPedido(clienteId,telefono,tipo,precio,metodoPago,fecha);
                aux.setFecha(fecha);

                listaRes.add(aux);
                cursor.moveToNext();
            }
        }
        return listaRes;
    }

    public ModeloCliente getCliente(String tel){
        //ModeloCliente res = new ModeloCliente();
        ModeloCliente res = null;
        String p_tel = tel+"";
        String[] parametros = new String[1];
        parametros[0] = tel;
        String query = "SELECT * FROM TABLA_CLIENTES  WHERE TELEFONO_CLIENTE = ? ";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query,parametros);

        if(cursor.moveToFirst()){

            int clienteId = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String direccion = cursor.getString(2);
            int telefono = cursor.getInt(3);
            res = new ModeloCliente(clienteId,nombre,direccion,telefono);

        }
        cursor.close();
        db.close();
        return res;
    }


    public boolean eliminarCliente(String tel){
        ModeloCliente res = new ModeloCliente();
        String p_tel = tel+"";
        String[] parametros = new String[1];
        parametros[0] = tel;
        String query = "DELETE FROM TABLA_CLIENTES  WHERE TELEFONO_CLIENTE = ? ";
        SQLiteDatabase db = this.getWritableDatabase();

        int borradoExitoso = db.delete(TABLA_CLIENTES,"TELEFONO_CLIENTE=?", parametros);

        db.close();
        return borradoExitoso > 0;
    }


}
