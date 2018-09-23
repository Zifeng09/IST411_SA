package httpapp_wxo;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.InetAddress;
import java.net.URL;

public class HTTPClient {

    public HTTPClient() throws Exception {
        System.out.println("HTTP Client Started");
        try {
            InetAddress serverInetAddress = InetAddress.getByName("127.0.0.1");
            Socket connection = new Socket(serverInetAddress, 8000);

            try (OutputStream out = connection.getOutputStream();
                 BufferedReader in = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
                sendGet(out);
                sendPost();
                System.out.println(getResponse(in));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void sendGet(OutputStream out) {
        
        try {
            System.out.println("\nSending 'GET' request to URL : " );
            // Send Request
            out.write("GET /index HTTP/1.0\r\n".getBytes());
            // Send Header
            
            out.write("User-Agent: Mozilla/5.0\r\n".getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
     private void sendPost() throws Exception {

        String url = "http://localhost:8000";
         URL obj  = new URL(url);
   
         HttpURLConnection con = (HttpURLConnection) obj.openConnection();

         //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

         String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

       // Send post request
        con.setDoOutput(true);
        con.setDoInput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

       int responseCode = con.getResponseCode();
       System.out.println("\nSending 'POST' request to URL : " + url);
       System.out.println("Post parameters : " + urlParameters);
       System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

       while ((inputLine = in.readLine()) != null) {
        response.append(inputLine);
    }
       in.close();

    //print result
        System.out.println(response.toString());
        }
    
    
    

    private String getResponse(BufferedReader in) {
        try {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }
            return response.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) throws Exception {
        new HTTPClient();
    }
}
 
