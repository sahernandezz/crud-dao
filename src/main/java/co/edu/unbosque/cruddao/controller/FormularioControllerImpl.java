package co.edu.unbosque.cruddao.controller;

import co.edu.unbosque.cruddao.controller.validation.PersonaValidationImpl;
import co.edu.unbosque.cruddao.model.Persona;
import co.edu.unbosque.cruddao.view.component.Alerta;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.net.URL;
import java.util.*;
import java.util.List;

public class FormularioControllerImpl implements Initializable {

    @FXML
    private MFXButton btnFormulario;

    @FXML
    private TextField txtFileApellidos;

    @FXML
    private TextField txtFileCelular;

    @FXML
    private TextField txtFileCorreo;

    @FXML
    private TextField txtFileDocumento;

    @FXML
    private TextField txtFileNombres;

    @FXML
    private ComboBox<String> txtFileTipoDocumento;


    public final static String REGISTRAR = "Guardar";

    public final static String ACTUALIZAR = "Actualizar";

    private String tipoAccion;

    private CrudControllerImpl crudControllerImpl;

    /**
     * Constructor
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.txtFileTipoDocumento.getItems().addAll("CC", "TI", "CE");
    }


    public void formalizar(final String tipoAccion, final CrudControllerImpl crudControllerImpl) {
        this.tipoAccion = tipoAccion;
        this.btnFormulario.setText(this.tipoAccion);
        this.crudControllerImpl = crudControllerImpl;

        if (this.tipoAccion.equals(ACTUALIZAR)) {
            Persona persona = this.crudControllerImpl.getTabla().getSelectionModel().getSelectedValues().get(0);
            this.setDatos(persona);
        }
    }

    @FXML
    private void btnFormularioEvent() {
        if (this.tipoAccion.equals(REGISTRAR)) {
            this.registrar();
        } else if (this.tipoAccion.equals(ACTUALIZAR)) {
            this.actualizar();
        }
    }

    private void registrar() {
        Optional<Persona> personaData = this.getDatosTxt();
        if (personaData.isPresent()) {
            Optional<Persona> savePersona = this.crudControllerImpl.getPersonaDao().save(this.getDatosTxt().get());
            if (savePersona.isPresent()) {
                this.crudControllerImpl.actualizarTabla();
                this.salir();
                Alerta.mostrarAlertInfo("Usuario registrado correctamente");
            } else {
                this.salir();
                Alerta.mostrarAlertWarning("No se pudo registrar el usuario");
            }
        }
    }

    private void actualizar() {
        Optional<Persona> personaData = this.getDatosTxt();
        if (personaData.isPresent()) {
            Persona persona = this.crudControllerImpl.getTabla().getSelectionModel().getSelectedValues().get(0);
            personaData.get().setId(persona.getId());
            Optional<Persona> updatePersona = this.crudControllerImpl.getPersonaDao().update(personaData.get());
            this.crudControllerImpl.getTabla().getSelectionModel().getSelection().clear();
            if (updatePersona.isPresent()) {
                this.salir();
                this.crudControllerImpl.actualizarTabla();
                Alerta.mostrarAlertInfo("Se ha Actualizado el registro exitosamente");
            } else {
                this.salir();
                Alerta.mostrarAlertWarning("El usuario no se pudo actualizar");
            }
        }
    }

    @SneakyThrows
    private Optional<Persona> getDatosTxt() {
        Persona persona = new Persona();
        persona.setNombres(this.txtFileNombres.getText());
        persona.setApellidos(this.txtFileApellidos.getText());
        persona.setNumeroCelular(this.txtFileCelular.getText());
        persona.setCorreo(this.txtFileCorreo.getText());
        persona.setTipoDocumento(this.txtFileTipoDocumento.getSelectionModel().getSelectedItem());
        persona.setNumeroDocumento(this.txtFileDocumento.getText());
        return this.validaciones(persona) ? Optional.of(persona) : Optional.empty();
    }


    @SneakyThrows
    private void setDatos(final Persona persona) {
        this.txtFileNombres.setText(persona.getNombres());
        this.txtFileApellidos.setText(persona.getApellidos());
        this.txtFileCelular.setText(persona.getNumeroCelular());
        this.txtFileCorreo.setText(persona.getCorreo());
        this.txtFileTipoDocumento.getSelectionModel().select(persona.getTipoDocumento());
        this.txtFileDocumento.setText(persona.getNumeroDocumento());
    }

    private Boolean validaciones(final Persona persona) throws Exception {
        boolean validacion = true;
        List<String> errores = new ArrayList<>();
        errores.add(PersonaValidationImpl.validarNombres(persona.getNombres()));
        errores.add(PersonaValidationImpl.validarApellidos(persona.getApellidos()));
        errores.add(PersonaValidationImpl.validarTipoDocumento(persona.getTipoDocumento()));
        errores.add(PersonaValidationImpl.validarNumeroDocumento(persona.getNumeroDocumento()));
        errores.add(PersonaValidationImpl.validarCelular(persona.getNumeroCelular()));
        errores.add(PersonaValidationImpl.validarCorreo(persona.getCorreo()));
        errores.removeIf(Objects::isNull);
        if (!errores.isEmpty()) {
            Alerta.mostrarAlertInfo(errores.get(0));
            validacion = false;
        }
        return validacion;
    }

    private void salir() {
        try {
            Stage stage = (Stage) this.btnFormulario.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            Alerta.mostrarAlertError("Error al cerrar la ventana");
        }
    }
}
