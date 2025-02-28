package com.example.registrocontacto;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTelefono;
    private ArrayList<Contacto> listaContactos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTelefono = findViewById(R.id.editTextTelefono);
        configurarBotonesNumericos();

        Button btnMostrar = findViewById(R.id.btnContactos);
        btnMostrar.setOnClickListener(v -> mostrarContactos());
    }

    private void configurarBotonesNumericos() {
        int[] idsBotones = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9};

        for (int id : idsBotones) {
            Button boton = findViewById(id);
            boton.setOnClickListener(v -> {
                String textoActual = editTextTelefono.getText().toString();
                editTextTelefono.setText(textoActual + boton.getText().toString());
            });
        }
    }

    public void guardarContacto(View view) {
        if (editTextTelefono.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingresa un número antes de guardar", Toast.LENGTH_SHORT).show();
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guardar Contacto");

        final EditText inputNombre = new EditText(this);
        inputNombre.setHint("Nombre");
        builder.setView(inputNombre);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String nombre = inputNombre.getText().toString().trim();
            String numero = editTextTelefono.getText().toString().trim();

            if (nombre.isEmpty()) {
                Toast.makeText(this, "Debes ingresar un nombre", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseHelper dbHelper = new DatabaseHelper(this);
            dbHelper.insertarContacto(nombre, numero);

            Toast.makeText(this, "Contacto guardado", Toast.LENGTH_SHORT).show();
            editTextTelefono.setText("");
        });

        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    public void mostrarContactos() {
        listaContactos.clear();

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.obtenerContactos();

        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                String numero = cursor.getString(cursor.getColumnIndexOrThrow("numero"));
                listaContactos.add(new Contacto(nombre, numero));
            } while (cursor.moveToNext());
        }
        cursor.close();

        if (listaContactos.isEmpty()) {
            Toast.makeText(this, "No hay contactos guardados", Toast.LENGTH_SHORT).show();
            return;
        }

        String[] nombres = new String[listaContactos.size()];
        for (int i = 0; i < listaContactos.size(); i++) {
            nombres[i] = listaContactos.get(i).toString();
        }

        new AlertDialog.Builder(this)
                .setTitle("Contactos Guardados")
                .setItems(nombres, null)
                .setPositiveButton("Cerrar", null)
                .show();
    }

    public void realizarLlamada(View view) {
        String numero = editTextTelefono.getText().toString();
        if (numero.isEmpty()) {
            Toast.makeText(this, "Ingresa un número para llamar", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Llamando a " + numero, Toast.LENGTH_SHORT).show();
        }
    }

    public void borrarUltimoNumero(View view) {
        String textoActual = editTextTelefono.getText().toString();
        if (!textoActual.isEmpty()) {
            editTextTelefono.setText(textoActual.substring(0, textoActual.length() - 1));
        }
    }

    public void abrirListaContactos(View view) {
        Intent intent = new Intent(this, ContactosActivity.class);
        startActivity(intent);
    }
}