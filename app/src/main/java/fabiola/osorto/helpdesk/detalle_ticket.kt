package fabiola.osorto.helpdesk

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detalle_ticket : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_ticket)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //aqui en el codigo de la pantalla recibo los valores
        val numeroRecibido = intent.getIntExtra("numero", 0)
        val tituloRecibido = intent.getStringExtra("titulo")
        val descripcionRecibido = intent.getStringExtra("descripcion")
        val autorRecibido = intent.getStringExtra("autor")
        val emailRecibido = intent.getStringExtra("email")

        //mandamos a llamar a los label para poner los valores ahi
        val lblNumeroT = findViewById<TextView>(R.id.lblNumeroT)
        val lblTitulo = findViewById<TextView>(R.id.lblTitulo)
        val lblDescripcion = findViewById<TextView>(R.id.lblDescripcion)
        val lblAutor = findViewById<TextView>(R.id.lblAutor)
        val lblEmail = findViewById<TextView>(R.id.lblEmail)

        // poner los elemetos que recibi en los labels
        lblNumeroT.text = numeroRecibido.toString()
        lblTitulo.text = tituloRecibido
        lblDescripcion.text = descripcionRecibido
        lblAutor.text = autorRecibido
        lblEmail.text = emailRecibido
    }
}