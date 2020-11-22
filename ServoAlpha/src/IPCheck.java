
import java.net.*;
public class IPCheck {
    public static void main(String[] args) {
        InetAddress myIP = null;
        try {
            myIP = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System. out.println( "ошибка доступа ->" + e);
        }
        System. out.println( " Мой IP ->" + myIP);
    }
}