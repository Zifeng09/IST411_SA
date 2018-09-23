/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpapp_wxo;

/**
 *
 * @author Zifeng Xia
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientHandler implements Runnable {

    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("\nClientHandler Started for " + this.socket);
        handleRequest(this.socket);
        System.out.println("ClientHandler Terminated for " + this.socket + "\n");
    }

    public void handleRequest(Socket socket) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));) {
            String headerLine = in.readLine();
            StringTokenizer tokenizer = new StringTokenizer(headerLine);
            String httpMethod = tokenizer.nextToken();

            if (httpMethod.equals("GET")) {
                System.out.println("Get method processed");
                String httpQueryString = tokenizer.nextToken();
                StringBuilder responseBuffer = new StringBuilder();
                responseBuffer
                        .append("<html><h1>The IST411 WXO Home Page.... </h1><br>")
                        .append("<b>Welcome to WXO web server!</b><BR>")
                        .append("<h1>Your current diary: </h1>")
                        .append("<h3> 9/23/2018</h3>\n<h3>I and my teammates worked on our IST411 assignment, the weather is getting cold</h3>\n")
                        .append("<h1>Please add your diary post here!</h1>")
                        .append("<form>\n" +
                        "  Diary Post:<br>\n" +
                        "  <input type=\"text\" name=\"diary\">\n <input type=\"submit\" value=\"Submit\">"+
                       
                        "</form>")

                        .append("</html>");
                sendResponse(socket, 200, responseBuffer.toString());
            } else {
                System.out.println("The HTTP method is not recognized");
                sendResponse(socket, 405, "Method Not Allowed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendResponse(Socket socket, int statusCode, String responseString) {
        String statusLine;
        String serverHeader = "Server: WebServer\r\n";
        String contentTypeHeader = "Content-Type: text/html\r\n";

        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());) {
            if (statusCode == 200) {
                statusLine = "HTTP/1.0 200 OK" + "\r\n";
                String contentLengthHeader = "Content-Length: " + responseString.length() + "\r\n";

                out.writeBytes(statusLine);
                out.writeBytes(serverHeader);
                out.writeBytes(contentTypeHeader);
                out.writeBytes(contentLengthHeader);
                out.writeBytes("\r\n");
                out.writeBytes(responseString);
            } else if (statusCode == 405) {
                statusLine = "HTTP/1.0 405 Method Not Allowed" + "\r\n";
                out.writeBytes(statusLine);
                out.writeBytes("\r\n");
            } else {
                statusLine = "HTTP/1.0 404 Not Found" + "\r\n";
                out.writeBytes(statusLine);
                out.writeBytes("\r\n");
            }
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}


