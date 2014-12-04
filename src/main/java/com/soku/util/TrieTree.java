/**
 *2014-3-26 上午10:04:53
 */
package com.soku.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;




/**
 * 字典树的Java实现。实现了插入、查询以及深度优先遍历. Trie tree's java
 * implementation.(Insert,Search,DFS)
 */
public class TrieTree {
	private TrieNode root;
	private int count;

	public TrieTree() {
		root = new TrieNode("");
	}

	private class TrieNode {
		private Map<Character, TrieNode> son;
		private boolean isEnd;// end of a string.
		private String val;// No this field mostly.

		TrieNode(String val) {
			this.son = new HashMap<Character, TrieTree.TrieNode>();
			this.isEnd = false;
			this.val= val;
		}
	}

	/**
	 * 初始化时，插入word
	 * @param str
	 */
	private void insert(String str,String type) {
		if (str == null || str.length() == 0) {
			return;
		}
		TrieNode node = root;
		str = str.toLowerCase();
		char[] letters = str.toCharArray();//A C 米 兰
		for (int i = 0, len = str.length(); i < len; i++) {
			char c = letters[i];
			if (!node.son.containsKey(c)) {
				TrieNode son = new TrieNode("");
				node.son.put(c, son);
			}
			
			node = node.son.get(c);
		}
		node.isEnd = true;
		node.val = type;
	}


	/**
	 * 查找一个字符串的描述。没有返回null 
	 */
	public String find(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		TrieNode node = root;
		str = str.toLowerCase();
		char[] letters = str.toCharArray();
		for (int i = 0, len = str.length(); i < len; i++) {
			char c = letters[i];
			if (node.son.containsKey(c)) {
				node = node.son.get(c);
			} else {
				return null;
			}
		}
		
		return node.isEnd?node.val:null;
	}
	
	/**
	 * 分词，返回字符串
	 * @param str
	 * @param wordSpilt
	 * @return
	 * @throws IOException
	 */
	public String segWords(String str, String wordSpilt) {
		if (str == null || str.length() == 0) {
			return null;
		}
		TrieNode node = root;
		String old = str;
		str = str.toLowerCase();
		char[] letters = str.toCharArray();
		StringBuilder sbBuilder = new StringBuilder();
		int start =0;
	    int continueChar = 0;
		for (int i = 0, len = str.length(); i < len; i++) {
			char c = letters[i];
			if (node.son.containsKey(c)) {
				continueChar++;
				if(node.equals(root)){
					start = i;
				}
				node = node.son.get(c);
				if(node.isEnd){
					sbBuilder.append(old.substring(start, i+1)).append("/").append(node.val).append(wordSpilt);
					start=i+1;
					node=root;
					continueChar=0;
				}
			} else {
				if(continueChar>0){//如果连续包含，然后出现了不包含而且不是叶子。指针后退
					i=i-continueChar;
					continueChar=0;
				}
				node=root;
			}
		}
		
		String tmp = sbBuilder.toString();
		if(tmp.endsWith(wordSpilt)){
			return tmp.substring(0, tmp.length()-1);
		}
		
		return tmp;
	}
	
	/**
	 * 提取双方球队的比分或者vs
	 * @param str 输入字符串，比如视频标题、用户搜索串
	 * @return
	 */
	public String segTeamVS(String str) {
		String wordSpilt = ";";
		String wordsInfo = segWords(str, wordSpilt);
		if(wordsInfo==null){
			return null;
		}
		String [] wordInfos  = wordsInfo.split(wordSpilt);
		StringBuilder sBuilder = new StringBuilder();
		if(wordInfos.length>0){
			//TODO 是否过滤相关的类型
			for (int i = 0,len=wordInfos.length; i < len; i++) {
				String wordInfo = wordInfos[i];
				if(wordInfo.endsWith("_vs") && i>0 && i<len-1){
					if(sBuilder.length()>0){
						sBuilder.append(" ");
					}
					sBuilder.append(wordInfos[i-1].split("/")[0]);
					sBuilder.append(wordInfo.split("/")[0]);
					sBuilder.append(wordInfos[i+1].split("/")[0]);
				}
			}
		}
		return sBuilder.toString();
	}

	public int getCount() {
		return count;
	}

	public static void main(String[] args) throws IOException {
		//TODO 计算有使用多少内存
		TrieTree tree = new TrieTree();
//		String[] strs = { "AC米兰", "band", "bee", "absolute", "acm", };
//		for (String str : strs) {
//			tree.insert(str,"s_y_t");
//		}
		tree.init();
		System.out.println(tree.find("AC米"));
		System.out.println(tree.find("AC米兰"));
		System.out.println(tree.find("巴洛特利"));
		System.out.println(tree.find("巴萨"));
		
		System.out.println(tree.segWords("2014.03.26 德甲第27轮 柏林赫塔vs拜仁慕尼黑 博斯 HD 720P 国语", "|"));
		System.out.println(tree.segWords("U18桑德兰1-3利物浦 全场精华", "|"));
		System.out.println(tree.segWords("我是切尔西，爱国际米兰", "|"));
		String aaa = tree.segWords("射箭世界杯上海站：中国队夺得女子反曲弓团体冠军[新闻夜线]", ";");
		System.out.println(aaa);
		
		System.out.println(tree.segWords("[www.jzwzx.com]4月13日 英超第34轮 斯旺西VS切尔西 国语 下半场", "|"));
		
		System.out.println(tree.segWords("巴洛特利世界波 AC米兰1-0博洛尼亚RAI集锦", "|"));
		
		 
		
		System.out.println(tree.segTeamVS("巴洛特利世界波 AC米兰1-0博洛尼亚RAI集锦"));
		
		System.out.println(tree.segTeamVS("巴洛特利世界波 ac米兰1-0博洛尼亚RAI集锦"));
		System.out.println(tree.segTeamVS("梅西帽子戏法 皇马3-4巴萨遭双杀集锦"));
		
		System.out.println(tree.segTeamVS("【哇哈体育】2014.03.24 西甲第29轮 巴萨vs皇马 FOXHD 720P 国语"));
		
		System.out.println(tree.segTeamVS("西甲官方集锦-贝尔传射本泽马2球 皇马4-2暂超巴萨3分登顶"));
		
		System.out.println(tree.segTeamVS("巴萨梦魇！C罗加盟皇马至今VS巴萨13粒进球全程回顾"));
		System.out.println(tree.segTeamVS("埃弗顿3-0纽卡斯尔、利物浦2-1桑德兰"));
		
		
	}
	
	/**
	 * 读取jar包中的字典文件
	 * @throws IOException
	 */
	public void init() throws IOException {
		InputStream inputStream = null;
		inputStream = TrieTree.class.getClassLoader().getResourceAsStream("data"+File.separator+"wordssports.dic");
		if(inputStream!=null){
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));  
	        String resTemp = "";  
	        while((resTemp = br.readLine()) != null){  
	        	if(resTemp.startsWith("#")){
					continue;
				}
				String [] infos = resTemp.split(" ");
				if(infos.length==2){
					String [] names = infos[0].split("/");
					for (String name : names) {
						insert(name, infos[1]);
						count++;
					}
				}
	        }  
		}
		inputStream.close();
	}
}
