package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class InvocacionFuncion(var nombreFuncion:Token, var listaArgumentos:ArrayList<Expresion>) : Sentencia()
{
    override fun toString(): String
    {
        return "InvocacionFuncion(nombreFuncion=$nombreFuncion, listaArgumentos=$listaArgumentos)"
    }

    override fun getArbolVisual (): TreeItem<String>
    {
        var raiz = TreeItem("Invocacion")

        raiz.children.add(TreeItem("Nombre: ${nombreFuncion.lexema}"))

        var raizArgumentos = TreeItem("Argumentos")

        for( a in listaArgumentos)
        {
            raizArgumentos.children.add(a.getArbolVisual())
        }
        raiz.children.add(raizArgumentos)

        return raiz
    }

    fun obtenerTiposArgumentos(tablaSimbolos: TablaSimbolos, ambito: Ambito):ArrayList<String>
    {
        var listaArgs = ArrayList<String>()
        for(a in listaArgumentos)
        {
            listaArgs.add(a.obtenerTipo(tablaSimbolos, ambito))
        }
        return listaArgs
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito: Ambito): String
    {
        var simbolo = tablaSimbolos.buscarSimboloMetodo(nombreFuncion.lexema, obtenerTiposArgumentos(tablaSimbolos, ambito))

        if(simbolo != null)
        {
            return simbolo.tipoDato
        }

        return ""
    }



    override fun getJavaCode(): String
    {
        var codigo = nombreFuncion.getJavaCode()+"("

        if(listaArgumentos.isNotEmpty())
        {
            for(p in listaArgumentos)
            {
                codigo += p.getJavaCode()+", "
            }

            codigo = codigo.substring(0, codigo.length-2)
        }
        codigo += ")"
        return codigo
    }
}