package hello.servlet.basic;


import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;

@Getter @Setter
public class HelloData {

    private String username;
    private int age;

}
