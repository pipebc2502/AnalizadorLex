package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ValorNumerico (var valor:Token)
{
    fun getArbolVisual(): TreeItem<String>
    {
        return TreeItem(valor.lexema)
    }

    fun getJavaCode():String
    {
        return valor.getJavaCode()
    }
}