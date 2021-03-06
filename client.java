import java.net.*;
import java.io.*;

class Client {

	Socket socket;
	BufferedReader br;
	PrintWriter out;

	public Client() {
		try {
			System.out.println("Sending request to server");
			socket = new Socket("127.0.0.1", 7777);
			System.out.println("connection done.");
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			out = new PrintWriter(socket.getOutputStream());

			startReading();
			startWriting();

		} catch (Exception e) {

		}
	}

	public void startReading() {
		// ??thread to read
		Runnable r1 = () -> {
			System.out.println("Reader started");
			while (true) {
				try {
					String msg = br.readLine();
					if (msg.equals("exit")) {
						System.out.println("Server terminated the chat");

						socket.close();
						break;
					}

					System.out.println("Server : " + msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};
		new Thread(r1).start();
	}

	public void startWriting() 
	{
		//thread to take data from the user and send to the client
		Runnable r2=()->{
		System.out.println("writer started...");
		try {
			while(!socket.isClosed()) {	
		BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));	
					String content = br1.readLine();
					out.println(content);
					out.flush();
					if(content.equals("exit")) {
						socket.close();
						break;
					}
				}
				System.out.println("connection closed");
				}catch(Exception e) {
					e.printStackTrace();//
				}
			};
			new Thread(r2).start();
	}
public static void main(String[] args) {
//chaini
//tuin
System.out.println("this is client...");
new Client();
}
}