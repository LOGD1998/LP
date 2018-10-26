package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Controller extends Application{
    private Stage stage;

    public void evtSalir(ActionEvent event) {  //CERRAR PROGRAMA//////////////////////
        System.exit(0);
    }

    public void evtAbrir(ActionEvent evento){     ////ABRIR ARCHIVO//////////////
        FileChooser openfile=new FileChooser();
        openfile.setTitle("Abrir archivos");
        FileChooser.ExtensionFilter filtro= new FileChooser.ExtensionFilter("Archivos .LYP","*.LYP");
        openfile.getExtensionFilters().add(filtro);
        File file=openfile.showOpenDialog(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage=primaryStage;
    }
}