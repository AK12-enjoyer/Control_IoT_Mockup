package com.example.webservicessencillo

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etRegisterNombre = findViewById<EditText>(R.id.etRegisterNombre)
        val etRegisterApellido = findViewById<EditText>(R.id.etRegisterApellido)
        val etRegisterDireccion = findViewById<EditText>(R.id.etRegisterDireccion)
        val etRegisterTelefono = findViewById<EditText>(R.id.etRegisterTelefono)
        val etRegisterCorreo = findViewById<EditText>(R.id.etRegisterCorreo)
        val etRegisterSexo = findViewById<EditText>(R.id.etRegisterSexo)
        val etRegisterFecha = findViewById<EditText>(R.id.etRegisterFecha)
        val etRegisterPassword = findViewById<EditText>(R.id.etRegisterPassword)

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvBackToLogin = findViewById<TextView>(R.id.tvBackToLogin)
        val registerProgressBar = findViewById<ProgressBar>(R.id.registerProgressBar)

        tvBackToLogin.setOnClickListener {
            finish()
        }

        btnRegister.setOnClickListener {
            val nombre = etRegisterNombre.text.toString().trim()
            val apellido = etRegisterApellido.text.toString().trim()
            val direccion = etRegisterDireccion.text.toString().trim()
            val telefono = etRegisterTelefono.text.toString().trim()
            val correo = etRegisterCorreo.text.toString().trim()
            val sexo = etRegisterSexo.text.toString().trim()
            val fecha = etRegisterFecha.text.toString().trim()
            val contrasena = etRegisterPassword.text.toString().trim()

            if (nombre.isEmpty() || apellido.isEmpty() || direccion.isEmpty() ||
                telefono.isEmpty() || correo.isEmpty() || sexo.isEmpty() || fecha.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos de tu registro", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val url = "http://192.168.1.15/test1/registro.php"

            registerProgressBar.visibility = View.VISIBLE
            btnRegister.isEnabled = false

            val queue = Volley.newRequestQueue(this)

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response ->
                    registerProgressBar.visibility = View.GONE
                    btnRegister.isEnabled = true

                    try {
                        val jsonResponse = JSONObject(response)
                        val success = jsonResponse.getBoolean("success")
                        val mensaje = jsonResponse.getString("message")

                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()

                        if (success) {
                            finish()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error al procesar respuesta del servidor", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    registerProgressBar.visibility = View.GONE
                    btnRegister.isEnabled = true
                    Toast.makeText(this, "Error de conexión con AWS", Toast.LENGTH_SHORT).show()
                }) {

                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["nombre"] = nombre
                    params["apellido"] = apellido
                    params["direccion"] = direccion
                    params["telefono"] = telefono
                    params["correo"] = correo
                    params["sexo"] = sexo
                    params["fecha"] = fecha
                    params["contrasena"] = contrasena
                    return params
                }
            }

            queue.add(stringRequest)
        }
    }
}
