package com.example.mcf;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.LinkedList;

public class DataBaseOperation extends SQLiteOpenHelper {

    public static  final String TABLA_CLIENTES = "TABLA_CLIENTES";
    public static final String COLUMNA_NOMBRE_CLIENTE = "NOMBRE_CLIENTE";
    public static final String COLUMNA_DIRECCION_CLIENTE = "DIRECCION_CLIENTE";
    public static final String COLUMNA_TELEFONO_CLIENTE = "TELEFONO_CLIENTE";

    public DataBaseOperation(@Nullable Context context) {
        super(context, "clientes.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLA_CLIENTES + " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMNA_NOMBRE_CLIENTE + " TEXT, " + COLUMNA_DIRECCION_CLIENTE + " TEXT, " + COLUMNA_TELEFONO_CLIENTE + " INT)";

        db.execSQL(createTableStatement);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //onCreate(db);
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
