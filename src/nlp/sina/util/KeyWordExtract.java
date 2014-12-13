package nlp.sina.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import nlp.sina.model.KeyWord;

import org.ansj.app.newWord.LearnTool;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;


/*
 * 抽取文本内容的关键词
 */
public class KeyWordExtract {
    private int nKeyword = 200;	//抽取关键词的个数
    
    public KeyWordExtract() {
    }
    public KeyWordExtract(int nKeyword) {
        this.nKeyword = nKeyword;
    }
    /*
     * 函数作用：从文本中抽取关键词列表
     */
    private List<KeyWord> computeArticleTfidf(String content, int titleLength) {
       
    	Map<String, KeyWord> tm = new HashMap<String, KeyWord>();
        LearnTool learn = new LearnTool();
        List<Term> parse = NlpAnalysis.parse(content, learn);
        parse = NlpAnalysis.parse(content, learn);
        for (Term term : parse) {
            int weight = getWeight(term, content.length(), titleLength);
            if (weight == 0)
                continue;
            KeyWord keyword = tm.get(term.getName());
            if (keyword == null) {
                keyword = new KeyWord(term.getName());
                keyword.setKeywordExtracScore(term.getNatrue().allFrequency, weight);
                tm.put(term.getName(), keyword);
            } else {
                keyword.score += 1 * keyword.idf;
            }
        }
        TreeSet<KeyWord> treeSet = new TreeSet<KeyWord>(tm.values());
        ArrayList<KeyWord> arrayList = new ArrayList<KeyWord>(treeSet);
        if (treeSet.size() < nKeyword) {
            return arrayList;
        } else {
            return arrayList.subList(0, nKeyword);
        }
    }
    
    /*
     * get keywords, just need content
     */
    public List<KeyWord> computeArticleTfidf(String content) {
        return computeArticleTfidf(content, 0);
    }
    /*
     * get keywords weight
     */
    private int getWeight(Term term, int length, int titleLength) {
        if (term.getName().matches("(?s)\\d.*")) {
            return 0;
        }
        if (term.getName().trim().length() < 2) {
            return 0;
        }
        String pos = term.getNatrue().natureStr;
        if (!pos.startsWith("n") || "num".equals(pos)) {
            return 0;
        }
        int weight = 0;
        if (titleLength > term.getOffe()) {
            return 20;
        }
        // position
        double position = (term.getOffe() + 0.0) / length;
        if (position < 0.05)
            return 10;
        weight += (5 - 5 * position);
        return weight;
    }
    
    public static void main(String[] args) throws IOException {
	   
	}
	
	
}
