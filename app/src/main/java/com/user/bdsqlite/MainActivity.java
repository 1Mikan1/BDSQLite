package com.user.bdsqlite;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    BaseDatos miDB;
    EditText etNombre;
    Button btnGuardar, btnMostrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miDB = new BaseDatos(this);
        etNombre = findViewById(R.id.etNombre);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnMostrar = findViewById(R.id.btnMostrar);

        // Botón para guardar datos
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            if (nombre.isEmpty()) {
                mostrarMensaje("Error", "Por favor, ingresa un nombre");
            } else {
                boolean insertado = miDB.insertarDatos(nombre);
                if (insertado) {
                    mostrarMensaje("Éxito", "Datos guardados correctamente");
                    etNombre.setText("");
                } else {
                    mostrarMensaje("Error", "Error al guardar los datos");
                }
            }
        });

        // Botón para mostrar datos
        btnMostrar.setOnClickListener(v -> {
            Cursor cursor = miDB.obtenerDatos();
            if (cursor.getCount() == 0) {
                mostrarMensaje("Sin Datos", "No hay datos para mostrar");
                return;
            }

            StringBuilder datos = new StringBuilder();
            while (cursor.moveToNext()) {
                datos.append("ID: ").append(cursor.getString(0)).append("\n");
                datos.append("Nombre: ").append(cursor.getString(1)).append("\n\n");
            }
            mostrarMensaje("Datos Almacenados", datos.toString());
        });
    }

    // Método para mostrar un cuadro de diálogo
    private void mostrarMensaje(String titulo, String mensaje) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}