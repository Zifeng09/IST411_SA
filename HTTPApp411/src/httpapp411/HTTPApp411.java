/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package httpapp411;

/**
 *
 * @author zjx5013
 */
public class HTTPApp411 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //New client
        WebServer theWebServer = new WebServer();
        HTTPServer411 theServer = new HTTPServer411();

        HTTPClient theClient = new HTTPClient();
        URLConnection connection = new URLConnection();
    }
    
}
