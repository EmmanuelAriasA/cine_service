package com.cine.salaService.business;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.cine.salaService.dto.Ack;
import com.cine.salaService.dto.Sala;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

@Component
public class SalaService {

	private static String salasCsv = ".\\src\\main\\resources\\csv\\salas.csv";

	public Ack createSala(Sala sala) {
		Ack ack = new Ack();

		if (buscarSala(sala.getIdSala()) == null) 
		{

			try {
				FileWriter writer = new FileWriter(salasCsv, true);
				CSVWriter csvWriter = new CSVWriter(writer);

				String[] linea = { Integer.toString(sala.getIdSala()), Integer.toString(sala.getCantFilas()),
						Integer.toString(sala.getCantColumnas()), sala.getEstado() };

				csvWriter.writeNext(linea);
				ack.setDescription("Se ha guardado la sala");
				ack.setCode(0);

				csvWriter.close();
			} catch (IOException e) {
				ack.setDescription("Error de almacenamiento");
				ack.setCode(-1);
				e.printStackTrace();
			}
		} 
		else 
		{
			ack.setDescription("Error: Ya existe una sala con ese id");
			ack.setCode(-1);
		}
		return ack;
	}
	
	public Ack readSala(Sala sala)
	{
		Ack ack = new Ack();
		
		if(buscarSala(sala.getIdSala()) != null)
		{
			ack.setCode(0);
			ack.setDescription("id: "+ sala.getIdSala()+"\n"+
					"Estado: "+ sala.getEstado()+"\n"+
					"Cantidad de filas: "+ sala.getCantFilas()+"\n"+
					"Cantidad de columnas: "+ sala.getCantColumnas()+"\n");
		} 
		else 
		{
			ack.setDescription("Error: No existe una sala con ese id");
			ack.setCode(-1);
		}

		
		return ack;
	}
	
	public Sala buscarSala(int id)
	{
		try {
			 
	        FileReader filereader = new FileReader(salasCsv);
	 
	        // create csvReader object passing
	        // file reader as a parameter
	        CSVReader csvReader = new CSVReader(filereader);
	        String[] sala;
	 
	        // we are going to read data line by line
	        while ((sala = csvReader.readNext()) != null) {
	            if(Integer.parseInt(sala[0]) == id)
	            {
	            	return new Sala(Integer.parseInt(sala[0]),Integer.parseInt(sala[1]),Integer.parseInt(sala[2]),sala[3]);
	            }
	        }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		
		return null;
	}

}
