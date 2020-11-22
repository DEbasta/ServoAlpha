
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientTest {
    private static Socket socket;
    private static ObjectOutputStream outObj;
    private static BufferedReader input,reader;
    private static BufferedWriter out;
    private static String wordRet;

    public static void main(String[] args) {
        ClientTest client = new ClientTest();
        ClientTest.startClient();
    }


    public static void startClient(){
        try {
            socket = new Socket("192.168.0.105", 7800);
            String object;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(System.in));
            String word = String.valueOf(reader.readLine());//отправляемое слово
            out.write(word + "\n"); // отправляем сообщение на сервер
            out.flush();
            wordRet = input.readLine(); // ждём, что скажет сервер
            System.out.println("this is returning text from server " + wordRet);
            while (socket != null) {
                ObjectInputStream obIn = new ObjectInputStream(socket.getInputStream());
                while ((object = (String) obIn.readObject()) != null) {
                    if (object.equals("Send photo")) {
                        FileOutputStream out = new FileOutputStream("image2.jpg");
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        byte[] bytes = new byte[5*1024];
                        int count, total = 0;
                        long lenght = in.readLong();
                        while ((count = in.read(bytes)) > -1) {
                            total += count;
                            out.write(bytes, 0, count);
                            if (total == lenght) break;
                        }
                        out.close();
                    }
                }
            }
            System.out.println("yet finished");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
