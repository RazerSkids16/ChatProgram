package ChatProg;

/**
 *
 * @author Reymar U. Sumangue
 */
import java.io.*;
import java.net.*;

public class ServerHost {

    private static ServerSocket servSock = null;
    private static Socket clientSock = null;
    private static final int maxCients = 20;
    private static final Threads[] chatThreads = new Threads[maxCients];
    

    public static void main(String[] args) {
        int portNum = 3306;

        if (args.length < 1) {
            System.out.println(">>>SERVER<<<\n"
                    + "Currently at Port Number: " + portNum);
        } else {
            portNum = Integer.valueOf(args[0]).intValue();
        }

        try {
            servSock = new ServerSocket(portNum);
        } catch (IOException ex) {
            System.out.println(ex);
        }

        while (true) {
            try {
                clientSock = servSock.accept();
                int i = 0;

                for (i = 0; i < maxCients; i++) {
                    if (chatThreads[i] == null) {
                        (chatThreads[i] = new Threads(clientSock, chatThreads)).start();
                        break;
                    }
                }
                if (i == maxCients) {
                    PrintStream ps = new PrintStream(clientSock.getOutputStream());
                    ps.println("Unable to reach server at this moment\nPlease try again later!");
                    ps.close();
                    clientSock.close();
                }
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
}
