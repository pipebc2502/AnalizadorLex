package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos

class UnidadDeCompilacion (var clase:Clase) {
    fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Unidad de compilacion")

        raiz.children.add(clase.getArbolVisual())

        return raiz
    }

    fun llenarTablaSimbolos(tablaSimbolos:TablaSimbolos, listaErrores:ArrayList<Error>)
    {
        var ambito = Ambito("Unidad de Compilacion", null, null, null,null)
        clase.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito)
    }

    fun getJavaCode():String
    {
        return clase.getJavaCode()
    }

    override fun toString(): String {
        return "UnidadDeCompilacion(clase=$clase)"
    }
}