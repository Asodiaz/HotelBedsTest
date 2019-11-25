package com.example.demo.service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.*;
import org.springframework.stereotype.Service;
import static com.example.demo.constants.Constants.*;

@Service
public class HackerDetectorImpl implements HackerDetector{

	public HackerDetectorImpl() {
	}

	public String checkFile() throws IOException, ParseException {
		String result = "null";
		File file = new File(
				getClass().getClassLoader().getResource("log").getFile()
		);

		FileReader fr = new FileReader(file);
		BufferedReader br= new BufferedReader(fr);  //creates a buffering character input stream
		String line, primerTiempoDate, ultimoTiempoDate;
		String[] stringList;
		Calendar primerCal = Calendar.getInstance();
		Calendar ultimoCal = Calendar.getInstance();
		long primerTiempo, ultimoTiempo;
		int contador = 0;
		Date primerDate = null, ultimoDate = null;
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");

		while((line = br.readLine())!=null){
			result = this.parseLine(line);

			if(!result.equals("Null")){
				contador++;
				stringList = line.split(",");
				if(contador == 1){
					primerTiempo = Long.parseLong(stringList[1]);
					primerTiempoDate =  sdf2.format(new java.util.Date (primerTiempo*1000));
					primerDate = sdf2.parse(primerTiempoDate);
					primerCal.setTime(primerDate);
				}
				if(contador == 5){
					ultimoTiempo = Long.parseLong(stringList[1]);
					ultimoTiempoDate =  sdf2.format(new java.util.Date (ultimoTiempo*1000));
					ultimoDate = sdf2.parse(ultimoTiempoDate);
					ultimoCal.setTime(ultimoDate);

					int diff = ultimoCal.get(Calendar.MINUTE) - primerCal.get(Calendar.MINUTE);

					if(diff < 5){
						return stringList[0];
					}
					contador = 0;
				}
			}
		}
		return result;
	}

	@Override
	public String parseLine(String line) {
		String[] stringList = line.split(",");

		String action = stringList[2];

        if(action.equals(SIGNAL_FAILURE)){
        	return "Fail";
		}
		
		return "Null";
	}

	public Integer timeCalculator() throws ParseException {
		int resultMins = 0;

		String pattern = "EEE, dd MMM yyyy HH:mm:ss Z";

		String time1 = "Sat, 13 Mar 2010 11:29:05 -0800";
		String time2 = "Sat, 13 Mar 2010 11:35:05 -0800";

		SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);

		TimeZone.getTimeZone("GMT");

		Date javaDate1 = format.parse(time1);
		Date javaDate2 = format.parse(time2);

		Long diferenciaEn_ms = javaDate2.getTime() - javaDate1.getTime();

		resultMins = (int) Math.floor((diferenciaEn_ms / 1000)/ 60);

		return resultMins;
	}
}
