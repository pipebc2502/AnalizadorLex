package co.edu.uniquindio.compiladores.lexico

import co.edu.uniquindio.compiladores.lexico.Categoria

class Token (var lexema: String, var categoria: Categoria, var fila: Int, var columna: Int) {
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }
    fun getJavaCode():String
    {
        if(categoria == Categoria.PALABRA_RESERVADA_DE_ENTERO)
        {
            return "int"
        }else if(categoria == Categoria.PALABRA_RESERVADA_DECIMAL)
        {
            return "double"
        }else if( categoria == Categoria.PALABRA_RESERVADA_DE_CADENA)
        {
            return "String"
        } else if( categoria == Categoria.PALABRA_RESERVADA_BOOLEAN)
        {
            return "boolean"
        }else if(categoria == Categoria.CADENA)
        {
            return lexema.replace("@","\"")
        }else if( categoria == Categoria.ENTERO)
        {
            return lexema.substring(1, lexema.length-1)
        }else if(categoria == Categoria.DECIMAL)
        {
            return lexema.substring(1, lexema.length-1)
        } else if(categoria == Categoria.BOOLEAN) {

            return lexema.substring(1, lexema.length - 1)
        }else if(categoria == Categoria.IDENTIFICADOR_VARIABLE){

            return lexema.replace("#","")
        }else if(categoria == Categoria.IDENTIFICADOR_METODO){

            return lexema.substring(0, lexema.length - 1)
        }else if(categoria == Categoria.OPERADOR_RELACIONAL){

            if(lexema == "Ma"){
                return " > "
            }else if(lexema == "Me"){
                return " < "
            }else if(lexema == "Ma="){
                return " >= "
            }else if(lexema == "Me="){
                return " <= "
            }else if(lexema == "I="){
                return " == "
            }
        }else if(categoria == Categoria.OPERADOR_ARITMETICO){

            if(lexema == "+O"){
                return " + "
            }else if(lexema == "-O"){
                return " - "
            }else if(lexema == "*O"){
                return " * "
            }else if(lexema == "/O"){
                return " / "
            }
        }else if(categoria == Categoria.OPERADOR_LOGICO) {

            if(lexema == "OpO"){
                return " || "
            }else if(lexema == "OpY") {
                return " && "
            }else if (lexema == "OpN") {
                return " != "
            }
        }else if (categoria == Categoria.INCREMENTO){
            return "++ "
        }else if (categoria == Categoria.DECREMENTO){
            return "-- "
        }

        return lexema

    }
}