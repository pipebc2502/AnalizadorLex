package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionRelacional():Expresion()
{
    var expAritmetica1 : ExpresionAritmetica? = null
    var expAritmetica2 : ExpresionAritmetica? = null
    var operador: Token? = null
    var valor: Token? = null

    constructor(expAritmetica1:ExpresionAritmetica?,operador:Token?,expAritmetica2:ExpresionAritmetica?):this(){
        this.expAritmetica1= expAritmetica1
        this.operador = operador
        this.expAritmetica2= expAritmetica2
    }

    constructor(valor:Token?):this()
    {
        this.valor= valor
    }

    override fun getArbolVisual (): TreeItem<String>
    {
        var raiz = TreeItem("Expresion Relacional")

        if(valor != null)
        {
            raiz.children.add(TreeItem("${valor!!.lexema}"))
        }else
        {
            raiz.children.add(expAritmetica1!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador Relacional: ${operador!!.lexema}"))
            raiz.children.add(expAritmetica2!!.getArbolVisual())
        }
        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:Ambito):String
    {
        return "fot"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {
        if(expAritmetica1 != null && expAritmetica2 != null)
        {
            expAritmetica1!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
            expAritmetica2!!.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }



    override fun toString(): String
    {
        return "ExpresionRelacional(expAritmetica1=$expAritmetica1, expAritmetica2=$expAritmetica2, operador=$operador, valor=$valor)"
    }

    override fun getJavaCode(): String {
        if(expAritmetica1 != null && expAritmetica2 != null)
        {
            return expAritmetica1!!.getJavaCode()+operador!!.getJavaCode()+expAritmetica2!!.getJavaCode()
        }else
        {
            return valor!!.getJavaCode()
        }
    }
}