football
========
根据词典，对字符串进行足球类的处理。主要依赖于词典的整理

使用方法
-------
	TrieTree tree = new TrieTree();   
	tree.init();
	
	System.out.println(tree.find("AC米")); //output:null  
	System.out.println(tree.find("AC米兰"));//output:意甲,球队_,足球_   
	System.out.println(tree.find("巴萨"));//output:西甲,欧冠,球队_,足球_
	
	System.out.println(tree.segWords("2014.03.26 德甲第27轮 柏林赫塔vs拜仁慕尼黑 博斯 HD 720P 国语", "|"));   //output:柏林赫塔/德甲,球队_,足球_|vs/s_vs|拜仁/德甲,欧冠,球队_,足球_
	
	System.out.println(tree.segTeamVS("西甲官方集锦-贝尔传射本泽马2球 皇马4-2暂超巴萨3分登顶"));   //output:皇马4-2巴萨
			
	System.out.println(tree.segTeamVS("巴萨梦魇！C罗加盟皇马至今VS巴萨13粒进球全程回顾"));    //output:皇马VS巴萨
	System.out.println(tree.segTeamVS("埃弗顿3-0纽卡斯尔、利物浦2-1桑德兰"));   //output:埃弗顿3-0纽卡斯尔 利物浦2-1桑德兰
