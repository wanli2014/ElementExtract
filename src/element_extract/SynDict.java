package element_extract;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

public class SynDict {
	Hashtable<String,Integer> wordTable=new Hashtable<String,Integer>();
	Vector<String> rootvect=new Vector<String>();
	
	SynDict(String dictPath) throws IOException{
		String dictStr=Reader.readFileStr(dictPath);
		String[] lines=dictStr.split("\n");
		int index=0;
		for(String line:lines){
			String[] attr=line.split(" ");
			for(int i=1;i<attr.length;++i){
				wordTable.put(attr[i], index++);
				rootvect.add(attr[1]);
			}
		}
	}

	public String findRoot(String word) {
		Integer index=wordTable.get(word);
		if(index!=null) return rootvect.get(index);
		else return null;
	}

}
