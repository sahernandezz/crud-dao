package co.edu.unbosque.cruddao.controller;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import java.util.concurrent.*;

public class StartControllerImpl implements Initializable {

    /**
     * Constructor
     * @param url
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resourceBundle
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(() -> {
            try {
                Main.setRoot("view");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 2, TimeUnit.SECONDS);
    }
}
