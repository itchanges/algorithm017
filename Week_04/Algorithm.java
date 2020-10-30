//柠檬水找零
public boolean lemonadeChange(int[] bills) {
         int five = 0;
         int ten = 0;

         for(int bill : bills){
            if(bill == 5){
                five++;
            }else if(bill == 10){
                if(five > 0){
                    five--;
                    ten++;
                }else{
                    return false;
                }
            }else if(bill == 20){
                if(five > 0 && ten > 0 ){
                    five--;
                    ten--;
                }else if(five >= 3){
                    five = five - 3;
                }else{
                    return false;
                }
            }
         }   
         return true;
    }



//单词接龙1 
class Solution {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        if(wordList == null || wordList.size()==0){
            return 0;
        }
        //广度优先遍历，找最短路径
        //组织一个hashmap，key是带通配符的单词，示例：*og, d*g, do*
        //然后使用队列层次遍历
        Map<String,List<String>> patternMap = new HashMap();
        //记录访问过的节点，避免重复
        Set<String> visitedWord = new HashSet();
        //所有单词的长度相同
        int wordLength = wordList.get(0).length();
        for(String word : wordList){
            for(int i=0;i<wordLength;i++){
                //index(0,0) 或者index(word.length(),word.length()) 返回空
                String wordPattern = word.substring(0,i) + "*" + word.substring(i+1,word.length());
                List<String> matchPatternWords = patternMap.getOrDefault(wordPattern, new ArrayList());
                matchPatternWords.add(word);
                patternMap.put(wordPattern,matchPatternWords);
            }
        }

        Queue<WordObj> queue = new LinkedList();
        //最短转换序列，是包含起始节点的，所以从1开始
        queue.add( new WordObj(beginWord,1));
        visitedWord.add(beginWord);
        while( ! queue.isEmpty()){
           WordObj wordObj1 = queue.remove();
           int level = wordObj1.getLevel();
           String word1 = wordObj1.getWord();

           for(int i=0;i<wordLength;i++){
                String wordPattern = word1.substring(0,i) + "*" + word1.substring(i+1,wordLength);
                List<String> matchPatternWords = patternMap.get(wordPattern);
                if(matchPatternWords == null || matchPatternWords.size() == 0 ){
                    continue;
                }
                for(String matchPatternWord  : matchPatternWords  ){
                    if(matchPatternWord.equals(endWord)){
                       return level+1;
                    }        
                    if( ! visitedWord.contains(matchPatternWord)){
                        visitedWord.add(matchPatternWord);
                        queue.add(new WordObj(matchPatternWord, level+1));
                    }
                }
           }
        }
        return 0;
    }
    class WordObj {
        private String word;
        private int level;

        public WordObj(String word,int level){
            this.word = word;
            this.level = level;
        }
        public String getWord(){
            return word;
        }
        public int getLevel(){
            return level;
        }       
    }
}



//单词接龙2
class Solution {
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        //校验wordList是否包含endWord
        if( ! wordList.contains(endWord)){
            return new ArrayList();
        }
        //记录结果
        List<List<String>> res = new ArrayList();
        //return bfs(beginWord,endWord,wordList);
        //bfs
        //因为要记录路径，所以队列元素类型是List<String>
        Queue<List<String>> q = new LinkedList();
        //创建访问记录，避免重复访问（出现重复访问，路径肯定不是最短的，这里要最短路径）
        Set<String> visited = new HashSet();
        //创建set，获取下层节点(getNeighbours)时，判断是否存在于wordList
        Set<String> dict = new HashSet();
        dict.addAll(wordList);
        //当前层是否存在最短路径，如果存在，当前层遍历完后就结束while循环  (所有最短路径都在同一层)
        boolean isFound = false;
        //添加首个元素
        List<String> firstPath = new ArrayList();
        firstPath.add(beginWord);
        q.add(firstPath);
        visited.add(beginWord);
        while( ! q.isEmpty()){
            //每层首个元素开始，计算size，然后for循环遍历当前层的元素
            int size = q.size();
            //当前层处理后，再统一放到visited集合
            Set<String> subVisited = new HashSet();
            for(int i=0; i< size; i++){
                List<String> path1 = q.remove();
                //获取末尾的元素
                String node1 = path1.get(path1.size()-1);
                List<String> neighbours = getNeighbours(node1,dict);
                for(String neighbour : neighbours){
                    //在所有的最短路径中，一个元素不可能同时出现在不同层
                    if( ! visited.contains(neighbour)){
                        subVisited.add(neighbour);
                        //匹配到结果
                        if(neighbour.equals(endWord)){
                            isFound = true;
                            path1.add(neighbour);
                            //拷贝元素，避免引用修改等问题
                            res.add(new ArrayList(path1));
                        }else{
                            //在list中添加元素，等path1塞到队列后，再把末尾元素移除; 
                            path1.add(neighbour);
                        }
                        q.add(new ArrayList(path1));
                        //恢复状态，继续下一个neighbour处理
                        path1.remove(path1.size()-1);
                    }
                }
            }
            //当前层遍历完后，subVisited放到visited
            visited.addAll(subVisited);
            if(isFound){
                break;
            }
        }
        return res;
    }

    /**
     * 获取所有相邻元素（字母相差一位的元素）
     */
    private List<String> getNeighbours(String word, Set<String> dict ){
        List<String> res = new ArrayList();
        for(char i='a';i<='z';i++){
            //word转char数组
            char[] chs = word.toCharArray() ;
            for(int j=0;j<chs.length;j++){
                //每次修改一个字符
                //如果当前字符跟外层循环的值一致(就是自己，不是相邻元素)，continue
                if(chs[j]==i){
                    continue;
                }
                char old = chs[j];
                chs[j] = i;

                if(dict.contains(new String(chs))){
                    res.add(new String(chs));
                }
                //处理完后，恢复状态，将字符改回去,方便下一次遍历
                chs[j] = old;
            }
        }
        return res;
    }
}
