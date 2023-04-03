package co.edu.unbosque.cruddao.model.dao;

import co.edu.unbosque.cruddao.model.Persona;

import java.util.*;

public class PersonaMemoriaDao implements Dao<Persona> {

    private final List<Persona> personas;

    public PersonaMemoriaDao() {
        personas = new ArrayList<>();
    }

    @Override
    public Optional<Persona> get(final Integer id) {
        Optional<Persona> respuesta = Optional.empty();
        for (Persona p : this.personas) {
            if (p.getId().equals(id)) {
                respuesta = Optional.of(p);
            }
        }
        return respuesta;
    }

    @Override
    public List<Persona> getAll() {
        return this.personas;
    }

    @Override
    public Optional<Persona> save(final Persona persona) {
        Optional<Persona> respuesta;
        try {
            this.personas.sort(Comparator.comparing(Persona::getId));
            persona.setId(!this.personas.isEmpty()
                    ? this.personas.get(this.personas.size() - 1).getId() + 1 : 1);
            this.personas.add(persona);
            respuesta = Optional.of(persona);
        } catch (Exception ex) {
            respuesta = Optional.empty();
        }
        return respuesta;
    }

    @Override
    public Optional<Persona> update(final Persona persona) {
        Optional<Persona> respuesta;
        try {
            if (this.personas.removeIf(persona1 -> persona1.getId().equals(persona.getId()))) {
                this.personas.add(persona);
                respuesta = Optional.of(persona);
                this.personas.sort(Comparator.comparing(Persona::getId));
            } else {
                respuesta = Optional.empty();
            }
        } catch (Exception ex) {
            respuesta = Optional.empty();
        }
        return respuesta;
    }

    @Override
    public Boolean delete(final Integer id) {
        boolean respuesta;
        try {
            respuesta = this.personas.removeIf(persona1 -> persona1.getId().equals(id));
            if (respuesta) {
                this.personas.sort(Comparator.comparing(Persona::getId));
            }
        } catch (Exception ex) {
            respuesta = false;
        }
        return respuesta;
    }

    public void saveAll(final List<Persona> lista) {
        this.personas.clear();
        this.personas.addAll(lista);
    }
}