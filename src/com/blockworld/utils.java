package com.blockworld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

class utils {
     static List<List<Integer>> generateOriginal(int blocks,int stacks){
        List<List<Integer>> original=new ArrayList<>();
        for(int i=0;i<stacks;i++){
            original.add(new ArrayList<Integer>());
        }
        List<Integer> list=new ArrayList<>();
        for(int i=1;i<=blocks;i++){
            list.add(i);
        }
        Random random=new Random();
        for(int i=0;i<list.size();i++){
            int p=random.nextInt(i+1);
            int temp=list.get(i);
            list.set(i,list.get(p));
            list.set(p,temp);
        }
        for(int i=0;i<list.size();i++){
            int index=random.nextInt(stacks);
            original.get(index).add(list.get(i));
        }
        return original;
    }

     static List<List<Integer>> generateFinal(int blocks,int stacks){
        List<List<Integer>> destination=new ArrayList<>();
        for(int i=0;i<stacks;i++)
            destination.add(new ArrayList<>());

        for(int i=1;i<=blocks;i++)
            destination.get(0).add(i);
        return destination;
    }

     static List<Node> getNeighbor(Node root){
        List<Node> neighbor=new ArrayList<>();

        for(int i=0;i<root.state.size();i++){
            if(root.state.get(i).size()==0)
                continue;
            for(int j=0;j<root.state.size();j++){
                if(j==i) continue;
                List<List<Integer>> newState=copy(root.state);
                int len=newState.get(i).size();
                int val=newState.get(i).get(len-1);
                newState.get(i).remove(len-1);
                newState.get(j).add(val);

                Node node=new Node(newState,root,root.depth+1);
                neighbor.add(node);
            }
        }
        return neighbor;
    }

     static String toStr(List<List<Integer>> state){
        StringBuilder res=new StringBuilder();
        for(int i=0;i<state.size();i++){
            StringBuilder sb=new StringBuilder();
            sb.append(i+1).append("|");
            if(state.get(i).size()==0){
                res.append(sb.toString()).append("\n");
                continue;
            }

            for(int j=0;j<state.get(i).size()-1;j++){
                char c=(char)(state.get(i).get(j)-1+'A');
                sb.append(c).append(",");
                //sb.append(state.get(i).get(j)).append(",");
            }
            char cc=(char)(state.get(i).get(state.get(i).size()-1)-1+'A');
            sb.append(cc);
            //sb.append(state.get(i).get(state.get(i).size()-1));
            res.append(sb.toString()).append("\n");
        }
        return res.toString();
    }

     static List<List<Integer>> copy(List<List<Integer>> root){
        List<List<Integer>> res=new ArrayList<>();
        int len=root.size();
        for(int i=0;i<len;i++){
            res.add(new ArrayList<>());
            res.get(i).addAll(root.get(i));
        }
        return res;
    }

     static void record(int iter,int size,int f,int depth){
        System.out.println("iter="+iter+", "+"queue="+size+", "+"f=g+h="+f+", "+"depth="+depth);
    }
//    static int computeH(List<List<Integer>> state){
//         int count=0;
//         for(int i=0;i<state.size();i++){
//             for(int j=0;j<state.get(i).size();j++){
//                 int temp=state.get(i).get(j);
//                 if(i!=0&&j!=temp-1)
//                     count++;
//             }
//         }
//         return count;
//    }
     static int computeH(List<List<Integer>> state){
         int h=0;
         int numBlocks=0;
         for(int i=0;i<state.size();i++)
             numBlocks+=state.get(i).size();
         int FirstStack=state.get(0).size();
         HashMap<Integer,Integer> map=new HashMap<>();
         for(int i=0;i<state.size();i++){
             List<Integer> stack=state.get(i);
             for(int j=0;j<stack.size();j++){
                 int dis=stack.size()-j;//above current
                 if(i!=0)
                     dis+=FirstStack+1;//other stack
                 else
                     dis=-j;//first stack,distance < 0
                 map.put(stack.get(j),dis);
             }
         }

         for(int i=0;i<numBlocks;i++){
             int block=i+1;
             if(map.get(block)>0)//other stack
                 h+=Math.abs(i-map.get(block));//(stack-j)->above now + (firstSize-i)->above target + 1->insert
             else{//first stack
                 int index=-map.get(block);//i:target,index:now pos
                 if(i<index)//above target position
                     h+=index-i+1+1;//(index-i+1),clear all the element(inclusive) above,+1 add cur block
                 else if(i>index)//under target position
                     h+=FirstStack+i-2*index+1;//挪走:size-index(包括block在内),重新垫底:i-index,放回:1
                 else
                     h+=0;
             }
         }
         return h;
     }
}
