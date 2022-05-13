package co.edu.uniquindio.compiladores.lexico

class AnalizadorLexico (var codigoFuente:String) {

    var posicionActual = 0
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0

    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) = listaTokens.add(Token(lexema, categoria, fila,columna))

    fun hacerBT(posicionInicial:Int, filaInicial:Int, columnaInicial:Int)
    {
        posicionActual = posicionInicial
        caracterActual = codigoFuente[posicionActual]
        filaActual = filaInicial
        columnaActual = columnaInicial
    }

    fun analizar() {
        while (caracterActual != finCodigo) {
            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n') {
                obtenerSiguienteCaracter()
                continue
            }

            if(esCadena()) continue
            if(esEntero()) continue
            if(esDecimal()) continue
            if(esCaracter()) continue
            if(esBoolean()) continue
            if(esOperadorEscape()) continue
            if(esOperadorAritmetico()) continue
            if(esOperadorAsignacion()) continue
            if(esOperadorRelacional()) continue
            if(esOperadorLogico()) continue
            if(esOperadorConcatenacion()) continue
            //if(esIdentificadorClase()) continue
            if(esIdentificadorVariable()) continue
            if(esIdentificadorMetodo()) continue
            if(esIdentificadorConstante()) continue
            if(esPalReservadaClase()) continue
            if(esPalReservadaMedProcedimiento()) continue
            if(esPalReservadaMedFuncion()) continue
            if(esPalReservadaCiclo()) continue
            if(esPalReservadaDecisiones()) continue
            if(esPalReservadaElse()) continue
            if(esPalReservadaDuplax()) continue
            if(esPalReservadaSumarHasta()) continue
            if(esPalReservadaRestarHasta()) continue
            if(esAgrupacion()) continue
            if(esBloque()) continue
            if(esTerminar()) continue
            if(esOperadorSeparacion()) continue
            if(esPalReservadaCadena()) continue
            if(esPalReservadaEntero()) continue
            if(esPalReservadaDecimales()) continue
            if(esPalReservadaBoolean()) continue
            if(esPalReservadaCaracter()) continue
            if(esPalReservadaImprimir()) continue
            if(esPalReservadaLectura()) continue
            if(esPalReservadaArreglo()) continue
            if(esPalReservadaRetorno()) continue
            if(esComentarioLinea()) continue
            if(esComentarioBloque()) continue
            if(esCorchete()) continue



            almacenarToken("" + caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
        }
    }

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

    fun esComentarioLinea(): Boolean
    {
        if(caracterActual == '*')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'L')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                while(caracterActual != finCodigo && caracterActual != '\n')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                almacenarToken(lexema, Categoria.COMENTARIO_DE_LINEA, filaInicial, columnaInicial)

                return true
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esComentarioBloque(): Boolean
    {
        if(caracterActual == '*')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'B')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                while(caracterActual != finCodigo)
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == '*')
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if(caracterActual == 'B')
                        {
                            lexema += caracterActual
                            almacenarToken(lexema, Categoria.COMENTARIO_DE_BLOQUE, filaInicial, columnaInicial)

                            obtenerSiguienteCaracter()
                            return true
                        }
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esCorchete(): Boolean
    {
        if(caracterActual == '{')
        {
            almacenarToken(""+caracterActual, Categoria.CORCHETE_IZQUIERDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
            return true

        }else if(caracterActual == '}')
        {
            almacenarToken(""+caracterActual, Categoria.CORCHETE_DERECHO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
            return true
        }
        return false
    }

    fun esPalReservadaDuplax() :Boolean
    {
        if(caracterActual == 'D')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'u')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'p')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == 'l')
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if(caracterActual == 'a')
                        {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()

                            if(caracterActual == 'x')
                            {
                                lexema += caracterActual
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_DUPLAX, filaInicial, columnaInicial)

                                obtenerSiguienteCaracter()
                                return true
                            }
                        }
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esPalReservadaSumarHasta() :Boolean
    {
        if(caracterActual == 'S')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'u')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'm')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == 'a')
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if(caracterActual == 'r')
                        {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()

                            if(caracterActual == 'H')
                            {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()

                                if(caracterActual == 'a')
                                {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()

                                    if(caracterActual == 's')
                                    {
                                        lexema += caracterActual
                                        obtenerSiguienteCaracter()

                                        if(caracterActual == 't')
                                        {
                                            lexema += caracterActual
                                            obtenerSiguienteCaracter()

                                            if(caracterActual == 'a')
                                            {
                                                lexema += caracterActual
                                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_SUMAR_HASTA, filaInicial, columnaInicial)

                                                obtenerSiguienteCaracter()
                                                return true
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esPalReservadaRestarHasta() :Boolean
    {
        if(caracterActual == 'R')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'e')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 's')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == 't')
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if(caracterActual == 'a')
                        {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()

                            if(caracterActual == 'r')
                            {
                                lexema += caracterActual
                                obtenerSiguienteCaracter()

                                if(caracterActual == 'H')
                                {
                                    lexema += caracterActual
                                    obtenerSiguienteCaracter()

                                    if(caracterActual == 'a')
                                    {
                                        lexema += caracterActual
                                        obtenerSiguienteCaracter()

                                        if(caracterActual == 's')
                                        {
                                            lexema += caracterActual
                                            obtenerSiguienteCaracter()

                                            if(caracterActual == 't')
                                            {
                                                lexema += caracterActual
                                                obtenerSiguienteCaracter()

                                                if(caracterActual == 'a')
                                                {
                                                    lexema += caracterActual
                                                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA_RESTAR_HASTA, filaInicial, columnaInicial)

                                                    obtenerSiguienteCaracter()
                                                    return true
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esIdentificadorConstante(): Boolean
    {
        if (caracterActual.isLetter())
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isLetter() || caracterActual.isDigit())
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }

            if (caracterActual == '\\')
            {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.IDENTIFICADOR_DE_CONSTANTE, filaInicial, columnaInicial)

                obtenerSiguienteCaracter()
                return true
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esPalReservadaClase(): Boolean
    {
        if(caracterActual == 'C')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'l')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'a')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == 's')
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if(caracterActual == 'e')
                        {
                            lexema += caracterActual
                            almacenarToken(lexema, Categoria.PALABRA_RESERVADA_DE_LA_CLASE, filaInicial, columnaInicial)

                            obtenerSiguienteCaracter()
                            return true
                        }
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esPalReservadaMedProcedimiento(): Boolean
    {
        if(caracterActual == 'P')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'r')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'o')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == 'c')
                    {
                        lexema += caracterActual
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA_DEL_METODO_PROCEDIMIENTO, filaInicial, columnaInicial)

                        obtenerSiguienteCaracter()
                        return true
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esAgrupacion(): Boolean
    {
        if(caracterActual == '[')
        {
            almacenarToken(""+caracterActual, Categoria.SIMBOLO_AGRUPACION_IZQUIERDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
            return true

        }else if(caracterActual == ']')
        {
            almacenarToken(""+caracterActual, Categoria.SIMBOLO_AGRUPACION_DERECHO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
            return true
        }

        return false
    }

    fun esBloque(): Boolean
    {
        if(caracterActual == '(')
        {
            almacenarToken(""+caracterActual, Categoria.SIMBOLO_BLOQUE_IZQUIERDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
            return true

        }else if(caracterActual == ')')
        {
            almacenarToken(""+caracterActual, Categoria.SIMBOLO_BLOQUE_DERECHO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
            return true
        }
        return false
    }

    fun esTerminar(): Boolean
    {
        if(caracterActual == 'T')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == '.')
            {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.TERMINAL, filaInicial, columnaInicial)

                obtenerSiguienteCaracter()
                return true
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esIdentificadorMetodo(): Boolean
    {
        if (caracterActual.isLetter())
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isLetter() || caracterActual.isDigit())
            {
                if (caracterActual == 'a' || caracterActual == 'e' || caracterActual == 'i')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual == 'r')
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if (caracterActual == 'ß' || caracterActual == 'ø')
                        {
                            lexema += caracterActual
                            almacenarToken(lexema, Categoria.IDENTIFICADOR_METODO, filaInicial, columnaInicial)

                            obtenerSiguienteCaracter()
                            return true
                        }
                    }
                }else
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esIdentificadorVariable(): Boolean
    {
        if (caracterActual.isLetter())
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual.isLetter() || caracterActual.isDigit())
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }

            if (caracterActual == '#')
            {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.IDENTIFICADOR_VARIABLE, filaInicial, columnaInicial)

                obtenerSiguienteCaracter()
                return true
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esOperadorConcatenacion(): Boolean
    {
        if(caracterActual == 'c')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == '+')
            {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.OPERADOR_DE_CONCATENACION, filaInicial, columnaInicial)

                obtenerSiguienteCaracter()
                return true
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esOperadorEscape(): Boolean
    {
        if(caracterActual == '*')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == '*'||caracterActual == '@'||caracterActual == 'S'||caracterActual == 'N'||caracterActual == 'C'||caracterActual == '_')
            {
                if(caracterActual == '*')
                {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_DE_ESCAPE_EN_CADENA, filaInicial, columnaInicial)
                    almacenarToken(lexema, Categoria.OPERADOR_DE_ESCAPE_EN_CARACTER, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }else if(caracterActual == '_')
                {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_DE_ESCAPE_EN_CARACTER, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }else
                {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.OPERADOR_DE_ESCAPE_EN_CADENA, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }

    fun esBoolean(): Boolean
    {
        if(caracterActual == '&')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'f')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'a')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == 'l')
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if(caracterActual == 's')
                        {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()
                        }else
                        {
                            hacerBT(posicionInicial, filaInicial, columnaInicial)
                            return false
                        }
                    }else
                    {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }else
                {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }

            }else if(caracterActual == 't')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'r')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == 'u')
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }else
                    {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }else
                {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }

            }else
            {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }

            if(caracterActual == 'e')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == '&')
                {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.BOOLEAN, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esDecimal() : Boolean
    {
        if(caracterActual == '-')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == '-')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }

            if(caracterActual.isDigit() || caracterActual == '.')
            {
                if(caracterActual == '.')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual.isDigit())
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }else
                    {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                }else
                {
                    while (caracterActual.isDigit())
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                    if(caracterActual == '.')
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                }
                while (caracterActual.isDigit())
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }

                if(caracterActual == '-')
                {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esCaracter(): Boolean {
        if (caracterActual == '°') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '*') {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if (caracterActual == '*' || caracterActual == '_') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            } else {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }

            if (caracterActual == '°') {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)

                obtenerSiguienteCaracter()
                return true
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esCadena(): Boolean {
        if (caracterActual == '^') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != finCodigo && caracterActual != '^') {
                if (caracterActual == '*') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if (caracterActual == '^' || caracterActual == '*' || caracterActual == 'S' || caracterActual == 'N' || caracterActual == 'C') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    } else {
                        hacerBT(posicionInicial, filaInicial, columnaInicial)
                        return false
                    }
                } else {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
            }

            if (caracterActual == '^') {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.CADENA, filaInicial, columnaInicial)

                obtenerSiguienteCaracter()
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
            }
        }

        return false
    }

    fun esIncremento(): Boolean {
        if (caracterActual == '&') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'S' || caracterActual == 's') {

                lexema += caracterActual

                almacenarToken(lexema, Categoria.INCREMENTO, filaInicial, columnaInicial)

                obtenerSiguienteCaracter()
                return true
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }

    fun esDecremento(): Boolean {
        if (caracterActual == '&') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == 'r' || caracterActual == 'R') {

                lexema += caracterActual

                almacenarToken(lexema, Categoria.DECREMENTO, filaInicial, columnaInicial)

                obtenerSiguienteCaracter()
                return true
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esFinSentencia(): Boolean {
        if (caracterActual == '!') {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if (caracterActual == '!') {
                lexema += caracterActual
                almacenarToken(lexema, Categoria.TERMINAL, filaInicial, columnaInicial)

                obtenerSiguienteCaracter()
                return true
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }

    fun esOperadorSeparacion(): Boolean
    {
        if(caracterActual == '_')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == '_' )
            {
                almacenarToken(lexema, Categoria.OPERADOR_DE_SEPARACION, filaInicial, columnaInicial)

                return true

            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
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

    /*palabras reservadas*/

    fun esPalReservadaCadena(): Boolean
    {
        if(caracterActual == 'c')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'a')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'd')
                {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA_DE_CADENA, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esPalReservadaMedFuncion(): Boolean
    {
        if(caracterActual == 'F')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'u')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'n')
                {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA_DEL_METODO_FUNCION, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }

    fun esPalReservadaCiclo(): Boolean
    {
        if(caracterActual == 'c')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'i')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'c')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == '1')
                    {
                        lexema += caracterActual
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA_DE_CICLO, filaInicial, columnaInicial)

                        obtenerSiguienteCaracter()
                        return true
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }
    fun esPalReservadaDecisiones(): Boolean
    {
        if(caracterActual == 'd')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'e')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 's')
                {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA_DE_DECISIONES, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }
    fun esPalReservadaElse(): Boolean
    {
        if(caracterActual == 'n')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'o')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == ' ')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == 'd')
                    {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                        if(caracterActual == 'e')
                        {
                            lexema += caracterActual
                            obtenerSiguienteCaracter()

                            if(caracterActual == 's')
                            {
                                lexema += caracterActual
                                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_DE_ELSE, filaInicial, columnaInicial)

                                obtenerSiguienteCaracter()
                                return true
                            }
                        }
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }


        return false
    }
    fun esPalReservadaEntero(): Boolean
    {
        if(caracterActual == 'n')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'u')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'm')
                {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA_DE_ENTERO, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }
    fun esPalReservadaDecimales(): Boolean
    {
        if(caracterActual == 'd')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'e')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'c')
                {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA_DECIMAL, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }
    fun esPalReservadaBoolean(): Boolean
    {
        if(caracterActual == 'f')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'o')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 't')
                {
                    lexema += caracterActual
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA_BOOLEAN, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }
    fun esPalReservadaCaracter(): Boolean
    {
        if(caracterActual == 'c')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'a')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'r')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if(caracterActual == 'r')
                    {
                        lexema += caracterActual
                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA_CARACTER, filaInicial, columnaInicial)

                        obtenerSiguienteCaracter()
                        return true
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }

        return false
    }
    fun esPalReservadaImprimir(): Boolean
    {
        if(caracterActual == 'i')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'm')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'p')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == '$')
                    {
                        lexema += caracterActual

                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA_IMPRIMIR, filaInicial, columnaInicial)

                        obtenerSiguienteCaracter()
                        return true
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }

    fun esPalReservadaLectura(): Boolean
    {
        if(caracterActual == 'l')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'e')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'e')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == 'r')
                    {
                        lexema += caracterActual

                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA_LECTURA, filaInicial, columnaInicial)

                        obtenerSiguienteCaracter()
                        return true
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }
    fun esPalReservadaArreglo(): Boolean
    {
        if(caracterActual == 'a')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'r')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == '!')
                {
                    lexema += caracterActual

                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA_ARREGLO, filaInicial, columnaInicial)

                    obtenerSiguienteCaracter()
                    return true
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }
    fun esPalReservadaRetorno(): Boolean
    {
        if(caracterActual == 'd')
        {
            var lexema = ""
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual

            lexema += caracterActual
            obtenerSiguienteCaracter()

            if(caracterActual == 'e')
            {
                lexema += caracterActual
                obtenerSiguienteCaracter()

                if(caracterActual == 'v')
                {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                    if(caracterActual == '$')
                    {
                        lexema += caracterActual

                        almacenarToken(lexema, Categoria.PALABRA_RESERVADA_RETORNO, filaInicial, columnaInicial)

                        obtenerSiguienteCaracter()
                        return true
                    }
                }
            }
            hacerBT(posicionInicial, filaInicial, columnaInicial)
        }
        return false
    }

}
