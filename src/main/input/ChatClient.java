import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
 
public class ChatClient {
    public static void main(String[] args) throws Exception {
        ChatClient cc = new ChatClient();
        // cc.setUpClient(args[0], Integer.parseInt(args[1]));
        cc.setUpClient("127.0.0.1", 9632);
    }
 
    public void setUpClient(String ip, int port) throws Exception {
 
        System.out.print("请输入用户名: ");
        Scanner scan = new Scanner(System.in);
        String clientName = scan.nextLine();
        Socket socket = new Socket(ip, port);
        System.out.println("客户端 " + clientName + " 连接成功");
        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
        bos.write(clientName.getBytes());
        bos.flush();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                        byte[] bytes = new byte[1024];
                        int len = bis.read(bytes);
                        String str = new String(bytes, 0, len);
                        System.out.println(str);
                    } catch (SocketException se) {
                        System.out.println("服务器断开，退出程序");
                        System.exit(0);
                    } catch (IOException e) {
                        continue;
                    }
 
                }
 
            }
        }).start();
        while (true) {
            String str = scan.nextLine();
            bos.write(str.getBytes());
            bos.flush();
        }
    }
}