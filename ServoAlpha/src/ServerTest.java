

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    private static Socket socket;
    private static ObjectOutputStream out;
    private static BufferedReader in;
    private static BufferedWriter outBuff;
    private static FileInputStream inF;
    private static DataOutputStream outF;


    public static void main(String[] args) {
        ServerTest server = new ServerTest();
        server.run();
    }


    public void run() {
        try {
            ServerSocket serversocket = new ServerSocket(7800);
            System.out.println("Сервер запущен!");
            socket = serversocket.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outBuff = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String wordFromClient = in.readLine();
            System.out.println("message recieved "+wordFromClient);
            outBuff.write("returned "+wordFromClient+"\n");
            outBuff.flush();
            String str = "Send photo";
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(str);
            out.flush();
            outF = new DataOutputStream(socket.getOutputStream());
            File file = new File("C:/ss/imageTest.jpg");
            BufferedImage bufferedImage = ImageIO.read(file);
            int width=bufferedImage.getWidth(), height=bufferedImage.getHeight();
            inF = new FileInputStream(file);
            byte[] bytes = new byte[width/2*height/2];
            int count;
            long lenght = file.length();
            outBuff.write(width/2*height/2);
            System.out.println("send size "+width/2*height/2);
            outF.writeLong(lenght);
            while ((count = inF.read(bytes)) > -1) {
                outF.write(bytes, 0, count);
            }
            inF.close();
            outF.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
