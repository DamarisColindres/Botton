package com.dc.botton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthException;

public class MainActivity extends AppCompatActivity {

    private Button btnPersonas;
    private Button btnProductos;
    private Button btnOrdenes;
    private Button btnInformes;
    private Button btnConfiguracion;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindUI();
        eventos();

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void bindUI() {
        btnPersonas = findViewById(R.id.btnPersonas);
        btnProductos = findViewById(R.id.btnProductos);
        btnOrdenes = findViewById(R.id.btnOrdenes);
        btnInformes = findViewById(R.id.btnInformes);
        btnConfiguracion = findViewById(R.id.btnConfiguracion);
    }

    private void eventos() {
        btnPersonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PersonasActivity.class);
                startActivity(intent);
            }
        });

        btnProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductosActivity.class);
                startActivity(intent);
            }
        });

        btnOrdenes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OrdenesActivity.class);
                startActivity(intent);
            }
        });

        btnInformes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InformesActivity.class);
                startActivity(intent);
            }
        });

        btnConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConfiguracionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void iniciarSesion(String email, String password) {
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    MainActivity.this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this, "¡Bienvenido, " + user.getEmail() + "!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, ProductosActivity.class);
                            startActivity(intent);
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidUserException e) {
                                Toast.makeText(MainActivity.this, "Usuario no encontrado. Verifica tu correo.", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(MainActivity.this, "Contraseña incorrecta. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show();
                            } catch (FirebaseAuthException e) {
                                Toast.makeText(MainActivity.this, "Error de autenticación: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "Error desconocido: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(MainActivity.this, "Ocurrió un error al iniciar sesión.", Toast.LENGTH_SHORT).show();
        }
    }
}