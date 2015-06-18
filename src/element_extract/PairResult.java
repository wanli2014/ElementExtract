package element_extract;

import java.util.Vector;

public class PairResult {
	public Vector<String[]> pairsVct=new Vector<String[]>();
	public float score=0;
	
	public void addPair(String[] pair) {
		pairsVct.add(pair);
	}
	
	public void setScore(float sentScore) {
		score=sentScore;
	}
	
	public void addSubRst(PairResult subRst) {
		score+=subRst.score;
		for(String[] pair:subRst.pairsVct){
			pairsVct.add(pair);
		}
	}
}
