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
    /**
     * Determina si la expresion es un operador logico
     */
    fun esOperadorLogico():Boolean{
        if(caracterActual == '~' || caracterActual == '$' || caracterActual == '¬'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            if(caracterActual == '~'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)

                if(caracterActual == '¬'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)

                if(caracterActual == '$'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                    return true
                }
                }
            }
        }
        return false
    }

    /**
     * Determina si la expresion es un operador relacional
     */
    fun esOperadorRelacional():Boolean{
        if(caracterActual == '+' || caracterActual == '-' || caracterActual == '<' || caracterActual == '!'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
            if(caracterActual == '-'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if(caracterActual == '>'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                    return true
                }
                almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                return true
            }
            if(caracterActual == '+'){
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == '>'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                    return true
                }
                if(caracterActual == '+'){
                    almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                    return false
                }

                return false

            }
            if(caracterActual == '<'){
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == '.'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                    return true
                }
            }
            if(caracterActual == '<'){
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == '<'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                    return true
                }
            }
        }
        return false
    }


    /**
     * Determina si la expresion es un operador aritmetico
     */
    fun esOperadorAritmetico():Boolean{
        if(caracterActual == '#'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 's' || caracterActual == 'r' || caracterActual == 'd' || caracterActual == 'p' || caracterActual == 'm'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                return true
            }
        }
        return false
    }
    /**
     * Determina si la expresion es un operador de asignacion
     */
    fun esOperadorAsignacion():Boolean {
        if (caracterActual == '>') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 's' || caracterActual == 'r' || caracterActual == 'd' || caracterActual == 'p' || caracterActual == 'm') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                return true
            }
        }
        return false
    }



    /**
     * Determina si la expresion es una llave izq
     */
    fun esLlaveIzq():Boolean{
        if(caracterActual == '¿'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.LLAVE_IZQUIERDA, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /**
     * Determina si la expresion es una llave derecha
     */
    fun esLlaveDerecha():Boolean{
        if(caracterActual == '?'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.LLAVE_DERECHA, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /**
     * Determina si la expresion es un corchete derecho
     */
    fun esCorcheteDerecha():Boolean{
        if(caracterActual == '!'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.CORCHETE_DERECHO, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /**
     * Determina si la expresion es un corchete izq
     */
    fun esCorcheteIzq():Boolean{
        if(caracterActual == '¡'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.CORCHETE_IZQUIERDO, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /**
     * Determina si la expresion es un parentesis izq
     */
    fun esParentesisIzq():Boolean{
        if(caracterActual == '['){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.PARENTESIS_IZQUIERDO, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    /**
     * Determina si la expresion es un parentesis derecho
     */
    fun esParentesisDerecho():Boolean{
        if(caracterActual == ']'){
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.PARENTESIS_DERECHO, filaInicial, columnaInicial)
            return true
        }
        return false
    }
}
