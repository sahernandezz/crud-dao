package co.edu.unbosque.cruddao.model.dao;

import co.edu.unbosque.cruddao.model.Persona;
import co.edu.unbosque.cruddao.model.persistence.Jdbc;


public class PersonaOracleDao extends PersonaSql {

    public PersonaOracleDao() {
        this.setJdbc(new Jdbc(
                "PERSONA",
                "jdbc:oracle:thin:@//localhost:1521/xe",
                "oracle.jdbc.OracleDriver",
                "lola",
                "elperri2022",
                new Persona()
        ));
    }

}
