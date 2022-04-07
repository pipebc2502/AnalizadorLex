package co.edu.uniquindio.compiladores.aplication

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico

fun main (){
    val lexico = AnalizadorLexico (codigoFuente = "imp$")
    lexico.esPalReservadaImprimir()
    print(lexico.listaTokens)
}