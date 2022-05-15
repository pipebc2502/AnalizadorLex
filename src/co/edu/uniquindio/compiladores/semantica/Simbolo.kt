package co.edu.uniquindio.compiladores.semantica

class Simbolo ()
{
    var nombre:String = ""
    var tipoDato:String = ""
    var modificable:Boolean = false
    var ambito:Ambito? = null
    var fila:Int = 0
    var columna:Int = 0
    var tiposParametros:ArrayList<String>? = null

    /**
     * Constructor para crear un simbolo de tipo valor
     */
    constructor( nombre:String, tipoDato:String, modificable:Boolean, ambito:Ambito, fila:Int, columna:Int ) : this()
    {
        this.nombre = nombre
        this.tipoDato = tipoDato
        this.modificable = modificable
        this.ambito = ambito
        this.fila = fila
        this.columna = columna
    }

    /**
     * Constructor para crear un simbolo de tipo metodo
     */
    constructor( nombre:String, tipoRetorno:String, tiposParametros:ArrayList<String>, ambito: Ambito ) : this()
    {
        this.nombre = nombre
        this.tipoDato = tipoRetorno
        this.tiposParametros = tiposParametros
        this.ambito = ambito
    }

    override fun toString(): String
    {
        return if(tiposParametros == null)
        {
             "Simbolo(nombre='$nombre', tipoDato='$tipoDato', modificable=$modificable, ambito='$ambito', fila=$fila, columna=$columna)"
        }else
            "Simbolo(nombre='$nombre', tipoDato='$tipoDato', ambito='$ambito', tiposParametros=$tiposParametros)"
    }
}