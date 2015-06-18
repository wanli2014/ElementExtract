package element_extract;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Reader {
	public static String readFileStr (String filePath) throws IOException{
		StringBuffer fileStr=new StringBuffer(); 
		InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "utf-8");
	   	BufferedReader inBufRd = new BufferedReader(isr);
	   	String lineStr="";
	   	while((lineStr=inBufRd.readLine())!=null){
	   		fileStr.append(lineStr+"\n");
	   	}
	   	inBufRd.close();
	   	isr.close();			
		return fileStr.toString();		
	}
}
