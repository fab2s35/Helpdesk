package fabiola.osorto.helpdesk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import modelo.ClaseConexion

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1. Mando a llmar a todos los elementos
        val txtCorreoLogin = findViewById<EditText>(R.id.txtCorreoLogin)
        val txtContraseñaLogin = findViewById<EditText>(R.id.txtContraseñaLogin)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val btnRegistrarte = findViewById<Button>(R.id.btnRegistrarte)

        //2.programo los botones
        btnIngresar.setOnClickListener {
            val pantallaPrincipal = Intent(this, MainActivity::class.java)

            GlobalScope.launch(Dispatchers.IO) {
                val objConexion = ClaseConexion().cadenaConexion()

                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM tbUsuarios WHERE correoElectronico = ? AND clave = ?")!!
                comprobarUsuario.setString(1, txtCorreoLogin.text.toString())
                comprobarUsuario.setString(2, txtContraseñaLogin.text.toString())
                val resultado = comprobarUsuario.executeQuery()

                    if (resultado.next()) {
                        startActivity(pantallaPrincipal)
                    } else {
                        println("Usuario no encontrado, verifique las credenciales")
                    }
            }

        }

        //para ir a registro de cuenta
        btnRegistrarte.setOnClickListener {
            val pantallaRegisrarse = Intent(this, Regisrarse::class.java)
            startActivity(pantallaRegisrarse)
        }
    }
}