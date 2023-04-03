module co.edu.unbosque.crud_dao {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires org.controlsfx.controls;
    requires MaterialFX;
    requires java.sql;


    opens co.edu.unbosque.cruddao.controller to javafx.fxml;
    opens co.edu.unbosque.cruddao.model to javafx.fxml;
    exports co.edu.unbosque.cruddao.controller;
    exports co.edu.unbosque.cruddao.model;

}