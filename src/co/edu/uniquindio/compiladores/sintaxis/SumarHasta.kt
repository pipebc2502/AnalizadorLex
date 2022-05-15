package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class SumarHasta(var inicio : ValorNumerico?, var final : ValorNumerico?) :Sentencia()
{
    override fun getArbolVisual (): TreeItem<String>
    {
        var raiz = TreeItem("SumarHasta")

        if(inicio != null)
        {
            var raizInicio = TreeItem("Valor inicial")
            raizInicio.children.add(inicio!!.getArbolVisual())
            raiz.children.add(raizInicio)
        }

        if(final != null)
        {
            var raizFinal = TreeItem("Valor Final")
            raizFinal.children.add(final!!.getArbolVisual())
            raiz.children.add(raizFinal)
        }

        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito): String
    {
        return "epn"
    }



    override fun getJavaCode(): String {
        var codigo = "sumarMetodo("+ inicio!!.getJavaCode() + ","+ final!!.getJavaCode() + ")"
        return codigo
    }

    override fun toString(): String {
        return "SumarHasta(inicio=$inicio, final=$final)"
    }
}