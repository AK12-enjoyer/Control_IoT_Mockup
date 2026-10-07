package com.example.webservicessencillo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var e1: EditText // nombre
    private lateinit var e2: EditText // apellido
    private lateinit var e3: EditText // direccion
    private lateinit var e4: EditText // telefono
    private lateinit var e5: EditText // correo
    private lateinit var e6: EditText // sexo
    private lateinit var e7: EditText // fecha
    private lateinit var e8: EditText // id
    private lateinit var b: Button
    private lateinit var mostrar: Button
    private lateinit var buscar: Button

    private lateinit var btnLogout: Button

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)
        relacionamosVistas()

        mostrar.setOnClickListener {
            limpiarCampos()
        }

        buscar.setOnClickListener {
            val idUsuario = e8.text.toString().trim()
            if (idUsuario.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa un ID para buscar", Toast.LENGTH_SHORT).show()
            } else {
                buscarUsuarios("http://192.168.1.15/test1/buscar_usuario.php?id=$idUsuario")
            }
        }

        btnLogout.setOnClickListener {
            regresar(it)
        }
    }

    private fun relacionamosVistas() {
        e1 = findViewById(R.id.nombre)
        e2 = findViewById(R.id.apellido)
        e3 = findViewById(R.id.direccion)
        e4 = findViewById(R.id.telefono)
        e5 = findViewById(R.id.correo)
        e6 = findViewById(R.id.sexo)
        e7 = findViewById(R.id.fecha)
        e8 = findViewById(R.id.id)
        b = findViewById(R.id.acceso)
        mostrar = findViewById(R.id.mostrar)
        buscar = findViewById(R.id.btnBuscar)

        btnLogout = findViewById(R.id.btnLogout)
    }

    private fun limpiarCampos() {
        e1.setText("")
        e2.setText("")
        e3.setText("")
        e4.setText("")
        e5.setText("")
        e6.setText("")
        e7.setText("")
        e8.setText("")
    }

    fun buscarUsuarios(url: String) {
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                if (response.length() > 0) {
                    try {
                        val jsonObject = response.getJSONObject(0)
                        e1.setText(jsonObject.getString("nombre"))
                        e2.setText(jsonObject.getString("apellido"))
                        e3.setText(jsonObject.getString("direccion"))
                        e4.setText(jsonObject.getString("telefono"))
                        e5.setText(jsonObject.getString("correo"))
                        e6.setText(jsonObject.getString("sexo"))
                        e7.setText(jsonObject.getString("fecha"))
                    } catch (e: JSONException) {
                        Toast.makeText(applicationContext, "Error en formato de datos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "No se encontró ningún usuario con ese ID", Toast.LENGTH_SHORT).show()
                }
            },
            {
                Toast.makeText(applicationContext, "Error de Conexión con Raspberry", Toast.LENGTH_SHORT).show()
            }
        )
        requestQueue.add(jsonArrayRequest)
    }

    fun regresar(view: View?) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}