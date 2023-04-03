package co.edu.unbosque.cruddao.model.dao;

import co.edu.unbosque.cruddao.model.Persona;
import co.edu.unbosque.cruddao.model.persistence.OperacionArchivo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PersonaArchivoDao implements Dao<Persona> {

    private final PersonaMemoriaDao memoria;

    private final OperacionArchivo<Persona> archivo;

    public PersonaArchivoDao() {
        this.memoria = new PersonaMemoriaDao();
        this.archivo = new OperacionArchivo<>(
                "src\\main\\resources\\co\\edu\\unbosque\\crud_dao\\database\\personas.data");
        this.memoria.saveAll(this.leerArchivo());
    }

    @Override
    public Optional<Persona> get(final Integer id) {
        return this.memoria.get(id);
    }

    @Override
    public List<Persona> getAll() {
        return this.memoria.getAll();
    }

    @Override
    public Optional<Persona> save(final Persona persona) {
        Optional<Persona> save = this.memoria.save(persona);
        return save.isPresent() ? this.escribirArchivo() != null ? save : Optional.empty() : Optional.empty();
    }

    @Override
    public Optional<Persona> update(final Persona persona) {
        Optional<Persona> update = this.memoria.update(persona);
        return update.isPresent() ? this.escribirArchivo() != null ? update : Optional.empty() : Optional.empty();
    }

    @Override
    public Boolean delete(final Integer id) {
        return this.memoria.delete(id) && (this.escribirArchivo() != null);
    }

    private List<Persona> escribirArchivo() {
        List<Persona> escribirArchivo = this.archivo.escribirArchivo(this.memoria.getAll());
        if (escribirArchivo == null) {
            this.memoria.saveAll(this.leerArchivo());
        }
        return escribirArchivo;
    }

    private List<Persona> leerArchivo() {
        List<Persona> leerArchivo;
        leerArchivo = this.archivo.leerArchivo();
        if (leerArchivo == null) {
            leerArchivo = Collections.emptyList();
        }
        return leerArchivo;
    }
}
