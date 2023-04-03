package co.edu.unbosque.cruddao.controller;

import co.edu.unbosque.cruddao.model.Persona;
import co.edu.unbosque.cruddao.model.dao.*;
import co.edu.unbosque.cruddao.view.component.Alerta;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;


import java.net.URL;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class CrudControllerImpl implements Initializable {

    @FXML
    private MFXComboBox<String> boxTipo;

    @FXML
    @Getter
    private MFXTableView<Persona> tabla;

    private PersonaMemoriaDao personaMemoriaDao;

    private PersonaArchivoDao personaArchivoDao;

    private PersonaOracleDao personaOracleDao;

    @Getter
    @Setter
    private Dao<Persona> personaDao;

    private final static String FORMULARIO = "/co/edu/unbosque/cruddao/fxml/formulario.fxml";

    /**
     * Constructor
     *
     * @param url            The location used to resolve relative paths for the root object, or
     *                       {@code null} if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or {@code null} if
     *                       the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setupTable();
        this.tabla.autosizeColumnsOnInitialization();

        this.personaMemoriaDao = new PersonaMemoriaDao();
        this.personaArchivoDao = new PersonaArchivoDao();
        this.personaOracleDao = new PersonaOracleDao();

        this.boxTipo.getItems().addAll("En Memoria", "En Archivo", "En Oracle");
        this.boxTipo.selectIndex(0);
        this.selectTipoCrud();
    }

    private void setupTable() {
        MFXTableColumn<Persona> nombres = new MFXTableColumn<>("Nombre", true, Comparator.comparing(Persona::getNombres));
        MFXTableColumn<Persona> apellidos = new MFXTableColumn<>("Apellidos", true, Comparator.comparing(Persona::getApellidos));
        MFXTableColumn<Persona> correo = new MFXTableColumn<>("Correo", true, Comparator.comparing(Persona::getCorreo));
        MFXTableColumn<Persona> celular = new MFXTableColumn<>("Celular", true, Comparator.comparing(Persona::getNumeroCelular));
        MFXTableColumn<Persona> tipoDocumento = new MFXTableColumn<>("TipoDocumento", true, Comparator.comparing(Persona::getTipoDocumento));
        MFXTableColumn<Persona> numeroDocumento = new MFXTableColumn<>("Numero de documento", true, Comparator.comparing(Persona::getNumeroDocumento));


        nombres.setRowCellFactory(person -> new MFXTableRowCell<>(Persona::getNombres));
        apellidos.setRowCellFactory(person -> new MFXTableRowCell<>(Persona::getApellidos));
        correo.setRowCellFactory(person -> new MFXTableRowCell<>(Persona::getCorreo));
        celular.setRowCellFactory(person -> new MFXTableRowCell<>(Persona::getNumeroCelular));
        tipoDocumento.setRowCellFactory(person -> new MFXTableRowCell<>(Persona::getTipoDocumento));
        numeroDocumento.setRowCellFactory(person -> new MFXTableRowCell<>(Persona::getNumeroDocumento));

        this.tabla.getTableColumns().addAll(nombres, apellidos, correo, celular, tipoDocumento, numeroDocumento);
        this.tabla.getFilters().addAll(new StringFilter<>("Correo", Persona::getCorreo), new StringFilter<>("Celular", Persona::getNumeroCelular), new StringFilter<>("Numero de documento", Persona::getNumeroDocumento), new StringFilter<>("Tipo de documento", Persona::getTipoDocumento));
    }

    private Optional<Dao<Persona>> obtenerCrud() {
        Dao<Persona> dao = null;
        switch (this.boxTipo.getSelectedIndex()) {
            case 0 -> dao = this.personaMemoriaDao;
            case 1 -> dao = this.personaArchivoDao;
            case 2 -> dao = this.personaOracleDao;
        }
        return dao != null ? Optional.of(dao) : Optional.empty();
    }

    @FXML
    private void selectTipoCrud() {
        Optional<Dao<Persona>> dao = this.obtenerCrud();
        if (dao.isPresent()) {
            this.personaDao = dao.get();
            this.tabla.setItems(FXCollections.observableArrayList(this.personaDao.getAll()));
        } else {
            Alerta.mostrarAlertError("No se pudo obtener el crud");
        }
    }

    @FXML
    private void abrirFormularioRegistrar() {
        this.abrirFormulario(FormularioControllerImpl.REGISTRAR);
    }

    @FXML
    private void abrirFormularioActualizar() {
        if (!this.tabla.getSelectionModel().getSelection().isEmpty()) {
            this.abrirFormulario(FormularioControllerImpl.ACTUALIZAR);
        } else {
            Alerta.mostrarAlertWarning("No se ha seleccionado un registro");
        }
    }

    @FXML
    private void borrarPersona() {
        if (!this.tabla.getSelectionModel().getSelection().isEmpty()) {
            Persona persona = this.tabla.getSelectionModel().getSelectedValues().get(0);
            Boolean borrarPersona = this.personaDao.delete(persona.getId());
            this.tabla.getSelectionModel().getSelection().clear();
            if (borrarPersona) {
                this.actualizarTabla();
                Alerta.mostrarAlertInfo("Se ha Borrado el registro exitosamente");
            } else {
                Alerta.mostrarAlertError("No se pudo borrar el registro");
            }
        } else {
            Alerta.mostrarAlertWarning("No se ha seleccionado un registro");
        }
    }

    private void abrirFormulario(final String titulo) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FORMULARIO));
            Parent root1 = fxmlLoader.load();
            FormularioControllerImpl formularioController = fxmlLoader.getController();
            formularioController.formalizar(titulo, this);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(titulo);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (Exception e) {
            Alerta.mostrarAlertError("Error al abrir el formulario");
        }
    }

    public void actualizarTabla() {
        this.tabla.setItems(FXCollections.observableArrayList(this.personaDao.getAll()));
    }
}
