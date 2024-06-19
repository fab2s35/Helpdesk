package fabiola.osorto.helpdesk

import RecycleViewHelper.Adaptador
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassTicket

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //1. Mando a llamar los elementos
        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtAutor = findViewById<EditText>(R.id.txtAutor)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        //2. mando a llmar el recycleView
        val rcvTicket = findViewById<RecyclerView>(R.id.rcvTicket)
        //le asigno un layout
        rcvTicket.layoutManager = LinearLayoutManager(this)

        //////////TOD0: mostrar datos
        fun mostrarDatos(): List<dataClassTicket>{
            //1. creo un objeto de la clase conexion
            val objConexion = ClaseConexion().cadenaConexion()

            //2. Crear un statement
            val statement = objConexion?.createStatement()
            val resultSet = statement?.executeQuery("SELECT * FROM tbTicket")!!

            //guardar lo que traiga el select
            val tickets = mutableListOf<dataClassTicket>()

            while (resultSet.next()) {
                val numero = resultSet.getInt("NumTicket")
                val titulo = resultSet.getString("Titulo")
                val descripcion = resultSet.getString("DESCRIPCION")
                val autor = resultSet.getString("Autor")
                val email = resultSet.getString("Email_Autor")
                val ticket = dataClassTicket(numero, titulo, descripcion, autor, email)
                tickets.add(ticket)

            }
            return tickets
        }

        //asigna el boton al recycleview
        //ejecutar la funcion para mostrar datos
        CoroutineScope(Dispatchers.IO).launch {
            //creo una variable que ejecute la funcion de ostrar datos
            val ticketDB = mostrarDatos()
            withContext(Dispatchers.Main){
                val miAdaptador = Adaptador(ticketDB)
                rcvTicket.adapter = miAdaptador
            }
        }

        //2. programar boton
        btnAgregar.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                //1. crear un objeto de la clase conexion
                val objConexion = ClaseConexion().cadenaConexion()

                //2. crear una variable que tenga un preparedStatement
                val addTicket = objConexion?.prepareStatement("insert into tbTicket(titulo, descripcion, autor, Email_autor) values(?, ?, ?, ?)")!!

                addTicket.setString(1, txtTitulo.text.toString())
                addTicket.setString(2, txtDescripcion.text.toString())
                addTicket.setString(3, txtAutor.text.toString())
                addTicket.setString(4, txtEmail.text.toString())

                val nuevosTickets = mostrarDatos()
                withContext(Dispatchers.Main){
                    //actualia el adaptador con los datos nuevos
                    (rcvTicket.adapter as? Adaptador)?.actualizarListado(nuevosTickets)
                }

            }
        }
}   }

