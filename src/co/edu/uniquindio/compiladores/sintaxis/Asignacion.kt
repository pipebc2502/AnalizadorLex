package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Asignacion (var nombreAsignacion:Token, var expresion:Expresion?, var sentencia:Sentencia?):Sentencia()
{
    override fun getArbolVisual (): TreeItem<String>
    {

        var raiz = TreeItem("Asignacion")

        raiz.children.add(TreeItem("Identificador: ${nombreAsignacion.lexema}"))

        if(expresion!= null)
        {
            var exp = TreeItem("Expresion")
            exp.children.add(expresion!!.getArbolVisual())

            raiz.children.add(exp)

        }else if(sentencia!= null)
        {
            var sen = TreeItem("Sentencia")
            sen.children.add(sentencia!!.getArbolVisual())

            raiz.children.add(sen)
        }
        return raiz
    }


    override fun getJavaCode(): String {

        var codigo = nombreAsignacion!!.getJavaCode()

        if(expresion != null)
        {
            codigo += " = "+expresion!!.getJavaCode()
        }else if(sentencia !=null){
            codigo += " = "+sentencia!!.getJavaCode()
        }
        codigo += ";"

        return codigo
    }

    override fun toString(): String {
        return "Asignacion(nombreAsignacion=$nombreAsignacion, expresion=$expresion, sentencia=$sentencia)"
    }
}