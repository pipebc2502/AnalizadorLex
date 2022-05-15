package co.edu.uniquindio.compiladores.sintaxis
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos

import javafx.scene.control.TreeItem

class ExpresionLogica() : Expresion()
{
    var expRelacional1: ExpresionRelacional? = null
    var expLogica1: ExpresionLogica? = null
    var expLogica2: ExpresionLogica? = null
    var operador1 : Token? = null
    var operador2 : Token? = null

    constructor(expRelacional1:ExpresionRelacional?,operador1:Token?,expLogica1:ExpresionLogica?):this()
    {
        this.expRelacional1= expRelacional1
        this.operador1 = operador1
        this.expLogica1= expLogica1
    }
    constructor(expRelacional1:ExpresionRelacional?):this()
    {
        this.expRelacional1= expRelacional1
    }
    constructor(operador1:Token?,expLogica1: ExpresionLogica?):this(){
        this.operador1 = operador1
        this.expLogica1= expLogica1
    }
    constructor(operador1:Token?,expLogica1: ExpresionLogica?,operador2: Token?,expLogica2:ExpresionLogica?):this()
    {
        this.operador1 = operador1
        this.expLogica1= expLogica1
        this.operador2 = operador2
        this.expLogica2 = expLogica2
    }



    override open fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Expresion Logica")

        if(expRelacional1 != null && operador1 != null && expLogica1 != null)
        {
            raiz.children.add(expRelacional1!!.getArbolVisual())
            raiz.children.add(TreeItem("Operador Logico: ${operador1!!.lexema}"))
            raiz.children.add(expLogica1!!.getArbolVisual())

        }else if(expRelacional1 != null)
        {
           raiz.children.add(expRelacional1!!.getArbolVisual())

        }else if(operador1 != null && expLogica1 != null && operador2 != null && expLogica2 != null)
        {
            var raizNegacion = TreeItem("Operador Logico: ${operador1!!.lexema}")
            raizNegacion.children.add(expLogica1!!.getArbolVisual())
            raiz.children.add(raizNegacion)
            raiz.children.add(TreeItem("Operador Logico: ${operador2!!.lexema}"))
            raiz.children.add(expLogica2!!.getArbolVisual())

        }else if(operador1 != null && expLogica1 != null)
        {
            var raizNegacion = TreeItem("Operador Logico: ${operador1!!.lexema}")
            raizNegacion.children.add(expLogica1!!.getArbolVisual())
            raiz.children.add(raizNegacion)
        }

        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:Ambito):String
    {
        return "fot"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito) {

        if(expRelacional1 != null &&  expLogica1 != null)
        {
            expRelacional1!!.analizarSemantica(tablaSimbolos,listaErrores,ambito)
            expLogica1!!.analizarSemantica(tablaSimbolos,listaErrores,ambito)

        }else if( expLogica1 != null && expLogica2 != null){
            expLogica1!!.analizarSemantica(tablaSimbolos,listaErrores,ambito)
            expLogica2!!.analizarSemantica(tablaSimbolos,listaErrores,ambito)

        }else if( expLogica1 != null ){
            expLogica1!!.analizarSemantica(tablaSimbolos,listaErrores,ambito)

        }else if( expLogica2 != null ){
            expLogica2!!.analizarSemantica(tablaSimbolos,listaErrores,ambito)

        }else{
            expRelacional1!!.analizarSemantica(tablaSimbolos,listaErrores,ambito)
        }
    }

    override fun getJavaCode(): String {
        if(expRelacional1 != null && operador1 != null && expLogica1 != null)
        {
            return expRelacional1!!.getJavaCode()+operador1!!.getJavaCode()+expLogica1!!.getJavaCode()
        }else if(operador1 != null && expLogica1 != null && operador2 != null && expLogica2 != null)
        {
            return operador1!!.getJavaCode()+"("+expLogica1!!.getJavaCode()+") "+operador2!!.getJavaCode()+" "+expLogica2!!.getJavaCode()
        }else if(operador1 != null && expLogica1 != null)
        {
            return operador1!!.getJavaCode()+"("+expLogica1!!.getJavaCode()+")"
        }else
        {
            return expRelacional1!!.getJavaCode()
        }
    }
}