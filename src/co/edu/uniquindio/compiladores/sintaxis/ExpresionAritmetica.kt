package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class ExpresionAritmetica() : Expresion()
{
    var expAritmetica1 : ExpresionAritmetica? = null
    var expAritmetica2 : ExpresionAritmetica? = null
    var operador: Token? = null
    var valor : ValorNumerico? = null

    constructor(expAritmetica1:ExpresionAritmetica?,operador:Token?,expAritmetica2:ExpresionAritmetica?):this(){
        this.expAritmetica1= expAritmetica1
        this.operador = operador
        this.expAritmetica2= expAritmetica2
    }
    constructor(expAritmetica1:ExpresionAritmetica?):this(){
        this.expAritmetica1= expAritmetica1
    }
    constructor(valor: ValorNumerico?,operador:Token?,expAritmetica2:ExpresionAritmetica?):this(){
        this.valor= valor
        this.operador = operador
        this.expAritmetica2= expAritmetica2
    }

    constructor(valor: ValorNumerico?):this(){
        this.valor= valor
    }

    override fun getArbolVisual (): TreeItem<String>
    {
        var raiz = TreeItem("Expresion Aritmetica")

        if (expAritmetica1 != null && operador != null && expAritmetica2 != null)
        {
            raiz.children.add(expAritmetica1!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador Aritmetico: ${operador!!.lexema}"))
            raiz.children.add(expAritmetica2!!.getArbolVisual())

        }else if(expAritmetica1!=null)
        {
            raiz.children.add(expAritmetica1!!.getArbolVisual())

        }else if(valor != null && operador != null && expAritmetica2 != null)
        {
            raiz.children.add(valor!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador Aritmetico: ${operador!!.lexema}"))
            raiz.children.add(expAritmetica2!!.getArbolVisual())

        }else if(valor != null)
        {
            raiz.children.add(valor!!.getArbolVisual())
        }

        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:Ambito):String
    {
        if( expAritmetica1 != null && expAritmetica2 != null)
        {
            var tipo1 = expAritmetica1!!.obtenerTipo(tablaSimbolos, ambito)
            var tipo2 = expAritmetica2!!.obtenerTipo(tablaSimbolos, ambito)

            if( tipo1 == "dec" || tipo2 == "dec")
            {
                return "dec"
            }else
            {
                return "epn"
            }

        }else if( expAritmetica1 != null)
        {
            return expAritmetica1!!.obtenerTipo(tablaSimbolos, ambito)

        }else if(valor != null && expAritmetica1 != null)
        {
            var tipo1 = obtenerTipoCampo(tablaSimbolos, ambito)
            var tipo2 = expAritmetica2!!.obtenerTipo(tablaSimbolos, ambito)

            if( tipo1 == "dec" || tipo2 == "dec")
            {
                return "dec"
            }else
            {
                return "epn"
            }

        }else if( valor != null)
        {
            return obtenerTipoCampo(tablaSimbolos, ambito)
        }
        return ""
    }

    fun obtenerTipoCampo(tablaSimbolos: TablaSimbolos, ambito: Ambito):String
    {
        if(valor!!.valor.categoria == Categoria.ENTERO)
        {
            return "epn"
        }else if(valor!!.valor.categoria == Categoria.DECIMAL)
        {
            return "dec"
        }else
        {
            var simbolo = tablaSimbolos.buscarSimboloValor(valor!!.valor.lexema, ambito, valor!!.valor.fila, valor!!.valor.columna)

            if(simbolo != null)
            {
                return simbolo.tipoDato
            }
        }
        return ""
    }

    override fun getJavaCode(): String
    {

        if (expAritmetica1 != null && operador != null && expAritmetica2 != null)
        {
            return "("+expAritmetica1!!.getJavaCode()+")"+operador!!.getJavaCode()+expAritmetica2!!.getJavaCode()
        }else if(expAritmetica1!=null)
        {
            return "("+expAritmetica1!!.getJavaCode()+")"
        }else if(valor != null && operador != null && expAritmetica2 != null)
        {
            return valor!!.getJavaCode()+operador!!.getJavaCode()+expAritmetica2!!.getJavaCode()
        }else
        {
            return valor!!.getJavaCode()
        }
    }

    override fun toString(): String {
        return "ExpresionAritmetica(expAritmetica1=$expAritmetica1, expAritmetica2=$expAritmetica2, operador=$operador, valor=$valor)"
    }


}