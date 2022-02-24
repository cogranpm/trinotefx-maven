module hellofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.glensoft.trinote to javafx.fxml;
    exports com.glensoft.trinote;
}