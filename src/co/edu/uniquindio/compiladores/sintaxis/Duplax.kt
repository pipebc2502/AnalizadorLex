package co.edu.uniquindio.compiladores.sintaxis


import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Duplax(var variable:Token?, var numero : ValorNumerico?) : Sentencia()
{
    override fun getArbolVisual (): TreeItem<String>
    {
        var raiz = TreeItem("Duplax")

        if(variable != null)
        {
            raiz.children.add(TreeItem("Variable: ${variable!!.lexema}"))
        }

        if(numero != null)
        {
            var raizValorNumerico = TreeItem("Valor Numerico")
            raizValorNumerico.children.add(numero!!.getArbolVisual())

            raiz.children.add(raizValorNumerico)
        }

        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito): String
    {
        return "cad"
    }


    override fun getJavaCode(): String
    {
        var codigo = "duplicarMetodo( "+ variable!!.getJavaCode() + "," + numero!!.getJavaCode() + ")"

        return codigo
    }

    override fun toString(): String {
        return "Duplax(varia=$variable, expresion=$numero)"
    }
}