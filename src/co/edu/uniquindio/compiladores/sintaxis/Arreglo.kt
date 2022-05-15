package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Arreglo(var nombre:Token?, var argumentos:ArrayList<Expresion>?) :Sentencia()
{
    override fun getArbolVisual (): TreeItem<String>
    {
        var raiz = TreeItem("Arreglo")

        if( nombre != null)
        {
            raiz.children.add(TreeItem("Nombre: ${nombre!!.lexema}"))
        }

        var raizArgumentos = TreeItem("Argumentos")

        if( argumentos != null)
        {
            for( a in argumentos!!)
            {
                raizArgumentos.children.add(a.getArbolVisual())
            }
            raiz.children.add(raizArgumentos)
        }

        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores: ArrayList<Error>, ambito: Ambito)
    {
        tablaSimbolos.guardarSimboloValor(nombre!!.lexema, "Arreglo", false, ambito, nombre!!.fila, nombre!!.columna)
    }

    override fun analizarSemantica(tablaSimbolos:TablaSimbolos, listaErrores:ArrayList<Error>, ambito: Ambito)
    {
        for( a in argumentos!!)
        {
            a.analizarSemantica(tablaSimbolos, listaErrores, ambito)
        }
    }

    override fun getJavaCode(): String {
        var codigo = "Object[] "+nombre!!.getJavaCode()+" = {"

        for(e in argumentos!!)
        {
            codigo += e.getJavaCode()+","
        }
        codigo = codigo.substring(0, codigo.length-1)

        codigo += "};"

        return codigo
    }

    override fun toString(): String {
        return "Arreglo(nombre=$nombre, argumentos=$argumentos)"
    }
}