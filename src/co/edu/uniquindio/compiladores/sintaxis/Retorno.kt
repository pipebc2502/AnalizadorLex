package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Retorno(var identificador:Token?) : Sentencia()
{
    override fun getArbolVisual (): TreeItem<String>
    {
        var raiz = TreeItem("Retorno")

        if( identificador != null)
        {
            raiz.children.add(TreeItem("Identificador: ${identificador!!.lexema}"))
        }
        return raiz
    }

    override fun tipo(): String
    {
        return "Retorno"
    }



    override fun getJavaCode(): String {
        return "return "+identificador!!.getJavaCode()+";"
    }

    override fun toString(): String {
        return "Retorno(identifiador=$identificador)"
    }

}