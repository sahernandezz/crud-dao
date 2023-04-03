package co.edu.unbosque.cruddao.model;


import lombok.*;
import java.io.Serializable;
@Data
public class Persona implements Serializable {

    private Integer id;

    private String nombres;

    private String apellidos;

    private String tipoDocumento;

    private String numeroDocumento;

    private String numeroCelular;

    private String correo;

    public Persona() {

    }
}
