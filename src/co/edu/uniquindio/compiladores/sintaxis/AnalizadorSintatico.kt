package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.lexico.Error

class AnalizadorSintatico (var listaTokens:ArrayList<Token>) {
    var posicionActual = 0
    var tokenActual = listaTokens[posicionActual]
    var listaErrores = ArrayList<Error>()

    /**
     * Controla el avance del analisis de los tokens del codigo fuente
     */
    fun obtenerSiguienteToken() {
        posicionActual++;

        if (posicionActual < listaTokens.size) {
            tokenActual = listaTokens[posicionActual]
        } else {
            tokenActual = Token("", Categoria.FIN_CODIGO, tokenActual.fila, tokenActual.columna)
        }
    }

    /**
     * Almacena los posible errores sintacticos que se reconozcan en el analisis
     */
    fun reportarError(mensaje: String) {
        listaErrores.add(Error(mensaje, tokenActual.fila, tokenActual.columna))
    }

    /**
     * Se devuelve hasta una posicion en la lista de tokens
     */
    fun hacerBacktracking(posInicial: Int) {
        posicionActual = posInicial
        tokenActual = listaTokens[posicionActual]
    }

    /**
     * <UnidadDeCompilacion> ::= <Clase>
     */

    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {
        val clase: Clase? = esClase()
        return if (clase != null) {
            UnidadDeCompilacion(clase)
        } else null
    }

