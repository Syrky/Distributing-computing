package org.example;

import java.net.Socket;

public class T {
    public Socket socket;
    public HttpRequest request;

    public T(Socket socket, HttpRequest request){
        this.socket = socket;
        this.request = request;
    }
}
