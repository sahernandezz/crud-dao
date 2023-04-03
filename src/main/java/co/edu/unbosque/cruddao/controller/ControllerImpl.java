package co.edu.unbosque.cruddao.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerImpl implements Initializable {
    @FXML
    private AnchorPane content;

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
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            this.setContent("crud");
    }

    private void setContent(final String fxml) throws IOException {
        Node node = FXMLLoader.load(Objects.requireNonNull(
                getClass().getResource("/co/edu/unbosque/cruddao/fxml/" + fxml + ".fxml")));
        this.content.getChildren().clear();
        this.content.getChildren().add(node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }
}
