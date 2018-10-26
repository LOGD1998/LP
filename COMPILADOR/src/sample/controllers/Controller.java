package sample.controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;
import sample.Constains.Configs;

import java.io.File;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static sample.Constains.Configs.*;
public class Controller extends Application {
    private Stage stage;
    @FXML
    private Pane pane1;
    @FXML
    TextArea CONSOLA;
    CodeArea codeArea = new CodeArea();

    @FXML
    public void initialize() {
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.replaceText(0, 0, sampleCode);

        codeArea.setPrefSize(800, 600);

        Subscription cleanupWhenNoLongerNeedIt = codeArea
                .multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(ignore -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));

        pane1.getChildren().add(codeArea);
    }


    public void evtSalir(ActionEvent event) {  //CERRAR PROGRAMA//////////////////////
        System.exit(0);
    }

    public void evtAbrir(ActionEvent evento) {     ////ABRIR ARCHIVO//////////////
        FileChooser openfile = new FileChooser();
        openfile.setTitle("Abrir archivos");
        FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Archivos .learn", "*.learn");
        openfile.getExtensionFilters().add(filtro);
        File file = openfile.showOpenDialog(stage);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
    }

    public void ejecutar(ActionEvent e) {
        compilar();
    }

    public void compilar() {
        CONSOLA.setText("");
        long tInicial=System.currentTimeMillis();
        String texto=codeArea.getText();
        String[] renglones=texto.split("\\n");

        for (int x=0;x<renglones.length;x++){
            boolean bandera=false;
            if (!renglones[x].trim().equals("")) {
                for (int y = 0; y < Configs.EXPRESIONES.length && bandera == false; y++) {
                    Pattern patron = Pattern.compile(Configs.EXPRESIONES[y]);
                    Matcher matcher = patron.matcher(renglones[x]);
                    if (matcher.matches()) {///si no cumple
                        bandera = true;
                       }//LLAVE IF

                }//LLAVE FOR Y
                if (bandera == false) {
                    CONSOLA.setText(CONSOLA.getText() + "\n" + "Error de sintaxis en la línea " + (x + 1));
                            }//LLAVE IF
                }//LLAVE IF RENGLONES

        }//LLAVE FOR X

        long tFinal=System.currentTimeMillis()-tInicial;
        CONSOLA.setText(CONSOLA.getText()+"\n"+"Compilado en: "+tFinal+" milisegundos");
    }//LLAVE COMPILAR

}