import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
public class NioHttpServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocket = ServerSocketChannel.open();
        serverSocket.bind(new InetSocketAddress(PORT));
        serverSocket.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("The server is running on the port: " + PORT);




        while (true) {
            selector.select();
            for (SelectionKey key : selector.selectedKeys()) {
                if (key.isAcceptable()) accept(selector, serverSocket);
            }
            selector.selectedKeys().clear();
        }
    }

    public static void accept(Selector selector, ServerSocketChannel serverSocket) throws IOException{
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
        System.out.println("New connection: " + client.getRemoteAddress());
    }




}
