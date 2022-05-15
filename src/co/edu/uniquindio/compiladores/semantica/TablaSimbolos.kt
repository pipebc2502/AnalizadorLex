package co.edu.uniquindio.compiladores.semantica

import co.edu.uniquindio.compiladores.lexico.Error

class TablaSimbolos (var listaErrores:ArrayList<Error>)
{
    var listaSimbolos:ArrayList<Simbolo> = ArrayList()

    /**
     * Permite guardar un simbolo que representa una variable, una constante, un parametro, un arreglo
     */
    fun guardarSimboloValor( nombre:String, tipoDato:String, modificable:Boolean, ambito:Ambito, fila:Int, columna:Int )
    {
        val s = buscarSimboloValor(nombre,ambito, fila, columna)

        if(s == null)
        {
            println(ambito)
            listaSimbolos.add(Simbolo(nombre, tipoDato, modificable, ambito, fila, columna))
        }else
        {
            listaErrores.add(Error("El campo $nombre ya existe dentro del ambito $ambito", fila,columna))
        }
    }

    /**
     * Permite guardar un simbolo que representa un metodo
     */
    fun guardarSimboloMetodo( nombre:String, tipoRetorno:String, tiposParametros:ArrayList<String>, ambito: Ambito, fila:Int, columna:Int )
    {
        val s = buscarSimboloMetodo(nombre, tiposParametros)

        if(s == null)
        {
            listaSimbolos.add(Simbolo(nombre, tipoRetorno, tiposParametros, ambito))
        }else
        {
            listaErrores.add(Error("El metodo $nombre ya existe dentro del ambito $ambito", fila,columna))
        }
    }

    /**
     * Permite buscar un valor dentro de la tabla de simbolos
     */
    fun buscarSimboloValor( nombre:String, ambito:Ambito, fila: Int, columna: Int):Simbolo?
    {
        for( s in listaSimbolos)
        {
            if(s.tiposParametros == null)
            {
                if( s.nombre == nombre )
                {
                    if( ambito.equals(s.ambito!!.nombre, s.ambito!!.clase, s.ambito!!.metodo, s.ambito!!.sentencia))
                    {
                        if(s.fila <= fila )
                        {
                            return s
                        }else
                        {
                            listaErrores.add(Error("La variable $nombre no puede ser usada antes de declarar",fila, columna))
                        }
                    }else if(ambito.ambito!!.equals(s.ambito!!.nombre, s.ambito!!.clase, s.ambito!!.metodo, s.ambito!!.sentencia))
                    {
                        if(s.fila <= fila )
                        {
                            return s
                        }else
                        {
                            listaErrores.add(Error("La variable $nombre no puede ser usada antes de declarar",fila, columna))
                        }
                    }else if(ambito.ambito!!.ambito!!.equals(s.ambito!!.nombre, s.ambito!!.clase, s.ambito!!.metodo, s.ambito!!.sentencia))
                    {
                        if(s.fila <= fila )
                        {
                            return s
                        }else
                        {
                            listaErrores.add(Error("La variable $nombre no puede ser usada antes de declarar",fila, columna))
                        }
                    }
                }
            }
        }
        return null
    }

    /**
     * Permite buscar un metodo dentro de la tabla de simbolos
     */
    fun buscarSimboloMetodo( nombre:String, tiposParametros:ArrayList<String>):Simbolo?
    {
        for( s in listaSimbolos)
        {
            if(s.tiposParametros != null)
            {
                if( s.nombre == nombre && s.tiposParametros == tiposParametros)
                {
                    return s
                }
            }
        }
        return null
    }

    override fun toString(): String {
        return "TablaSimbolos(listaSimbolos=$listaSimbolos)"
    }
}