package servidorCliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Cliente {

	@SuppressWarnings("resource")
	public static void main(String args[]){
		
		String simetrico = "BLOWFISH";
		String hmac = "HMACSHA256";
		
		try {
			Socket socket=new Socket("localhost",19999);
			InputStream in=socket.getInputStream();
			OutputStream out=socket.getOutputStream();
			PrintWriter printer=new PrintWriter(out,true);
			BufferedReader reader=new BufferedReader(new InputStreamReader(in));
			
			Protocolo protocolo = new Protocolo();
			protocolo.setAlgSimetrico(simetrico);
			protocolo.setAlgHmac(hmac);
			
			protocolo.procesarCadena(in,out,printer,reader);
	
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
