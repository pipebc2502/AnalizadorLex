package co.edu.uniquindio.compiladores.app

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.stage.Stage

class Aplicacion : Application {
    override fun start(primaryStage: Stage?) {

        val loader = FXMLLoader(Aplicacion::class.java.getResource( "/inicio.fxml"))
        val parent:Parent = loader.load() 
    }
}