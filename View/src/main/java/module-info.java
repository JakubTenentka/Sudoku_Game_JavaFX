module ViewProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires ModelProject;
    requires velocity.tools;
    requires java.desktop;
    requires java.sql;

    opens org.View to javafx.fxml;
    exports org.View;
}
