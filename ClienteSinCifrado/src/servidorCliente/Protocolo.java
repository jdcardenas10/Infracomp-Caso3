package servidorCliente;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.ws.ProtocolException;

public class Protocolo {
	
	//Atributos
	private Certificado certificado;
	
	private String algSimetrico;
	private String algAsimetrico;
	private String algHmac;

	private PublicKey publicKeySer;

	private long inicio;

	private long acabo;

	//Constructor
	public Protocolo() throws Exception {
		certificado=new Certificado();
		algAsimetrico="RSA";
	}

	public void procesarCadena(InputStream in,OutputStream out,PrintWriter printer,BufferedReader reader) throws IOException, CertificateException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{

			printer.println("HOLA");
			
			if (reader.readLine().equals("INICIO")) {
				printer.println("ALGORITMOS:" + algSimetrico + ":" + algAsimetrico + ":" + algHmac);}
			if(reader.readLine().equals("ESTADO:OK")){
				printer.println("CERTCLNT");
			} else {
				throw new ProtocolException("La entrada no es valida en el inicio");
			}

			X509Certificate cert = certificado.certificado;
			byte[] mybyte = cert.getEncoded();
			out.write(mybyte);
			out.flush();
			if(reader.readLine().equals("ESTADO:OK")){
				System.out.println("Se envio el certificado");
			} else {
				throw new ProtocolException("No se pudo enviar el certificado");
			}

			if(!"CERTSRV".equals(reader.readLine())){
				throw new ProtocolException("No ha podido mandar el protocolo del cliente");
			}
			
			try{
			byte[] certificado = new byte[1024];
			in.read(certificado);
			System.out.println(certificado);
			
			X509Certificate certSer = (X509Certificate) CertificateFactory.getInstance("X.509")
					.generateCertificate(new ByteArrayInputStream(certificado));
			publicKeySer = certSer.getPublicKey();
            certSer.verify(publicKeySer);
			System.out.println(certSer.toString());
			
			printer.println("ESTADO:OK");
			} catch (Exception e) {
				e.printStackTrace();
				printer.println("ESTADO:ERROR");
			}

			if("INICIO".equals(reader.readLine())){
			
				inicio=System.currentTimeMillis();
				printer.println("ACT1");
				printer.println("ACT2");
				if("ESTADO:OK".equals(reader.readLine())){
					acabo=System.currentTimeMillis();
					Long tiempo=acabo-inicio;
					File file = new File("./t2.txt"); 
					FileWriter salida = new FileWriter(file, true);
					salida.write("\n" + tiempo);
					salida.close();
					System.out.println("tiempo:"+tiempo);
				}
			}
	}

	//Metodos
	public String getAlgSimetrico() {
		return algSimetrico;
	}

	public void setAlgSimetrico(String algSimetrico) {
		this.algSimetrico = algSimetrico;
	}

	public String getAlgAsimetrico() {
		return algAsimetrico;
	}

	public void setAlgAsimetrico(String algAsimetrico) {
		this.algAsimetrico = algAsimetrico;
	}

	public String getAlgHmac() {
		return algHmac;
	}

	public void setAlgHmac(String algHmac) {
		this.algHmac = algHmac;
	}	
}
