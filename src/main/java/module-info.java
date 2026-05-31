module com.example.joinup {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.joinup to javafx.fxml;
    exports com.example.joinup;
}