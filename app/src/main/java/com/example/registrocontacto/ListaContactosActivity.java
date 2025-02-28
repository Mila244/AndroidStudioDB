package com.example.registrocontacto;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListaContactosActivity extends AppCompatActivity {

    private ListView listViewContactos;  // Aquí va el ListView correctamente definido
    private DatabaseHelper dbHelper;  // Aquí usas el mismo DatabaseHelper que en MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactos);  // Asegúrate de que cargue el layout correcto

        listViewContactos = findViewById(R.id.listViewContactos);  // Vincular con el XML
        dbHelper = new DatabaseHelper(this);  // Conectar con la base de datos

        cargarContactos();  // Mostrar los contactos al abrir esta pantalla
    }

    private void cargarContactos() {
        ArrayList<String> listaContactos = new ArrayList<>();
        Cursor cursor = dbHelper.obtenerContactos();  // Leer desde SQLite

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