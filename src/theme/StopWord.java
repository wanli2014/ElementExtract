package theme;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import org.nlpcn.commons.lang.util.WordAlert;

import element_extract.Reader;

public class StopWord {
	Hashtable<String,Integer> words=new Hashtable<String,Integer>();
	Vector<String> wordvect=new Vector<String>();
	
	public StopWord(String dictPath) throws IOException{
		String dictStr=Reader.readFileStr(dictPath);
		String[] item=dictStr.split("\n");
		for(int i=0;i<item.length;++i){
			String wordStr=new String(WordAlert.alertStr(item[i]));
			words.put(wordStr,i);
			wordvect.add(wordStr);
		}
	}

}
