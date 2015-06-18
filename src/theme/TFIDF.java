package theme;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import element_extract.Reader;



public class TFIDF {
	public void calTfidf() throws IOException{
		Vector<String> word=new Vector<String>(); 
		Vector<Double> wt=new Vector<Double>(); 
		Vector<Double> tfidf=new Vector<Double>(); 
		
		double total=0;
		String article=Reader.readFileStr("./data/theme.txt");
		String[] lines=article.split("\n");
		for(String line :lines){
			String[] attr=line.split("\t");
			if(word.contains(attr[0])){
				int index=word.indexOf(attr[0]);
				wt.set(index, wt.get(index)+Double.parseDouble(attr[2])*1000);
			}else{
				word.add(attr[0]);
				wt.add(Double.parseDouble(attr[2])*1000);
			}
			total+=Double.parseDouble(attr[2])*1000;
		}
		
		String atcl="";
		int size=word.size();
		for(int i=0;i<size;++i){
			double tf=wt.get(i);
			double rst=tf*Math.log(total/tf);
			tfidf.add(rst);
			atcl+=word.get(i)+"\t"+rst+"\r\n";
		}
		System.out.println(atcl);
		
		
	}
	public static void main(String[] args) throws IOException{
		TFIDF tf=new TFIDF();
		tf.calTfidf();
		
		
	}

}
