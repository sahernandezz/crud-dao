package co.edu.unbosque.cruddao.model.persistence;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class Jdbc<T> {
    private T t;

    private String table;

    private String url;

    private String drive;
    private String user;

    private String password;

    private Connection connection;

    public Jdbc(final String table, final String url,final String drive, final String user, final String password, final T t) {
        this.table = table;
        this.url = url;
        this.drive = drive;
        this.user = user;
        this.password = password;
        this.t = t;
    }

    private void connect() {
        try {
            connection = DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<T> findAll() {
        try {
            this.connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.table);
            List<T> list = new ArrayList<>();
            while (rs.next()) {
                list.add(this.parseT(rs));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            this.disconnect();
        }
    }

    public Optional<T> findById(final Integer id) {
        try {
            Optional<T> optionalT = Optional.empty();
            this.connect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + this.table + " WHERE ID = " + id);
            while (rs.next()) {
                optionalT = Optional.of(this.parseT(rs));
                break;
            }
            return optionalT;
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            disconnect();
        }
    }

    public Optional<T> save(final T t) {
        try {
            this.connect();
            Statement stmt = connection.createStatement();
            StringBuilder sql = new StringBuilder("INSERT INTO " + this.table + "(");
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.getName().equals("id")) sql.append(field.getName().toUpperCase(Locale.ROOT)).append(", ");
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 2));
            sql.append(") VALUES (");
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.getName().equals("id")) {
                    if (field.getType().equals(Integer.class)) {
                        sql.append(field.get(t)).append(", ");
                    } else if (field.getType().equals(String.class)) {
                        sql.append("'").append(field.get(t)).append("', ");
                    } else if (field.getType().equals(Date.class)) {
                        sql.append("TO_DATE('").append(field.get(t)).append("', 'dd/MM/yyyy'), ");
                    }
                }
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 2));
            sql.append(")");
            stmt.executeUpdate(sql.toString());
            return Optional.of(t);
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        } finally {
            disconnect();
        }
    }

    public Optional<T> update(final T t, final Integer id) {
        try {
            this.connect();
            Statement stmt = connection.createStatement();
            StringBuilder sql = new StringBuilder("UPDATE " + this.table + " SET ");
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (!field.getName().equals("id")) {
                    if (field.getType().equals(Integer.class)) {
                        sql.append(field.getName().toUpperCase(Locale.ROOT)).append(" = ").append(field.get(t)).append(", ");
                    } else if (field.getType().equals(String.class)) {
                        sql.append(field.getName().toUpperCase(Locale.ROOT)).append(" = '").append(field.get(t)).append("', ");
                    } else if (field.getType().equals(Date.class)) {
                        sql.append(field.getName().toUpperCase(Locale.ROOT)).append(" = TO_DATE('").append(field.get(t)).append("', 'dd/MM/yyyy'), ");
                    }
                }
            }
            sql = new StringBuilder(sql.substring(0, sql.length() - 2));
            t.getClass().getDeclaredField("id").setAccessible(true);
            sql.append(" WHERE ID = ").append(id);
            stmt.executeUpdate(sql.toString());
            return Optional.of(t);
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
            return Optional.empty();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } finally {
            disconnect();
        }
    }

    public Boolean delete(final Integer id) {
        try {
            this.connect();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM " + this.table + " WHERE ID = " + id);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }

    private T parseT(final ResultSet rs) {
        try {
            T t = (T) this.t.getClass().newInstance();
            for (Field field : this.t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = rs.getObject(field.getName().toUpperCase(Locale.ROOT));
                switch (field.getType().getTypeName()) {
                    case "java.lang.Integer" -> field.set(t, Integer.parseInt(String.valueOf(value)));
                    case "java.lang.String" -> field.set(t, String.valueOf(value));
                    case "java.util.Date" -> field.set(t, value);
                    default -> field.set(t, value);
                }
            }
            return t;
        } catch (InstantiationException | IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
