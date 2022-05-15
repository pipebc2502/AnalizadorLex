package co.edu.uniquindio.compiladores.sintaxis
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

class Procedimiento( var nombreProcedimiento:Token,var listaParametros:ArrayList<Parametro>,var listaSentencias:ArrayList<Sentencia>) : Metodo()
{
    override fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Procedimiento")

        raiz.children.add(TreeItem("Nombre: ${nombreProcedimiento.lexema}"))

        var raizParametros = TreeItem("Parametros")

        for (p in listaParametros)
        {
            raizParametros.children.add(p.getArbolVisual())
        }

        raiz.children.add(raizParametros)

        var raizSentencias = TreeItem("Sentencias")

        for (s in listaSentencias) {
            raizSentencias.children.add(s.getArbolVisual())
        }

        raiz.children.add(raizSentencias)

        return raiz
    }

    fun obtenerTiposParametros():ArrayList<String>
    {
        var lista = ArrayList<String>()
        for(p in listaParametros)
        {
            lista.add(p.tipoDato.lexema)
        }
        return lista
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores:ArrayList<Error>, ambito:Ambito)
    {
        tablaSimbolos.guardarSimboloMetodo(nombreProcedimiento.lexema, "", obtenerTiposParametros(), ambito, nombreProcedimiento.fila, nombreProcedimiento.columna)

        var ambito2 = Ambito(ambito.nombre, ambito.clase, this, null, ambito)

        for (p in listaParametros)
        {
            tablaSimbolos.guardarSimboloValor(p.nombre.lexema,p.tipoDato.lexema,true ,ambito2, p.nombre.fila, p.nombre.columna)
        }

        for(s in listaSentencias)
        {
            s.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito2)
        }
    }

    override fun getJavaCode(): String
    {
        var codigo = ""
        //Main en Java
        if(nombreProcedimiento.lexema == "principarß")
        {
            codigo = "public static void main(String[] args){"
        }else
        {
            codigo = "static void"+" "+nombreProcedimiento.getJavaCode()+ "("

            if(listaParametros.isNotEmpty())
            {
                for(p in listaParametros)
                {
                    codigo += p.getJavaCode()+", "
                }

                codigo = codigo.substring(0, codigo.length-2)
            }

            codigo += "){"
        }

        for(s in listaSentencias)
        {
            codigo += s.getJavaCode()
        }
        codigo += "}"

        return codigo
    }

    override fun toString(): String {
        return "Procedimiento(nombreProcedimiento=$nombreProcedimiento, listaParametros=$listaParametros, listaSentencias=$listaSentencias)"
    }
}