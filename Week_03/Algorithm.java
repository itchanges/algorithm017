
//组合
public List<List<Integer>> combine(int n, int k){
        List<List<Integer>> res = new ArrayList();
        if(k<=0 || n<k){
            return res;
        }
        Deque<Integer> paths = new ArrayDeque<Integer>();
        dfs(n,k,1,paths,res);
        return res;
    }
    public void dfs(int n, int k, int begin,Deque<Integer> paths, List<List<Integer>> res){
        // 递归终止条件：path.size = k
        if(paths.size() == k ){
            res.add(new ArrayList(paths));
            return;
        }
        for(int i=begin;i<=n;i++){
            paths.addLast(i);
            //因为是组合，每下探一层i+1，避免有重复元素
            dfs(n,k,i+1,paths,res);
            //递归恢复后，重置到递归前的状态，继续for循环
            paths.removeLast();
        }

    }




//全排列
 public List<List<Integer>> permute(int[] nums) {
         //res初始化
         List<List<Integer>> res = new ArrayList();
         //校验
         if(nums.length == 0){
             return res;
         }
         //path链路中已使用标记下，这个used也是比较巧妙的
         boolean[] used = new boolean[nums.length];
         //paths初始化
         Deque<Integer> paths = new ArrayDeque();
         dfs(nums,used,0,paths,res);
        //dfs遍历
        return res;         
    }

    public void dfs(int[] nums, boolean[] used, int deepth, Deque<Integer> paths, List<List<Integer>> res){
        //退出条件
        if( deepth == nums.length){
            //这里注意，新建一个list，因为paths会被多个地方引用到，会被修改
            res.add(new ArrayList(paths));
            return;
        }
        //处理当前层
        for(int i=0;i<nums.length; i++){
            if( ! used[i]){
                used[i] = true;
                paths.addLast(nums[i]);
                //下钻
                dfs(nums,used,deepth+1,paths,res);
                //重置状态
                used[i] = false;
                paths.removeLast();
            }
        }
    }

