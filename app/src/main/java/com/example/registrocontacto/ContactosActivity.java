package com.example.registrocontacto;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ContactosActivity extends AppCompatActivity {

    ListView listViewContactos;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);

        listViewContactos = findViewById(R.id.listViewContactos);
        dbHelper = new DatabaseHelper(this);

        cargarContactos();
    }

    private void cargarContactos() {
        ArrayList<String> listaContactos = new ArrayList<>();
        Cursor cursor = dbHelper.obtenerContactos();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String numero = cursor.getString(cursor.getColumnIndexOrThrow("numero"));
                listaContactos.add(nombre + " - " + numero);
            }
            cursor.close();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaContactos);
        listViewContactos.setAdapter(adapter);
    }
}