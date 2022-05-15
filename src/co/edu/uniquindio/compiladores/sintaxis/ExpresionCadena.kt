package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem


class ExpresionCadena() : Expresion()
{
    var cadena:Token? = null
    var expresion:Expresion? = null
    var identificador:Token? = null

    constructor(cadena:Token, expresion: Expresion?):this()
    {
        this.cadena = cadena
        this.expresion = expresion
    }

    constructor(identificador:Token):this()
    {
        this.identificador = identificador
    }

    override fun getArbolVisual (): TreeItem<String>
    {
        var raiz = TreeItem("Cadena")

        if(cadena != null && expresion != null)
        {
            raiz.children.add(TreeItem(cadena!!.lexema))
            raiz.children.add(expresion!!.getArbolVisual())
        }else if(identificador != null)
        {
            raiz.children.add(TreeItem(identificador!!.lexema))
        }
        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:Ambito):String
    {
        return "cad"
    }


    override fun getJavaCode(): String {

        var codigo = ""

        if( cadena != null)
        {
            codigo += cadena!!.getJavaCode()
        }
        if(identificador != null)
        {
            codigo += identificador!!.getJavaCode()
        }

        if(expresion != null)
        {
            codigo += "+"+expresion!!.getJavaCode()
        }

        return codigo
    }
}