package co.edu.uniquindio.compiladores.aplication

class AnalizadorLexico (var codigoFuente:String) {

    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0

    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) = listaTokens.add(Token(lexema, categoria, fila,columna))

    fun esEntero():Boolean{

        if (caracterActual.isDigit()){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual

            lexema+=caracterActual
            obtenerSiguienteCaracter()

            while(caracterActual.isDigit()){
                lexema+=caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun obtenerSiguienteCaracter()
    {
        if(posicionActual == codigoFuente.length-1)
        {
            caracterActual = finCodigo
        }else
        {
            if(caracterActual == '\n')
            {
                filaActual++
                columnaActual = 0
            }else
            {
                columnaActual++
            }

            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }
    }

}