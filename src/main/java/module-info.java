module pong.example.ponggame {
    requires javafx.controls;
    requires javafx.fxml;


    opens pong.example.ponggame to javafx.fxml;
    exports pong.example.ponggame;
}