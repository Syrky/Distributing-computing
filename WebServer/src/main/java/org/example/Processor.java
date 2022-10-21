package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Processor of HTTP request.
 */
public class Processor {
    private final Socket socket;
    private final HttpRequest request;

    public Processor(Socket socket, HttpRequest request) {
        this.socket = socket;
        this.request = request;
    }

    public void process() throws IOException {
        String req = request.toString();
        PrintWriter output = new PrintWriter(socket.getOutputStream());

        if (req.contains("create")) {
            String name = "";
            int pos = 12;
            while (req.charAt(pos) != ' ') {
                char a = req.charAt(pos);
                name += a;
                pos++;
            }
            File myFile = new File(name);

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
//        else if(req.contains("write")) {
//            String filename = "";
//            int pos = 11;
//            while(req.charAt(pos) != '/'){
//                char a = req.charAt(pos);
//                filename += a;
//                pos++;
//            }
//            pos++;
//            String text = "";
//            while(req.charAt(pos) != ' '){
//                char c = req.charAt(pos);
//                text += c;
//                pos++;
//            }
//            FileWriter Writer = new FileWriter(filename);
//            Writer.write(text);
//            Writer.close();
//            output.println("<body><p>Successfully wrote to the file.</p><body>");
//        }

        else if(req.contains("delete")) {
            String name = "";
            int pos = 12;
            while(req.charAt(pos) != ' '){
                char a = req.charAt(pos);
                name += a;
                pos++;
            }
            File file = new File(name);
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
