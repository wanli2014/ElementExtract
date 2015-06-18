package element_extract;

import java.io.IOException;
import java.util.List;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.nlpcn.commons.lang.standardization.SentencesUtil;

public class PairExtract {
 
	public PairResult extractPair(String article) throws IOException{

		article=preprocess(article);
		List<String> sentenceList=sentenceSegment(article);
		
		//init dict
		SentDict dict=new SentDict("./dict/sent_dict.data");
//		insertSentWord(dict);
		AdverbDict advDict=new AdverbDict("./dict/adverb_dict.data");
		SynDict syndict=new SynDict("./dict/synonyms.data");
		PairResult result=new PairResult();
		for (String sentence : sentenceList) {
			List<Term> parse=wordSegment(sentence);
//			PairResult subRst=identifyPair(parse,dict,advDict);
			PairResult subRst=identifyPairJIECI(parse,dict,advDict);
			result.addSubRst(subRst);
			System.out.println(sentence);
			System.out.println(parse+"\r\n");
		}
		result=addRoot(result,syndict);

		return result;
	}
	
	private PairResult addRoot(PairResult result, SynDict syndict) {
		int size=result.pairsVct.size();
		for(int i=0;i<size;++i){
			String pair[]=result.pairsVct.get(i);
			String nRoot=syndict.findRoot(pair[0]);
			String aRoot=syndict.findRoot(pair[2]);
			if(nRoot!=null)	pair[0]+="("+nRoot+")";
			if(aRoot!=null)	pair[2]+="("+aRoot+")";
			result.pairsVct.set(i, pair);
		}
		return result;
	}

	@SuppressWarnings("unused")
	private void insertSentWord(SentDict dict) {
		int dictSize=dict.wordvect.size();
		for(int i=0;i<dictSize;++i){
			 UserDefineLibrary.insertWord(dict.wordvect.get(i), dict.pos.get(i), 1000);
		}
	}

	private PairResult identifyPair(List<Term> parse, SentDict dict, AdverbDict advDict) {
		// TODO Auto-generated method stub
		PairResult result=new PairResult();
		String noun="";
		int begin=0;
		float sentScore=0;
		for(int i=0;i<parse.size();++i){
			Integer wordIndex=dict.words.get(parse.get(i).getName());
			String nature=parse.get(i).getNatureStr();
			if(wordIndex !=null&&nature.startsWith("a")){//
				String[] pair=new String[4];
				if(!noun.equals("")){
					pair[0]=noun;
				}else{
					pair[0]=findNextNoun(i,parse);
				}
				AdvPrefix adv_prefix=processAdv(parse,advDict,begin,i);
				double unitscore=adv_prefix.coefficient*dict.wordsPolar.get(wordIndex)*dict.strength.get(wordIndex);
				pair[1]=adv_prefix.advStr;
				pair[2]=parse.get(i).getName();
				pair[3]=Double.toString((float)unitscore);
				result.addPair(pair);
				System.out.println(pair[0]+"\t"+pair[1]+"\t"+pair[2]+"\t"+pair[3]);
				sentScore+=unitscore;
				
				begin=i;
			}
			
			if(!nature.equals("null")&&((nature.startsWith("n")||nature.startsWith("r")))){
				noun=parse.get(i).getName();
			}
		}
//		System.out.println(sentScore);
		result.setScore(sentScore);
		return result;
	}
	
