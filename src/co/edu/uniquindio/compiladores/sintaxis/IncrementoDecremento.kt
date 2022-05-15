package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class IncrementoDecremento (var variable: Token, var operador: Token): Sentencia()
{
    override fun toString(): String {
        return "IncrementoDecremento(variable=$variable, operador=$operador)"
    }

    override fun getArbolVisual (): TreeItem<String> {

        var raiz = TreeItem("IncrementoDecremento")

        raiz.children.add(TreeItem("Variable: ${variable.lexema}"))
        raiz.children.add(TreeItem("Operador: ${operador.lexema}"))

        return raiz
    }

    override fun getJavaCode(): String {

        return variable.getJavaCode()+ operador.getJavaCode() + ";"
    }
}