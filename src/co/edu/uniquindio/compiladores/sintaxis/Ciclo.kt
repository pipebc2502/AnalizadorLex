package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Ciclo(var expresionLogica:ExpresionLogica?, var listaSentencia:ArrayList<Sentencia>?) : Sentencia()
{
    override fun getArbolVisual (): TreeItem<String>
    {

        var raiz = TreeItem("Ciclo")

        if(expresionLogica != null)
        {
            raiz.children.add(expresionLogica!!.getArbolVisual())
        }

        var raizSentencias = TreeItem("Sentencias")

        if(listaSentencia != null)
        {
            for( s in listaSentencia!!)
            {
                raizSentencias.children.add(s.getArbolVisual())
            }
        }

        raiz.children.add(raizSentencias)

        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito)
    {
        var ambito2 = Ambito(ambito.nombre, ambito.clase, ambito.metodo, this, ambito)
        for( s in listaSentencia!!)
        {
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito2)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito)
    {
        var ambito2 = Ambito(ambito.nombre, ambito.clase, ambito.metodo, this, ambito)
        if(expresionLogica != null)
        {
            expresionLogica!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            for( s in listaSentencia!!)
            {
                s.analizarSemantica(tablaSimbolos, listaErrores, ambito2)
            }
        }
    }

    override fun getJavaCode(): String {
        var codigo = "while ("+expresionLogica!!.getJavaCode()+"){"

        for(s in listaSentencia!!)
        {
            codigo += s.getJavaCode()
        }
        codigo += "}"

        return codigo
    }

    override fun toString(): String
    {
        return "Ciclo(expresionLogica=$expresionLogica, listaSentencia=$listaSentencia)"
    }

}