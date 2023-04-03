package co.edu.unbosque.cruddao.view.component;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class Alerta {

    /**
     * Alert de error
     *
     * @param text texto del alert
     */
    @FXML
    public static void mostrarAlertError(String text) {
        Alerta.alertas(text, Alert.AlertType.ERROR);
    }

    /**
     * Alert de info
     *
     * @param text texto del alert
     */
    @FXML
    public static void mostrarAlertInfo(String text) {
        Alerta.alertas(text, Alert.AlertType.INFORMATION);
    }

    /**
     * Alert de advertencia
     *
     * @param text texto del alert
     */
    @FXML
    public static void mostrarAlertWarning(final String text) {
        Alerta.alertas(text, Alert.AlertType.WARNING);
    }

    /**
     * Alert de errorLEVEL
     *
     * @param text      texto
     * @param alertType tipo de alerta
     */
    @FXML
    private static void alertas(final String text, final Alert.AlertType alertType) {
        try {
            Alert alert = new Alert(alertType);
            alert.setHeaderText(null);
            alert.setTitle(alertType.name());
            alert.setContentText(text);
            alert.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
