package co.edu.uniquindio.compiladores.aplication

fun main (){
    val lexico = AnalizadorLexico (codigoFuente = "+")
    lexico.esOperadorRelacional()
    print(lexico.listaTokens)
}