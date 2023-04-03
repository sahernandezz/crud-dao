package co.edu.unbosque.cruddao.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("start"));
//        stage.getIcons().add(
//                new Image(String.valueOf(getClass().getResource("/" + "icons/1.png"))));
        stage.setTitle("CRUD");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e -> {
            Main.exit();
        });
    }

    /**
     * Cerrar programa
     */
    public static void exit() {
        Platform.exit();
        System.exit(0);
    }


    /**
     * Se encarga de modificar la ventana con las de más ventanas
     *
     * @param fxml view
     */
    public static void setRoot(final String fxml) throws IOException {
        scene.setRoot(Main.loadFXML(fxml));
    }

    /**
     * Carga todas las vistas que contengan la extension .fxml
     *
     * @param fxml view
     * @return parent
     */
    private static Parent loadFXML(final String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(
                "/co/edu/unbosque/cruddao/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * Permite ejecutar el programa
     */

    public static void main(String[] args) {
        launch();
    }

}