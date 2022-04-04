package co.edu.uniquindio.compiladores.aplication

fun main (){
    val lexico = AnalizadorLexico (codigoFuente = "123456789")
    lexico.esEntero()
    print(lexico.listaTokens)
}