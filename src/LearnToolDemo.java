import java.io.FileWriter;
import java.io.IOException;

import org.ansj.dic.LearnTool;
import org.ansj.domain.Nature;
import org.ansj.splitWord.analysis.NlpAnalysis;

import element_extract.Reader;


public class LearnToolDemo {
	  public static void main(String[] args) throws IOException {

		  String path="C:/Users/Administrator/Desktop/ElmtExtract/";
		  FileWriter out=new FileWriter(path+"out.txt");
		
		  
		  //构建一个新词学习的工具类。这个对象。保存了所有分词中出现的新词。出现次数越多。相对权重越大。
          LearnTool learnTool = new LearnTool() ;
          String fileStr=Reader.readFileStr(path+"coae.txt");

          //进行词语分词。也就是nlp方式分词，这里可以分多篇文章
          NlpAnalysis.parse("说过，社交软件也是打着沟通的平台，让无数寂寞男女有了肉体与精神的寄托。", learnTool) ;
          NlpAnalysis.parse("其实可以打着这个需求点去运作的互联网公司不应只是社交类软件与可穿戴设备，还有携程网，去哪儿网等等，订房订酒店多好的寓意", learnTool) ;
          out.write(NlpAnalysis.parse(fileStr,learnTool).toString());
          out.close();
          

          //取得学习到的topn新词,返回前10个。这里如果设置为0则返回全部
          System.out.println(learnTool.getTopTree(100));

          //只取得词性为Nature.NR的新词
          System.out.println(learnTool.getTopTree(100,Nature.NW));

      }

}
