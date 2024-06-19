package fabiola.osorto.helpdesk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.util.UUID

class Regisrarse : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_regisrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1. mando a llammar a todos los elementos
        val imgAtrasregistrarse = findViewById<ImageView>(R.id.imgAtrasregistrarse)
        val txtCorreoRegistro = findViewById<EditText>(R.id.txtCorreoRegistrarse)
        val txtContraseñaRegisrarse = findViewById<EditText>(R.id.txtContraseñaRegistrarse)
        val txtConfirmarContraseña = findViewById<EditText>(R.id.txtConfirmarContraseña)
        val btnCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)
        val btnRegresarLogin = findViewById<Button>(R.id.btnRegresarLogin)

        //2.proramo botones
        btnCrearCuenta.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()
                val crearUsuario =
                    objConexion?.prepareStatement("INSERT INTO tbUsuarios(UUID_usuario, correoElectronico, clave) VALUES (?, ?, ?)")!!
                crearUsuario.setString(1, UUID.randomUUID().toString())
                crearUsuario.setString(2, txtCorreoRegistro.text.toString())
                crearUsuario.setString(3, txtContraseñaRegisrarse.text.toString())
                crearUsuario.executeUpdate()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Regisrarse, "Usuario creado", Toast.LENGTH_SHORT).show()
                    txtCorreoRegistro.setText("")
                    txtCorreoRegistro.setText("")
                    txtConfirmarContraseña.setText("")
                }
            }
        }

        //flechita de arriba
        imgAtrasregistrarse.setOnClickListener {
            val pantallaLogin = Intent(this, Login::class.java)
            startActivity(pantallaLogin)
        }
        //boton que ya tengo una cuenta
        btnRegresarLogin.setOnClickListener {
            val pantallaLogin = Intent(this, Login::class.java)
            startActivity(pantallaLogin)
        }

    }
}








