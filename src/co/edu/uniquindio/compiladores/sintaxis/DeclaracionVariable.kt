package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos

import javafx.scene.control.TreeItem


class DeclaracionVariable(var tipoDato:Token, var identifidor:Token?, var expresion:Expresion?, var sentencia:Sentencia?) :Sentencia()
{
    override fun getArbolVisual (): TreeItem<String>
    {
        var raiz = TreeItem("Declaracion de variable")

        raiz.children.add(TreeItem("Tipo De Dato: ${tipoDato.lexema}"))

        if( identifidor != null)
        {
            raiz.children.add(TreeItem("Identificador: ${identifidor!!.lexema}"))
        }

        if(expresion!= null)
        {
            var exp = TreeItem("Expresion")
            exp.children.add(expresion!!.getArbolVisual())

            raiz.children.add(exp)

        }else if(sentencia != null)
        {
            var inv = TreeItem("Sentencia")
            inv.children.add(sentencia!!.getArbolVisual())

            raiz.children.add(inv)
        }

        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito)
    {
        tablaSimbolos.guardarSimboloValor(identifidor!!.lexema, tipoDato.lexema, true, ambito, identifidor!!.fila, identifidor!!.columna)
    }

    override fun getJavaCode(): String {
        var codigo = tipoDato.getJavaCode()+" "+identifidor!!.getJavaCode()

        if(expresion != null)
        {
            codigo += " = "+expresion!!.getJavaCode()
        }else if(sentencia != null)
        {
            codigo += " = "+sentencia!!.getJavaCode()
        }
        codigo += ";"

        return codigo
    }

    override fun toString(): String {
        return "DeclaracionVariable(tipoDato=$tipoDato, identifidor=$identifidor, expresion=$expresion, sentencia=$sentencia)"
    }
}