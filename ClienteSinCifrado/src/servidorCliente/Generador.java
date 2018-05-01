package servidorCliente;

import uniandes.gload.core.LoadGenerator;
import uniandes.gload.core.Task;
import uniandes.gload.examples.clientserver.generator.Generator;

public class Generador {

	private LoadGenerator loadG;
	
	public Generador(){
		Task work=crearTask();
		int nTasks=100;
		int brecha=50;
		
		loadG=new LoadGenerator("Sinn cifrado",nTasks,work,brecha);
		loadG.generate();
	}

	private Task crearTask() {
		return new Cliente();
	}
	
	public static void main(String[] args){
		Generator gen=new Generator();
	}
}
