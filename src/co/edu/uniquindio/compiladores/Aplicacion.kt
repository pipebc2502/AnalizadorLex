package co.edu.uniquindio.compiladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintatico

fun main()
{
    var lexico = AnalizadorLexico("Fun epn sumarø[  ]( ar! a# : [-5-] )")
    lexico.analizar()

    println(lexico.listaTokens)

    val sintaxis = AnalizadorSintatico( lexico.listaTokens)
    print(sintaxis.esUnidadDeCompilacion())
    print( sintaxis.listaErrores)


}