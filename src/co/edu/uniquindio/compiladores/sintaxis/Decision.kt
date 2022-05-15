package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Decision(var expresionLogica:ExpresionLogica?, var listaSentencia:ArrayList<Sentencia>,var listaSentenciaElse:ArrayList<Sentencia>?) : Sentencia()
{
    override fun toString(): String {
        return "Decision(expresionLogica=$expresionLogica, listaSentencia=$listaSentencia, listaSentenciaElse=$listaSentenciaElse)"
    }

    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Decision")

        if(expresionLogica != null)
        {
            var condicion = TreeItem("Condicion")
            condicion.children.add(expresionLogica!!.getArbolVisual())
            raiz.children.add(condicion)
        }

        var raizTrue = TreeItem("Sentencias Verdaderas")

        for (s in listaSentencia)
        {
            raizTrue.children.add(s.getArbolVisual())
        }

        raiz.children.add(raizTrue)

        var raizFalse = TreeItem("Sentencias Falsas")

        if( listaSentenciaElse != null)
        {
            var raizFalse = TreeItem("Sentencias Falsas")

            for (s in listaSentenciaElse!!)
            {
                raizFalse.children.add(s.getArbolVisual())
            }

            raiz.children.add(raizFalse)
        }



        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito)
    {
        var ambito2 = Ambito(ambito.nombre, ambito.clase, ambito.metodo, this, ambito)
        for( s in listaSentencia!!)
        {
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito2)
        }
        if(listaSentenciaElse != null)
        {
            for( s in listaSentenciaElse!!)
            {
                s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito2)
            }
        }
    }

    override fun getJavaCode(): String {
        var codigo = "if ("+expresionLogica!!.getJavaCode()+"){"

        for(s in listaSentencia!!)
        {
            codigo += s.getJavaCode()
        }
        codigo += "}"

        if(listaSentenciaElse != null)
        {
            codigo += "else {"

            for(s in listaSentenciaElse!!)
            {
                codigo += s.getJavaCode()
            }
            codigo += "}"
        }

        return codigo
    }
}