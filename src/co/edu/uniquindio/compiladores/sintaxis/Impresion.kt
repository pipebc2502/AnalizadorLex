package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Impresion(var expresion:ExpresionCadena?) : Sentencia()
{
    override fun toString(): String {
        return "Impresion(expresion=$expresion)"
    }

    override fun getArbolVisual (): TreeItem<String>
    {

        var raiz = TreeItem("Impresion")

        if(expresion != null)
        {
            raiz.children.add(expresion!!.getArbolVisual())
        }

        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito)
    {
        expresion!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
    }

    override fun getJavaCode(): String {
        return "JOptionPane.showMessageDialog(null, "+expresion!!.getJavaCode()+");"
    }
}