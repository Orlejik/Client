package org.example;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    static String osName = System.getProperty("os.name");
    static String osArch = System.getProperty("os.arch");
    static String osVersion = System.getProperty("os.version");
    static String userName = System.getProperty("user.name");
    // send the information to the server
    static String pcInfo = "\n\t\tOperation System Name: " + osName +"\n\t\t"+
            "Operation System Architecture : "+osArch+"\n\t\t" +
            "Operation System Version : "+osVersion + "\n\t\t"+
            "User Name : "+userName +"\n";


    private static DataOutputStream dataOutputStream = null;
    private static DataInputStream dataInputStream = null;
    private static BufferedWriter out; //writer in socket
    private static BufferedReader in; //writer in socket
    public static void main(String[] args) throws InterruptedException {

        while(true){
            try(Socket socket = new Socket("10.30.0.149", 2412)){
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(curTime());
                System.out.println("Sending the data to the server :");
                System.out.println(pcInfo);
                out.write(pcInfo);
//                out.flush();

//                String serverResponse = in.readLine();
//                System.out.println(serverResponse);

                dataInputStream.close();
                dataOutputStream.close();

            }catch (IOException e){
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Thread.sleep(30000);
        }
    }

    private static String curTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}