package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class InvocacionProcedimiento (var nombreProc: Token, var listaArgumentos:ArrayList<Expresion>) : Sentencia()
{
    override fun getArbolVisual (): TreeItem<String>
    {
        var raiz = TreeItem("Invocacion")

        raiz.children.add(TreeItem("Nombre: ${nombreProc.lexema}"))

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


    override fun getJavaCode(): String
    {
        var codigo = nombreProc.getJavaCode()+"("

        if(listaArgumentos.isNotEmpty())
        {
            for(p in listaArgumentos)
            {
                codigo += p.getJavaCode()+", "
            }

            codigo = codigo.substring(0, codigo.length-2)
        }
        codigo += ");"
        return codigo
    }

    override fun toString(): String {
        return "InvocacionProcedimiento(nombreProc=$nombreProc, listaArgumentos=$listaArgumentos)"
    }
}