module com.yb.sparadrap {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.yb.sparadrap to javafx.fxml;
    exports com.yb.sparadrap;
}