module com.yb.sparadrap {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;


    opens com.yb.sparadrap to javafx.fxml;
    opens com.yb.sparadrap.controller to javafx.fxml;

    exports com.yb.sparadrap;
    exports com.yb.sparadrap.controller;
    exports com.yb.sparadrap.model;
    exports com.yb.sparadrap.model.enums;
    exports com.yb.sparadrap.view;
}