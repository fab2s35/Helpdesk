package modelo

import java.sql.Connection
import java.sql.DriverManager

class ClaseConexion {

    fun cadenaConexion(): Connection?{
        try {

            val ip = "jdbc:oracle:thin:@192.168.1.2:1521:xe"
            val usuario = "System"
            val contrasena = "1234"

            val conexion = DriverManager.getConnection(ip, usuario, contrasena)
            return conexion
        }catch (e: Exception){
            println("Este es el error: $e")
            return null
        }
    }

}