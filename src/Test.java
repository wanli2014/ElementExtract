import java.io.IOException;
import element_extract.PairExtract;
import element_extract.PairResult;
import element_extract.Reader;


public class Test {
	public static void main(String[] args) throws IOException{
		String paragraph="为什么我喜欢买山寨？";
//		String paragraph=Reader.readFileStr("C:/Users/Administrator/Desktop/ElmtExtract/识别2.txt");
		PairExtract pe=new PairExtract();
		PairResult rst=pe.extractPair(paragraph);
		
		System.out.println("该句得分：\t"+rst.score);
		System.out.println("评价元组：");
		for(String[] pair:rst.pairsVct ){
			System.out.println("\t"+pair[0]+"\t"+pair[1]+"\t"+pair[2]+"\t"+pair[3]);
		}
		
	} 

}
