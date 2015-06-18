import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

import element_extract.Reader;




public class TestFenci {
	
	public static void main(String[] args) throws IOException{
		
		String sen = Reader.readFileStr("C:/Users/Administrator/Desktop/c1.txt");
		
		FileWriter out=new FileWriter("C:/Users/Administrator/Desktop/c1-rst.txt");
//		out.write(NlpAnalysis.parse(sen).toString());
//		out.write(ToAnalysis.parse(sen).toString());
		out.write(BaseAnalysis.parse(sen).toString());
		out.close();
		
//		while(true){
////			Scanner s = new Scanner(System.in);
////			String str = s.next();
//			String str="黄晓明陈乔恩吻戏";
//			if(str.equals("break")) break;
//			System.out.println(BaseAnalysis.parse(str));
//		}
		
	
	}

}
