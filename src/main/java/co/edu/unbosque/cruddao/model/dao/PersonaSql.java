package co.edu.unbosque.cruddao.model.dao;

import co.edu.unbosque.cruddao.model.Persona;
import co.edu.unbosque.cruddao.model.persistence.Jdbc;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

public class PersonaSql implements Dao<Persona> {

    @Setter
    private Jdbc jdbc;

    public PersonaSql() {

    }

    @Override
    public Optional<Persona> get(final Integer id) {
        return this.jdbc.findById(id);
    }

    @Override
    public List<Persona> getAll() {
        return jdbc.findAll();
    }

    @Override
    public Optional<Persona> save(final Persona persona) {
        return this.jdbc.save(persona);
    }

    @Override
    public Optional<Persona> update(final Persona persona) {
        return this.jdbc.update(persona, persona.getId());
    }

    @Override
    public Boolean delete(final Integer id) {
        return this.jdbc.delete(id);
    }
}
