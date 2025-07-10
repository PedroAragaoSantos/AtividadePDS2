module principal.lojamvc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;

    opens controller to javafx.fxml;
    opens model to javafx.base, javafx.fxml;

    exports principal;
}