package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Lectura(var expresion:ExpresionCadena?, var nombreVariable:Token?) : Sentencia()
{
    private var simbolo:Simbolo? = null

    override fun getArbolVisual (): TreeItem<String>
    {
        var raiz = TreeItem("Lectura")

        if(expresion != null)
        {
            raiz.children.add(expresion!!.getArbolVisual())
        }

        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito): String
    {
        return simbolo!!.tipoDato
    }

    override fun getJavaCode(): String {

        if(simbolo!!.tipoDato == "epn"){

            return "Integer.parseInt(JOptionPane.showInputDialog(null, ${expresion!!.getJavaCode()}))"
        }else if(simbolo!!.tipoDato == "fot"){

            return "Boolean.parseBoolean(JOptionPane.showInputDialog(null, ${expresion!!.getJavaCode()}))"
        }else if(simbolo!!.tipoDato == "dec"){
            return "Double.parseDouble(JOptionPane.showInputDialog(null, ${expresion!!.getJavaCode()}))"

        }else{
            return "JOptionPane.showInputDialog(null, ${expresion!!.getJavaCode()})"
        }

        return ""
    }

    override fun toString(): String {
        return "Lectura(expresion=$expresion)"
    }
}