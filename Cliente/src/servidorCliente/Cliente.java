package servidorCliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import uniandes.gload.core.Task;


public class Cliente extends Task{

	@Override
	public void fail() {
		System.out.println(Task.MENSAJE_FAIL);
		
	}

	@Override
	public void success() {
		System.out.println(Task.OK_MESSAGE);
		
	}

	@Override
	public void execute() {
	
		String simetrico = "BLOWFISH";
		String hmac = "HMACSHA256";
		
		Socket socket=null;
		try {
			
			socket=new Socket("172.24.42.86",9090);
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
