module com.example.stringchatgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jfr;


    opens com.example.stringchatgui to javafx.fxml;
    exports com.example.stringchatgui;
}