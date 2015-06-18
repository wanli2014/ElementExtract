package theme;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;

import element_extract.Reader;

public class ExtractTheme {
public static void main(String[] args) throws IOException{
		
		String sen = Reader.readFileStr("F:/CWL/coae.txt");
		String[] lines=sen.split("\n");
//		StopWord stop_word=new StopWord("C:/Users/Administrator/Desktop/ElmtExtract/stop_word.txt");
		String article="";
		for(String line:lines){
			List<Term> parse=NlpAnalysis.parse(line);
			
			for(Term term:parse){
				String nature=term.getNatureStr();
				if(!nature.equals("null")&&!nature.startsWith("w")){//!term.getName().equals(" ")&&
//					System.out.print(term+" ");
//					article+=term+" ";
					
					if(nature.startsWith("n")||nature.startsWith("t")||nature.startsWith("s")
							||nature.startsWith("f")||nature.startsWith("v")||nature.startsWith("a")
							||nature.startsWith("b")||nature.startsWith("z")||nature.startsWith("d")
						){
							article+=term+" ";
						}
				}
//				if(term.getName().equals(" "))	System.out.println(term.getName()+"\t"+term.getNatureStr());
				
			}
			
			

			article+="\r\n";
		}
		
		FileWriter out=new FileWriter("F:/CWL/coae_out.txt");
		out.write(article);
		out.close();
		
	}

}
