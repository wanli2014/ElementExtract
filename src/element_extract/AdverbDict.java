package element_extract;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class AdverbDict {
	Hashtable<String,Integer> advWord=new Hashtable<String,Integer>();
	Vector<String> adv_vect=new Vector<String>();
	Vector<Double> degree=new Vector<Double>();
	Vector<String> adv_POS=new Vector<String>();
	
	AdverbDict(String path) throws IOException{
		String Str=Reader.readFileStr(path);
		String[] lineAry=Str.split("\n");
		for(int i=0;i<lineAry.length;++i){
//			System.out.println(lineAry[i]);
			String[] attr=lineAry[i].split("\t"); 
			advWord.put(attr[0], i);
			adv_vect.add(attr[0]);
			degree.add(Double.parseDouble(attr[1]));
			adv_POS.add(attr[2]);
		}
	}
}
