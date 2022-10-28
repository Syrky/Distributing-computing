package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Processor of HTTP request.
 */
public class Processor extends Thread{
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }

    @Override
    public void run(){
        try {
            process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void process() throws IOException{
        String req = request.toString();
        PrintWriter output = new PrintWriter(socket.getOutputStream());

        if (req.contains("create")) {
            String request = "Create";
            String name = "";
            int pos = 12;
            while (req.charAt(pos) != ' ') {
                char a = req.charAt(pos);
                name += a;
                pos++;
            }
            File myFile = new File(name);
            System.out.print(request);
            System.out.println();
            if (myFile.createNewFile()) {
                output.println("HTTP/1.1 200 OK");
                output.println("Content-Type: text/html; charset=utf-8");
                output.println();
                output.println("<html>");
                output.println("<body><p>File: " + name + " was successfully created</p><body>");
                output.println("</html>");
            } else {
                output.println("HTTP/1.1 200 OK");
                output.println("Content-Type: text/html; charset=utf-8");
                output.println();
                output.println("<html>");
                output.println("<body><p>File" + name + "already exists.</p><body>");
                output.println("</html>");
            }
        }

        else if(req.contains("delete")) {
            String request = "Delete";
            String name = "";
            int pos = 12;
            while(req.charAt(pos) != ' '){
                char a = req.charAt(pos);
                name += a;
                pos++;
            }
            File file = new File(name);
            System.out.print(request);
            System.out.println();
            if (file.delete()) {
                output.println("HTTP/1.1 200 OK");
                output.println("Content-Type: text/html; charset=utf-8");
                output.println();
                output.println("<html>");
                output.println(file.getName() + " is deleted!");
                output.println("</html>");
            } else {
                output.println("HTTP/1.1 200 OK");
                output.println("Content-Type: text/html; charset=utf-8");
                output.println();
                output.println("<html>");
                output.println("File " + file.getName() + " doesn't exist.");
                output.println("</html>");
            }
        }
        else if(req.contains("exec")){
            String request = "Execute";
            System.out.print(request);
            System.out.println();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // We are returning a simple web page now.
        output.println("HTTP/1.1 200 OK");
        output.println("Content-Type: text/html; charset=utf-8");
        output.println();
        output.println("<html>");
        output.println("<head><title>Hello</title></head>");
        output.println("<body><p>Hello, world!</p></body>");
        output.println("</html>");
        output.flush();
        socket.close();
    }
}
