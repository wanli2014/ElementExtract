package element_extract;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import org.nlpcn.commons.lang.util.WordAlert;

public class SentDict {
	Hashtable<String,Integer> words=new Hashtable<String,Integer>();
	Vector<String> wordvect=new Vector<String>();
	Vector<Integer> wordsPolar=new Vector<Integer>();
	Vector<Integer> strength=new Vector<Integer>();
	Vector<String> pos=new Vector<String>();
	
	SentDict(String dictPath) throws IOException{
		String dictStr=Reader.readFileStr(dictPath);
		String[] item=dictStr.split("\n");
		for(int i=0;i<item.length;++i){
			String[] atrbt=item[i].split("\t");
			String wordStr=new String(WordAlert.alertStr(atrbt[0]));
			words.put(wordStr, i);
			wordvect.add(wordStr);
			wordsPolar.add(Integer.parseInt(atrbt[1]));
			strength.add(Integer.parseInt(atrbt[2]));
			pos.add(atrbt[3]);
//			System.out.println(atrbt[3]);
		}
	}
	

}
