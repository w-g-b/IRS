import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
 
public class ChatServer {
    public static void main(String[] args) throws Exception {
        ChatServer cs = new ChatServer();
        // cs.setUpSever(Integer.parseInt(args[0]));
        cs.setUpSever(9632);
    }
 
    HashMap<String, Socket> scMap = new HashMap<>();
 
    public void setUpSever(int port) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("服务器创建成功！！！");
        Scanner scan = new Scanner(System.in);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String msg = "管理员消息: " + scan.nextLine();
                    for (Map.Entry<String, Socket> entry : scMap.entrySet()) {
                        BufferedOutputStream bos = null;
                        try {
                            bos = new BufferedOutputStream(entry.getValue().getOutputStream());
                            bos.write(msg.getBytes());
                            bos.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        while (true) {
            try {
                Socket client = serverSocket.accept();
                BufferedInputStream bis = new BufferedInputStream(client.getInputStream());
                byte[] bytes = new byte[1024];
                int len = bis.read(bytes);
                String str = new String(bytes, 0, len);
                for (Map.Entry<String, Socket> entry : scMap.entrySet()) {
                    String msg = str + " 加入到聊天室中！";
                    BufferedOutputStream bos = new BufferedOutputStream(entry.getValue().getOutputStream());
                    bos.write(msg.getBytes());
                    bos.flush();
                }
                scMap.put(str, client);
                System.out.println(str + "，连接到服务器");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                int len = bis.read(bytes);
                                String msg = str + " say :" + new String(bytes, 0, len);
                                System.out.println(msg);
                                for (Map.Entry<String, Socket> entry : scMap.entrySet()) {
                                    if (str.equals(entry.getKey())) {
                                        continue;
                                    }
                                    BufferedOutputStream bos = new BufferedOutputStream(entry.getValue().getOutputStream());
                                    bos.write(msg.getBytes());
                                    bos.flush();
                                }
                            } catch (SocketException se) {
                                scMap.remove(str);
                                System.out.println(str + "退出聊天室！");
                                for (Map.Entry<String, Socket> entry : scMap.entrySet()) {
                                    String msg = str + " 退出聊天室！";
                                    BufferedOutputStream bos = null;
                                    try {
                                        bos = new BufferedOutputStream(entry.getValue().getOutputStream());
                                        bos.write(msg.getBytes());
                                        bos.flush();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                break;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
 
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}