	//加入对介词短语的排除
	private PairResult identifyPairJIECI(List<Term> parse, SentDict dict, AdverbDict advDict) {
		// TODO Auto-generated method stub
		PairResult result=new PairResult();
		String noun="";
		int beginForAdv=0;
		Term t = null;
		float sentScore=0;
		for(int i=0;i<parse.size();++i){
			Integer wordIndex=dict.words.get(parse.get(i).getName());
			t= parse.get(i);
			String nature=t.getNatureStr();
			if(wordIndex !=null&&nature.startsWith("a")){//
				String[] pair=new String[4];
				if(!noun.equals("")){
					pair[0]=noun;
				}else{
					pair[0]=findNextNounNoJIECI(i,parse);
				}
				AdvPrefix adv_prefix=processAdv(parse,advDict,beginForAdv,i);
				double unitscore=adv_prefix.coefficient*dict.wordsPolar.get(wordIndex)*dict.strength.get(wordIndex);
				pair[1]=adv_prefix.advStr;
				pair[2]=t.getName();
				pair[3]=Double.toString((float)unitscore);
				result.addPair(pair);
				System.out.println(pair[0]+"\t"+pair[1]+"\t"+pair[2]+"\t"+pair[3]);
				sentScore+=unitscore;
				
				beginForAdv=i+1;
			}
			
			if(!nature.equals("null")&&(nature.startsWith("n")||nature.startsWith("r")) ){
				System.out.println("要换的新名词索引位=="+i);
				System.out.println("要换的新名词（测试是否介词成分）=="+parse.get(i).getName());
				if (notOfJIECI(i, parse)){
					noun=parse.get(i).getName();
					System.out.println("要换的新名词找到了=="+noun);
				}else{
					System.out.println("要换的新名词是介词短语的成分，不做名词认定：");
				}
			}
		}
//		System.out.println(sentScore);
		result.setScore(sentScore);
		return result;
	}

	private AdvPrefix processAdv(List<Term> parse, AdverbDict advDict, int begin, int end) {
		// TODO Auto-generated method stub
		String word= null;
		String advStr="";
		double coefficient =1;
		for(int i=begin;i<end;i++){
//			System.out.println("现在介词查询的起始index是："+i);
			word = parse.get(i).getName();
//			System.out.println("要去介词典里查的词是：  "+word);
			Integer advIndex=advDict.advWord.get(word);
//			System.out.println("在介词词典里查到的index是--  "+advIndex);
			if(advIndex!=null){
//				System.out.println("当前找到的介词是=="+advDict.adv_vect.get(advIndex));
				advStr+="+"+advDict.adv_vect.get(advIndex);
//				System.out.println("当前advStr是=="+advStr);
				if(!advDict.adv_POS.get(advIndex).equals("DEGREE")){
					coefficient*=advDict.degree.get(advIndex);
				}else{
					coefficient=Math.pow(advDict.degree.get(advIndex),coefficient);
				} 
//				System.out.println(coefficient);
			}
		}
		if(!advStr.equals(""))	advStr=advStr.substring(1);
		AdvPrefix adv_pre=new AdvPrefix(advStr,coefficient);
		return adv_pre;
	}

	private String findNextNoun(int i, List<Term> parse) {
		// TODO Auto-generated method stub
		for(int j=i+1;j<parse.size();++j){
			if(parse.get(j).getNatureStr().startsWith("n")) return parse.get(j).getName();
		}
		return "";
	}
	
	private String findNextNounNoJIECI(int i, List<Term> parse) {
		// TODO Auto-generated method stub
		for(int j=i+1;j<parse.size();++j){
			if(parse.get(j).getNatureStr().startsWith("n")) {
				System.out.println("需判断是否为介词成分的找到的下一个名词是： "+parse.get(j).getName());
				if (notOfJIECI(j, parse)){
					System.out.println("判断不是介词");
					return parse.get(j).getName();}
			}
		}
		return "";
	}

	private boolean notOfJIECI(int j, List<Term> parse) {
		if (j==0){
			System.out.println("  前面没有词");
			return true;
		};
		for(int k=j-1;k>=j-3;k--){
			System.out.println("当前k是"+k);
			if (parse.get(k).getNatureStr().startsWith("p")){
				System.out.println("是介词的成分："+ parse.get(k).getName());
				return false;
			}
			if (k==0) {
				System.out.println("k到0了没找到前面有介词");
				return true;
			}
		};

		return true;
	}

	//
	public String preprocess(String article){
		article=article.replace(" ", "、");// space of English
		article=article.replace("　", "、");// space of Chinese
		article=article.replaceAll(" [a-zA-z]+://[^\\s]*", "");
		article=article.replaceAll(",|;", "，");
		return article;
	}
	
	public List<String> sentenceSegment(String sen){
		SentencesUtil su = new SentencesUtil();
		return su.toSentenceList(sen);
	}

	public List<Term> wordSegment(String sentence){
		return NlpAnalysis.parse(sentence);
	}
	
//	public static void main(String[] args) throws IOException{
//		PairExtract pe=new PairExtract();
//		pe.extractPair("今天天气真好！");
//	}

}
