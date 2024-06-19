package RecycleViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fabiola.osorto.helpdesk.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val txtTitulo: TextView = view.findViewById(R.id.txtNombreCard)
    val imgBorrar: ImageView = view.findViewById(R.id.imgBorrar)
    val imgEditar: ImageView = view.findViewById(R.id.imgEditar)

}