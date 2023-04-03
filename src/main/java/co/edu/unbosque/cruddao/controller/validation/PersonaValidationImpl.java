package co.edu.unbosque.cruddao.controller.validation;

public class PersonaValidationImpl {

    public static String validarNombres(final String nombre) {
        String mensaje = null;
        if (nombre == null || nombre.isEmpty()) {
            mensaje = "El nombre no puede estar vacio";
        }else {
            if (nombre.length() > 50) {
                mensaje = "El nombre no puede ser mayor a 50 caracteres";
            }
        }

        return mensaje;
    }

    public static String validarApellidos(final String apellido) {
        String mensaje = null;
        if (apellido == null || apellido.isEmpty()) {
            mensaje = "El apellido no puede estar vacio";
        }else {
            if (apellido.length() > 50) {
                mensaje = "El apellido no puede ser mayor a 50 caracteres";
            }
        }

        return mensaje;
    }

    public static String validarNumeroDocumento(final String numeroDocumento) {
        String mensaje = null;
        if (numeroDocumento == null || numeroDocumento.isEmpty()) {
            mensaje = "El numero de documento no puede estar vacio";
        } else {
            if (numeroDocumento.length() > 20) {
                mensaje = "El numero de documento no puede ser mayor a 50 caracteres";
            }

            if (!numeroDocumento.matches("[0-9]+")) {
                mensaje = "El numero de documento solo puede contener numeros";
            }
        }

        return mensaje;
    }

    public static String validarTipoDocumento(final String tipoDocumento) {
        String mensaje = null;
        if (tipoDocumento == null || tipoDocumento.isEmpty()) {
            mensaje = "El tipo de documento no puede ser vacio";
        } else {
            if (tipoDocumento.length() > 2) {
                mensaje = "El tipo de documento no puede ser mayor a 50 caracteres";
            }
        }

        return mensaje;
    }

    public static String validarCelular(final String celular) {
        String mensaje = null;
        if (celular == null || celular.isEmpty()) {
            mensaje = "El celular no puede estar vacio";
        } else {
            if (celular.length() > 20) {
                mensaje = "El celular no puede ser mayor a 50 caracteres";
            }

            if (!celular.matches("[0-9]+")) {
                mensaje = "El celular solo puede contener numeros";
            }
        }

        return mensaje;
    }

    public static String validarCorreo(final String correo) {
        String mensaje = null;
        if (correo == null || correo.isEmpty()) {
            mensaje = "El correo no puede estar vacio";
        } else {
            if (correo.length() > 50) {
                mensaje = "El correo no puede ser mayor a 50 caracteres";
            }

            if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                mensaje = "El correo no tiene un formato valido";
            }
        }

        return mensaje;
    }
}
