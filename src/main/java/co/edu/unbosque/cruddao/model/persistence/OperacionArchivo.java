package co.edu.unbosque.cruddao.model.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OperacionArchivo<T> {

    private String archivo;

    /**
     * Constructor de la clase OperacionArchivo
     *
     * @param archivo file
     */
    public OperacionArchivo(final String archivo) {
        this.archivo = archivo;
    }

    /**
     * Método para leer un archivo
     */
    public List<T> leerArchivo() {
        List<T> lista = null;
        FileInputStream fichero = null;
        try {
            fichero = new FileInputStream(this.archivo);
            ObjectInputStream objectInputStream = new ObjectInputStream(fichero);
            lista = (List<T>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        } finally {
            try {
                assert fichero != null;
                fichero.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return lista;
    }

    /**
     * Método para escribir en un archivo
     */
    public List<T> escribirArchivo(final List<T> lista) {
        FileOutputStream fichero = null;
        try {
            fichero = new FileOutputStream(this.archivo);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fichero);
            objectOutputStream.writeObject(new ArrayList<>(lista));
            return lista;
        } catch (IOException ex) {
            return null;
        } finally {
            try {
                assert fichero != null;
                fichero.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}