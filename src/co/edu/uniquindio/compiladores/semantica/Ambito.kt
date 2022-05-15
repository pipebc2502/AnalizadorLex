package co.edu.uniquindio.compiladores.semantica

import co.edu.uniquindio.compiladores.sintaxis.Clase
import co.edu.uniquindio.compiladores.sintaxis.Metodo
import co.edu.uniquindio.compiladores.sintaxis.Sentencia

class Ambito (var nombre:String?, var clase:Clase?, var metodo:Metodo?, var sentencia:Sentencia?, var ambito: Ambito?)
{
    override fun toString(): String
    {
        if(nombre != null && clase != null && metodo == null &&  sentencia == null)
        {
            return "($nombre, Clase)"
        }
        if( nombre != null && clase != null && metodo != null && sentencia == null )
        {
            return "($nombre, Clase, metodo=${metodo.toString()})"
        }
        if(nombre != null && clase != null && metodo == null && sentencia != null)
        {
            return "($nombre, Clase, sentecia=${sentencia.toString()})"
        }
        if(nombre != null && clase != null && metodo != null && sentencia != null && ambito == null)
        {
            return "($nombre, Clase, metodo=${metodo.toString()}, sentencia=${sentencia.toString()})"
        }
        if(nombre != null && clase !=null && metodo != null && sentencia != null && ambito != null)
        {
            return "($nombre, Clase, metodo=${metodo.toString()}, sentencia=${sentencia.toString()}, ambito=${ambito.toString()})"
        }

        return ""
    }

    @Override
    fun equals(nombre1: String?,clase1: Clase?, metodo1: Metodo?, sentencia1: Sentencia? ): Boolean
    {

        if (nombre == nombre1 && clase == clase1  && metodo == metodo1 && sentencia == sentencia1 )
        {
            return true
        }

        return false
    }
}