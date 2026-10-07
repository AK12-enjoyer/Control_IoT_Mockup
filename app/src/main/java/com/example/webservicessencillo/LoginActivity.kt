package com.example.webservicessencillo

import android.content.Intent
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

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        btnLogin.setOnClickListener {
            val usuario = etUsername.text.toString().trim()
            val contrasena = etPassword.text.toString().trim()

            if (usuario.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor, llena todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val url = "http://192.168.1.15/test1/ingreso.php"

            progressBar.visibility = View.VISIBLE
            btnLogin.isEnabled = false

            val queue = Volley.newRequestQueue(this)

            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener { response ->
                    progressBar.visibility = View.GONE
                    btnLogin.isEnabled = true

                    try {
                        val jsonResponse = JSONObject(response)
                        val success = jsonResponse.getBoolean("success")

                        if (success) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val mensaje = jsonResponse.getString("message")
                            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this, "Error al procesar respuesta", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener {
                    progressBar.visibility = View.GONE
                    btnLogin.isEnabled = true
                    Toast.makeText(this, "Error de conexión con AWS", Toast.LENGTH_SHORT).show()
                }) {

                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["username"] = usuario
                    params["password"] = contrasena
                    return params
                }
            }

            queue.add(stringRequest)
        }
        val tvGoToRegister = findViewById<TextView>(R.id.tvGoToRegister)

        tvGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
}