    /**
     * <Clase> ::= Clase "(" [<ListaVariables>] [<ListaMetodos>] ")"
     */
    fun esClase(): Clase? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_DE_LA_CLASE) {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.SIMBOLO_BLOQUE_IZQUIERDO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de bloque izquierdo")
            }

            val listaVariables: ArrayList<DeclaracionVariable> = esListaVariables()
            val listaMetodos: ArrayList<Metodo> = esListaMetodos()

            if (tokenActual.categoria == Categoria.SIMBOLO_BLOQUE_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de bloque derecho")
            }

            return Clase(listaMetodos, listaVariables)
        } else {
            reportarError("Falta la palabra reservada Clase")
        }

        return null
    }

    /**
     * <ListaVariables> ::= <DeclaracionVariable> [<ListaVariables>]
     */
    fun esListaVariables(): ArrayList<DeclaracionVariable> {
        var listaVariables = ArrayList<DeclaracionVariable>()
        var variable = esDeclaracionVariable()

        while (variable != null) {
            listaVariables.add(variable)

            variable = esDeclaracionVariable()
        }
        return listaVariables
    }

    /**
     * <ListaMetodos> ::= <Metodo> [<ListaMetodos>]
     */
    fun esListaMetodos(): ArrayList<Metodo> {
        var listaMetodos = ArrayList<Metodo>()
        var funcion = esFuncion()
        var procedimiento = esProcedimiento()

        while (funcion != null || procedimiento != null) {
            if (funcion != null) {
                listaMetodos.add(funcion)
            }
            if (procedimiento != null) {
                listaMetodos.add(procedimiento)
            }

            funcion = esFuncion()
            procedimiento = esProcedimiento()
        }
        return listaMetodos
    }

    /**
     * <Funcion> ::= Fun <TipoDato> identificadorFuncion "[" [<ListaParametros>] "]" <BloqueSentencias>
     */
    fun esFuncion(): Funcion? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_DEL_METODO_FUNCION) {
            obtenerSiguienteToken()

            val tipoRetorno = esTipoDeDato()

            if (tipoRetorno != null) {
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.IDENTIFICADOR_METODO && tokenActual.lexema[tokenActual.lexema.length - 1] == 'ø') {
                    val nombreFuncion = tokenActual
                    obtenerSiguienteToken()

                    if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                        obtenerSiguienteToken()
                    } else {
                        reportarError("Falta el simbolo de agrupacion izquierdo")
                    }
                    var listaParametros = esListaParametros()

                    if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                        obtenerSiguienteToken()
                    } else {
                        reportarError("Falta el simbolo de agrupacion derecho")
                    }
                    var bloqueSentencias = esBloqueSentencias()

                    if (bloqueSentencias != null) {
                        //La funcion esta bien escrita
                        return Funcion(nombreFuncion, tipoRetorno, listaParametros, bloqueSentencias)
                    } else {
                        reportarError("El bloque de sentencias esta vacio")
                    }

                } else {
                    reportarError("Falta el nombre de la funcion")
                }
            } else {
                reportarError("Falta el tipo de retorno en la funcion")
            }

        }
        return null
    }

    /**
     * <Procedimiento> ::= Proc identificadorProcedimiento "[" [<ListaParametros>] "]" <BloqueSentencias>
     */

    fun esProcedimiento(): Procedimiento? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_DEL_METODO_PROCEDIMIENTO) {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.IDENTIFICADOR_METODO && tokenActual.lexema[tokenActual.lexema.length - 1] == 'ß') {
                val nombreProcedimiento = tokenActual
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                    obtenerSiguienteToken()
                } else {
                    reportarError("Falta el parentesis izquierdo")
                }

                var listaParametros = esListaParametros()

                if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                    obtenerSiguienteToken()
                } else {
                    reportarError("Falta el parentesis derecho")
                }

                var bloqueSentencias = esBloqueSentencias()

                if (bloqueSentencias != null) {
                    //El procedimiento esta bien escrito
                    return Procedimiento(nombreProcedimiento, listaParametros, bloqueSentencias)
                } else {
                    reportarError("Error en el bloque de sentencias")
                }
            } else {
                reportarError("Falta el nombre del procedimiento")
            }

        }
        return null
    }


    /**
     * <TipoDeDato> ::= int | decimal | string | chart | bool
     */
    fun esTipoDeDato(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_DE_ENTERO || tokenActual.categoria == Categoria.PALABRA_RESERVADA_DECIMAL || tokenActual.categoria == Categoria.PALABRA_RESERVADA_DE_CADENA || tokenActual.categoria == Categoria.PALABRA_RESERVADA_CARACTER || tokenActual.categoria == Categoria.PALABRA_RESERVADA_BOOLEAN) {
            return tokenActual
        }
        return null
    }

    /**
     * <ListaParametros> ::= <Parametro> ["."<ListaParametros>]
     */
    fun esListaParametros(): ArrayList<Parametro> {
        var listaParametros = ArrayList<Parametro>()
        var parametro = esParametro()

        while (parametro != null) {
            listaParametros.add(parametro)

            if (tokenActual.categoria == Categoria.OPERADOR_DE_SEPARACION) {
                obtenerSiguienteToken()
                parametro = esParametro()
            } else {
                if (tokenActual.categoria != Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                    reportarError("Falta una coma en la lista de parametros")
                }
                break
            }
        }

        return listaParametros
    }

    /**
     * <Parametro> ::= <TipoDato> identificador
     */
    fun esParametro(): Parametro? {
        val tipoDato = esTipoDeDato()
        if (tipoDato != null) {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                val nombre = tokenActual
                obtenerSiguienteToken()

                return Parametro(tipoDato, nombre)
            } else {
                reportarError("Falta el identificador de la variable")
            }
        }

        return null
    }

    /**
     * <BloqueSentencias> ::= "(" [<ListaSentencias>] ")"
     */
    fun esBloqueSentencias(): ArrayList<Sentencia>? {
        if (tokenActual.categoria == Categoria.SIMBOLO_BLOQUE_IZQUIERDO) {
            obtenerSiguienteToken()

            var listaSentencias = esListaSentencias()

            if (tokenActual.categoria == Categoria.SIMBOLO_BLOQUE_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta la llave derecha en la funcion")
            }

            return listaSentencias
        } else {
            reportarError("Falta la llave izquierda en la funcion")
        }

        return null
    }

    /**
     * <ListaSentencias> ::= <Sentencia> [<ListaSentencias>]
     */
    fun esListaSentencias(): ArrayList<Sentencia> {
        val lista = ArrayList<Sentencia>()
        var s = esSentencia()

        while (s != null) {
            lista.add(s)
            s = esSentencia()
        }

        return lista
    }

    /**
     * <Sentencia> ::= <Decision> | <Ciclo> | <Impresion> | <Lectura> | <Asignacion> | <DeclaracionVariable> | <InvocacionProcedimiento> | <Retorno> | <IncrementoDecremento>
     */
    fun esSentencia(): Sentencia? {
        var s: Sentencia? = esArreglo()

        if (s != null) {
            return s
        }
        s = esAsignacion()

        if (s != null) {
            return s
        }
        s = esCiclo()

        if (s != null) {
            return s
        }
        s = esDecision()

        if (s != null) {
            return s
        }
        s = esDeclaracionVariable()

        if (s != null) {
            return s
        }
        s = esImpresion()

        if (s != null) {
            return s
        }
        s = esInvocacionProcedimiento()

        if (s != null) {
            return s
        }
        s = esRetorno()

        if (s != null) {
            return s
        }
        s = esIncrementoDecremento()

        if (s != null) {
            return s
        }
        return null
    }

    /**
     * <Arreglo> ::= ar! identificadorVariable ":" "[" <ListaArgumentos> "]" T.
     */
    fun esArreglo(): Arreglo? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_ARREGLO) {
            obtenerSiguienteToken()
            var nombre: Token? = null

            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                nombre = tokenActual
                obtenerSiguienteToken()
            } else {
                reportarError("Falta definir el nombre del arreglo")
            }

            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el operador de asignacion")
            }
            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion izquierdo")
            }
            val argumentos = esListaArgumentos()

            if (argumentos.isEmpty()) {
                reportarError("Faltan las expresiones del arreglo")
            }

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion derecho")
            }

            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el fin de sentencia")
            }
            return Arreglo(nombre, argumentos)
        }

        return null
    }

    /**
     * <Asignacion> ::= identificador operadorAsignacion <Expresion> T. |
     *                  identificador operadorAsignacion <InvocacionFuncion> T. |
     *                  identificador operadorAsignacion <Lectura> T.
     */
    fun esAsignacion(): Asignacion? {
        var posicionInical = posicionActual

        if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
            val identificador = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) {
                obtenerSiguienteToken()

            } else {
                if (tokenActual.categoria == Categoria.INCREMENTO || tokenActual.categoria == Categoria.DECREMENTO) {
                    hacerBacktracking(posicionInical)
                    return null
                }

                reportarError("Falta el operador de asignacion")
            }

            val expresion = esExpresion()
            var sentencia: Sentencia? = null

            if (expresion == null) {
                sentencia = esInvocacionFuncion()

                if (sentencia == null) {
                    sentencia = esLectura(identificador)

                    if (sentencia == null) {
                        sentencia = esDuplax()

                        if (sentencia == null) {
                            sentencia = esSumarHasta()

                            if (sentencia == null) {
                                sentencia = esRestarHasta()
                            }
                        }
                    }
                }
            }

            if (expresion == null && sentencia == null) {
                reportarError("Error en la expresion")

                while (!(tokenActual.categoria == Categoria.TERMINAL) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }
            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el fin de sentencia")
            }
            return Asignacion(identificador, expresion, sentencia)

        }
        return null
    }

    /**
     * <Ciclo> ::= cic1 ":" "[" <ExpresionLogica> "]" <BloqueSentencias>
     */
    fun esCiclo(): Ciclo? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_DE_CICLO) {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el operador de asignacion")
            }

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion izquierdo")
            }

            val expresion = esExpresionLogica()

            if (expresion == null) {
                reportarError("Error en la expresion logica")

                while (!(tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                obtenerSiguienteToken()

            } else {
                reportarError("Falta el simbolo de agrupacion derecho")
            }

            val bloque = esBloqueSentencias()

            if (bloque == null) {
                reportarError("Error en el bloque de sentencias")

            }

            return Ciclo(expresion, bloque)
        }
        return null
    }

    /**
     * <Decision> ::= des "[" <ExpresionLogica> "]" <BloqueSentencias>  [no des <BloqueSentencias>]
     */
    fun esDecision(): Decision? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_DE_DECISIONES) {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion izquierdo")
            }

            val expresion = esExpresionLogica()

            if (expresion == null) {
                reportarError("Error en la expresion logica")

                while (!(tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }
            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion derecho")
            }
            val bloqueV = esBloqueSentencias()

            if (bloqueV != null) {
                if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_DE_ELSE) {
                    obtenerSiguienteToken()
                    val bloqueF = esBloqueSentencias()

                    if (bloqueF != null) {
                        return Decision(expresion, bloqueV, bloqueF)
                    } else {
                        reportarError("Error en el bloque de sentencias del no des")
                    }
                } else {
                    return Decision(expresion, bloqueV, null)
                }
            } else {
                reportarError("Error en el bloque de sentencias del des")
            }
        }
        return null
    }

    /**
     * <DeclaracionVariable> ::= <TipoDato> identificadorVariable [ ":" <Expresion> ] T.
     *                           <TipoDato> identificadorVariable [ ":" <InvocacionFuncion> ] T.
     *                           <TipoDato> identificadorVariable [ ":" <Lectura> ] T.
     */
    fun esDeclaracionVariable(): DeclaracionVariable? {
        val tipoDato = esTipoDeDato()

        if (tipoDato != null) {
            obtenerSiguienteToken()
            var identificador: Token? = null

            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                identificador = tokenActual
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el nombre de la variable")
            }

            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) {
                obtenerSiguienteToken()

                val expresion = esExpresion()
                var sentencia: Sentencia? = null


                if (expresion == null) {
                    sentencia = esInvocacionFuncion()

                    if (sentencia == null) {
                        sentencia = esLectura(identificador)

                        if (sentencia == null) {
                            sentencia = esDuplax()

                            if (sentencia == null) {
                                sentencia = esSumarHasta()

                                if (sentencia == null) {
                                    sentencia = esRestarHasta()
                                }
                            }
                        }
                    }
                }

                if (expresion == null && sentencia == null) {
                    reportarError("Falta la expresion")

                    while (!(tokenActual.categoria == Categoria.TERMINAL) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                        obtenerSiguienteToken()
                    }
                }

                if (tokenActual.categoria == Categoria.TERMINAL) {
                    obtenerSiguienteToken()
                } else {
                    reportarError("Falta el fin de la sentencia")
                }
                return DeclaracionVariable(tipoDato, identificador, expresion, sentencia)
            }

            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el fin de la sentencia")
            }
            return DeclaracionVariable(tipoDato, identificador, null, null)
        }
        return null
    }

    /**
     * <Impresion ::= imp$ "[" <ExpresionCadena> "]" T.
     */
    fun esImpresion(): Impresion? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_IMPRIMIR) {

            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion izquierdo")
            }

            val expresion = esExpresionCadena()

            if (expresion == null) {
                reportarError("Falta una expresion de cadena")

                while (!(tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion derecho")
            }

            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el fin de sentencia")
            }
            return Impresion(expresion)
        }
        return null
    }

    /**
     * <InvocacionFuncion> ::= IdentificadorMetodo "[" [<ListaArgumentos>] "]"
     */
    fun esInvocacionFuncion(): InvocacionFuncion? {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_METODO && tokenActual.lexema[tokenActual.lexema.length - 1] == 'ø') {
            var nombreFuncion = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion izquierdo")
            }

            val argumentos = esListaArgumentos()

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion derecho")
            }
            return InvocacionFuncion(nombreFuncion, argumentos)
        }

        return null
    }

    /**
     * <InvocacionProcedimiento> ::= IdentificadorMetodo "[" [<ListaArgumentos>] "]" T.
     */
    fun esInvocacionProcedimiento(): InvocacionProcedimiento? {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_METODO && tokenActual.lexema[tokenActual.lexema.length - 1] == 'ß') {
            val nombre = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion izquierdo")
            }
            val argumentos = esListaArgumentos()

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                obtenerSiguienteToken()

            } else {
                reportarError("Falta el simbolo de agrupacion derecho")
            }

            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta fin de sentencias")
            }
            return InvocacionProcedimiento(nombre, argumentos)
        }

        return null
    }


    /**
     * <ListaArgumentos> ::= <Argumento>[ "." <ListaArgumentos>]
     * <Argumento> ::= <Expresion>
     */
    fun esListaArgumentos(): ArrayList<Expresion> {
        val lista = ArrayList<Expresion>()
        var param = esExpresion()

        while (param != null) {
            lista.add(param)

            if (tokenActual.categoria == Categoria.OPERADOR_DE_SEPARACION) {
                obtenerSiguienteToken()
                param = esExpresion()


            } else {
                if (tokenActual.categoria != Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                    reportarError("Falta el simbolo de separacion")
                }
                break
            }
        }
        return lista
    }

    /**
     * <Lectura> ::= ler$ "[" <ExpresionCadena> "]"
     */
    fun esLectura(nombreVariable: Token?): Lectura? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_LECTURA) {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion izquierdo")
            }

            val expresion = esExpresionCadena()

            if (expresion == null) {
                reportarError("Falta la expresion")

                while (!(tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) || tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion derecho")
            }

            return Lectura(expresion, nombreVariable)
        }

        return null
    }

    /**
     * <Retorno> ::= "{" identificadorVariable "}" dev$ T.
     */
    fun esRetorno(): Retorno? {
        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.CORCHETE_IZQUIERDO) {
            obtenerSiguienteToken()

            var identificador: Token? = null

            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                identificador = tokenActual
                obtenerSiguienteToken()
            } else {
                reportarError("Falta un identificador de variable")
            }
            if (tokenActual.categoria == Categoria.CORCHETE_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el corchete derecho")
            }
            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_RETORNO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Error con la palabra reservada del retorno")
            }
            if (tokenActual.categoria == Categoria.TERMINAL) {
                obtenerSiguienteToken()

            } else {
                hacerBacktracking(posicionInicial)
                return esRetornoErroneo()
            }
            return Retorno(identificador)

        }
        return null
    }

    /**
     * <RetornoErroneo> ::= "{" identificadorVariable "}" dev$
     */
    fun esRetornoErroneo(): Retorno? {
        if (tokenActual.categoria == Categoria.CORCHETE_IZQUIERDO) {
            obtenerSiguienteToken()

            var identificador: Token? = null

            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                identificador = tokenActual
                obtenerSiguienteToken()
            } else {
                reportarError("Falta un identificador de variable")
            }
            if (tokenActual.categoria == Categoria.CORCHETE_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el corchete derecho")
            }
            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_RETORNO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Error con la palabra reservada del retorno")
            }

            return Retorno(identificador)

        }
        return null
    }

    /**
     * <IncrementoDecremento> ::= Variable operadorIncremento u operadorDecremento T.
     */
    fun esIncrementoDecremento(): IncrementoDecremento? {
        var posicionInicial = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
            val variable = tokenActual
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.INCREMENTO || tokenActual.categoria == Categoria.DECREMENTO) {
                val operador = tokenActual
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.TERMINAL) {
                    obtenerSiguienteToken()
                } else {
                    reportarError("Falta el terminal de la sentencia")
                }
                return IncrementoDecremento(variable, operador)
            }
            hacerBacktracking(posicionInicial)
        }

        return null
    }

    /**
     * <Expresion> ::= <ExpresionAritmetica> | <ExpresionRelacional> | <ExpresionLogica> | <ExpresionCadena>
     */
    fun esExpresion(): Expresion? {
        var e: Expresion? = esExpresionLogica()
        if (e != null) {
            return e
        }
        e = esExpresionAritmetica()

        if (e != null) {

            return e
        }
        e = esExpresionCadena()

        return e
    }

    /**
     * <ExpresionAritmetica> ::= "["<ExpresionAritmetica>"]" [operadorAritmetico <ExpresionAritmetica>] | <ValorNumerico> [operadorAritmetico <ExpresionAritmetica>]
     */
    fun esExpresionAritmetica(): ExpresionAritmetica? {
        if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
            obtenerSiguienteToken()
            var exp1: ExpresionAritmetica? = esExpresionAritmetica()
            if (exp1 != null) {
                if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO) {

                        val operador1 = tokenActual
                        obtenerSiguienteToken()
                        var exp2: ExpresionAritmetica? = esExpresionAritmetica()
                        if (exp2 != null) {
                            return ExpresionAritmetica(exp1, operador1, exp2)
                        } else {
                            reportarError("Falta una segunda expresion aritmetica")
                        }
                    } else {
                        return ExpresionAritmetica(exp1)
                    }
                } else {
                    reportarError("Falta el simbolo de agrupacion derecho")
                }
            } else {
                reportarError("Falta una expresion aritmetica")
            }
        } else {
            val valor: ValorNumerico? = esValorNumerico()

            if (valor != null) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO) {
                    val operador2 = tokenActual
                    obtenerSiguienteToken()

                    val exp3: ExpresionAritmetica? = esExpresionAritmetica()

                    if (exp3 != null) {
                        return ExpresionAritmetica(valor, operador2, exp3)
                    } else {
                        reportarError("Falta una segunda expresion Aritmetica")
                    }
                } else {
                    return ExpresionAritmetica(valor)
                }
            }
        }


        return null
    }

    /**
     * <ExpresionLogica> ::=  <ExpresionRelacional> [ OperadorBinario <ExpresionLogica>]   |
     *                        OpN "[" <ExpresionLogica> "]" [ OperadorBinario <ExpresionLogica>]
     */
    fun esExpresionLogica(): ExpresionLogica? {
        val expR1: ExpresionRelacional? = esExpresionRelacional()
        if (expR1 != null) {
            if (tokenActual.categoria == Categoria.OPERADOR_LOGICO && tokenActual.lexema != "OpN") {
                val operador = tokenActual
                obtenerSiguienteToken()

                val expL1 = esExpresionLogica()

                if (expL1 != null) {
                    // cumple con la estructura de una expresion logica
                    return ExpresionLogica(expR1, operador, expL1)
                } else {
                    reportarError("Falta una segunda expresion relacional")
                }

            } else {
                return ExpresionLogica(expR1)
            }
        } else {
            if (tokenActual.categoria == Categoria.OPERADOR_LOGICO && tokenActual.lexema == "OpN") {
                val operador2 = tokenActual
                obtenerSiguienteToken()

                if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                    obtenerSiguienteToken()
                } else {
                    reportarError("Falta el simbolo de agrupacion izquierdo")
                }

                val exL2: ExpresionLogica? = esExpresionLogica()
                if (exL2 != null) {
                    if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                        obtenerSiguienteToken()

                    } else {
                        reportarError("Falta el simbolo de agrupacion derecho")
                    }

                    if (tokenActual.categoria == Categoria.OPERADOR_LOGICO && tokenActual.lexema != "OpN") {
                        val operador3 = tokenActual
                        obtenerSiguienteToken()

                        val exL3 = esExpresionLogica()

                        if (exL3 != null) {
                            return ExpresionLogica(operador2, exL2, operador3, exL3)
                        } else {
                            reportarError("Falta la expresion logica")
                        }
                    } else {
                        return ExpresionLogica(operador2, exL2)
                    }
                } else {
                    reportarError("Falta la expresion logica")
                }
            }
        }
        return null
    }

    /**
     * <ExpresionRelacional> ::= <ExpresionAritmetica>  operadorRelacional <ExpresionAritmetica> | &false& | &true&
     */
    fun esExpresionRelacional(): ExpresionRelacional? {
        val posInicial = posicionActual

        var exp1: ExpresionAritmetica? = esExpresionAritmetica()
        if (exp1 != null) {
            if (tokenActual.categoria == Categoria.OPERADOR_RELACIONAL) {
                var operador = tokenActual
                obtenerSiguienteToken()

                var exp2: ExpresionAritmetica? = esExpresionAritmetica()

                if (exp2 != null) {
                    return ExpresionRelacional(exp1, operador, exp2)
                } else {
                    reportarError("Falta una expresión relacional")
                }

            } else {
                hacerBacktracking(posInicial)
                return null
            }

        } else {
            if (tokenActual.categoria == Categoria.BOOLEAN) {
                val valor = tokenActual
                obtenerSiguienteToken()
                return ExpresionRelacional(valor)
            }
        }
        return null
    }

    /**
     * <ExpresionCadena> ::=  cadena [c+ <ExpresionCadena>] | identificador [c+ <ExpresionCadena>]
     */
    fun esExpresionCadena(): ExpresionCadena? {
        if (tokenActual.categoria == Categoria.CADENA) {
            val cadena = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_DE_CONCATENACION) {
                obtenerSiguienteToken()
                val expresion: Expresion? = esExpresion()
                if (expresion != null) {
                    return ExpresionCadena(cadena, expresion)
                } else {
                    reportarError("Falta una expresion cadena")
                }
            } else {
                return ExpresionCadena(cadena)
            }

        } else {
            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                val identificador = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.OPERADOR_DE_CONCATENACION) {
                    obtenerSiguienteToken()
                    val expresion2: ExpresionCadena? = esExpresionCadena()
                    if (expresion2 != null) {
                        return ExpresionCadena(identificador, expresion2)
                    } else {
                        reportarError("Falta una expresion cadena")
                    }
                } else {
                    return ExpresionCadena(identificador)
                }
            }
        }
        return null
    }

    /**
     * <ValorNumerico> ::= decimal | entero | identificador
     */
    fun esValorNumerico(): ValorNumerico? {
        if (tokenActual.categoria == Categoria.DECIMAL || tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
            val valor = tokenActual
            return ValorNumerico(valor)
        }

        return null
    }

    /**
     * <Duplax> ::= Duplax "[" variable ":" <ValorNumerico> "]"
     */
    fun esDuplax(): Duplax? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_DUPLAX) {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion izquierdo")
            }

            var variable: Token? = null

            if (tokenActual.categoria == Categoria.IDENTIFICADOR_VARIABLE) {
                variable = tokenActual
                obtenerSiguienteToken()
            } else {
                reportarError("Falta la variable")
            }

            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) {
                obtenerSiguienteToken()
            }

            val numero = esValorNumerico()

            if (numero != null) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta un valor numerico")

                while (!(tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion derecho")
            }

            return Duplax(variable, numero)

        }

        return null
    }

    /**
     * <SumarHasta> ::= SumarHasta "[" <ValorNumerico> ":" <ValorNumerico> "]"
     */
    fun esSumarHasta(): SumarHasta? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_SUMAR_HASTA) {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion izquierdo")
            }

            val numeroInicio = esValorNumerico()

            if (numeroInicio != null) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta un valor numerico")

                while (!(tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }

            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) {
                obtenerSiguienteToken()
            }

            val numeroFinal = esValorNumerico()

            if (numeroFinal != null) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta un valor numerico")

                while (!(tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion derecho")
            }

            return SumarHasta(numeroInicio, numeroFinal)

        }

        return null
    }

    /**
     * <RestarHasta> ::= RestarHasta "[" <ValorNumerico> ":" <ValorNumerico> "]"
     */
    fun esRestarHasta(): RestarHasta? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA_RESTAR_HASTA) {
            obtenerSiguienteToken()

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_IZQUIERDO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion izquierdo")
            }

            val numeroInicio = esValorNumerico()

            if (numeroInicio != null) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta un valor numerico")

                while (!(tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }

            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) {
                obtenerSiguienteToken()
            }

            val numeroFinal = esValorNumerico()

            if (numeroFinal != null) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta un valor numerico")

                while (!(tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) && tokenActual.categoria != Categoria.FIN_CODIGO) {
                    obtenerSiguienteToken()
                }
            }

            if (tokenActual.categoria == Categoria.SIMBOLO_AGRUPACION_DERECHO) {
                obtenerSiguienteToken()
            } else {
                reportarError("Falta el simbolo de agrupacion derecho")
            }

            return RestarHasta(numeroInicio, numeroFinal)

        }

        return null
    }
}