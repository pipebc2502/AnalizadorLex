package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem
import co.edu.uniquindio.compiladores.semantica.Ambito
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos

class Clase (var listaMetodos:ArrayList<Metodo>, var listaVariables:ArrayList<DeclaracionVariable>) {
    fun getArbolVisual(): TreeItem<String>
    {
        var raiz = TreeItem("Clase")

        for ( v in listaVariables)
        {
            raiz.children.add(v.getArbolVisual())
        }

        for ( m in listaMetodos)
        {
            raiz.children.add(m.getArbolVisual())
        }

        return raiz
    }

    fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, listaErrores:ArrayList<Error>, ambito:Ambito)
    {
        var ambito2 = Ambito(ambito.nombre, this, null, null,ambito)
        for( v in listaVariables)
        {
            v.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito2)
        }

        for( f in listaMetodos)
        {
            f.llenarTablaSimbolos(tablaSimbolos, listaErrores, ambito2)
        }
    }

    fun getJavaCode ():String
    {
        var codigo = "import javax.swing.JOptionPane; public class Principal{"
        var metodo = ""

        for (v in listaVariables)
        {
            codigo += "static "+v.getJavaCode()
        }

        for (f in listaMetodos)
        {
            codigo += f.getJavaCode()
        }


        var contener: Boolean = codigo.contains("duplicarMetodo(")
        if(contener == true) {

            codigo += "static String duplicarMetodo ( String cadena, int a ){ String cad2=\"\"; for(int i =0; i<=a; i++)"+
                    "{cad2+=\"\" + cadena; }return cad2;}"

        }
        var contener2: Boolean = codigo.contains("sumarMetodo(")
        if(contener2 == true)
        {
            codigo += "static int sumarMetodo(int inicio, int fin) { int res=0; for(int i =inicio; i<=fin;i++)"+
                    "{res+=i;} return res;}"

        }
        var contener3: Boolean = codigo.contains("restarMetodo(")
        if(contener3==true)
        {
            codigo+= "static int restarMetodo(int inicio, int fin){int res=fin; for(int i= fin-1;"+
                    "i>=inicio; i--){ res -=i;} return res; } "
        }

        codigo += "}"

        return codigo
    }

    override fun toString(): String {
        return "Clase(listaMetodos=$listaMetodos, listaVariables=$listaVariables)"
    }
}