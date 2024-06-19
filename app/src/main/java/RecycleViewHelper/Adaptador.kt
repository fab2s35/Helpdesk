package RecycleViewHelper

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import fabiola.osorto.helpdesk.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import modelo.dataClassTicket

class Adaptador(var Datos: List<dataClassTicket>): RecyclerView.Adapter<ViewHolder>() {

    //Funcion para que cuando agregue datos
    // se actualice la lista automaticamente
    fun actualizarListado (nuevaLista: List<dataClassTicket>){
        Datos = nuevaLista
        notifyDataSetChanged()
    }

    fun ActualizarListaDespuesDeEditar(numero: Int, nuevoTitulo: String){
        val index = Datos.indexOfFirst { it.NumTicket == numero }
        Datos[index].Titulo == nuevoTitulo
        notifyItemChanged(index)
    }

    /// eliminar datos
    fun eliminarDatos(Titulo: String, posicion: Int){
        //elminar de la lista
        val listaDatos = Datos.toMutableList()
        listaDatos.removeAt(posicion)

        //eliminarlo de la base de datos
        GlobalScope.launch(Dispatchers.IO){
            //1. creo un objeto de la clase conexion
            val objConexión = ClaseConexion().cadenaConexion()

            //2. creo una variable que contenga un PrepareStatement
            val deleteCancion = objConexión?.prepareStatement("delete tbTicket where Titulo = ?")!!
            deleteCancion.setString(1, Titulo)
            deleteCancion.executeUpdate()

            val commit = objConexión.prepareStatement("commit")
            commit.executeUpdate()

        }

        Datos = listaDatos.toList()
        notifyItemRemoved(posicion)
        notifyDataSetChanged()
    }

    fun actualizarDatos(nuevoTitulo: String, numero: Int) {
        GlobalScope.launch(Dispatchers.IO){
            //1. Creo un obj de la clase conexión
            val objConexion = ClaseConexion().cadenaConexion()

            // 2. creo una variable que contenga un PrepareStatement
            val updateCancion = objConexion?.prepareStatement("update tbTicket set Titulo = ? where NumTicket = ?")!!
            updateCancion.setString(1, nuevoTitulo)
            updateCancion.setString(2, numero.toString())
            updateCancion.executeQuery()

            val commit = objConexion.prepareStatement("commit")
            commit.executeUpdate()

            withContext(Dispatchers.Main){
                ActualizarListaDespuesDeEditar(numero, nuevoTitulo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Unir el recyclerview con la card
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount() = Datos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = Datos[position]
        holder.txtTitulo.text = item.Titulo

        //Tod0 click al icono
        holder.imgBorrar.setOnClickListener {

            // creo loa alerta para comfirmar la eliminación
            val context = holder.itemView.context

            //Creo la alerta
            //Usando los tres pasos: titulo, mensaje y botones
            val builder = AlertDialog.Builder(context)

            builder.setTitle("Confirmación")
            builder.setMessage("¿estas seguro que quieres borrar?")

            builder.setPositiveButton("Si"){ dialog, wich ->
                eliminarDatos(item.Titulo, position)
            }

            builder.setNegativeButton("No"){ dialog, wich ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
        }

        // click al icono de editar
        holder.imgEditar.setOnClickListener {
            //creo mi alerta para editar
            val context = holder.itemView.context

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Actualizar")

            //agregarle un cuaddro de texto
            // donde el usuario va a escrivir el nuevo nombre
            val cuadroTexto = EditText(context)
            cuadroTexto.setHint((item.Titulo))
            builder.setView(cuadroTexto)

            val dialog = builder.create()
            dialog.show()

        }
    }

}

