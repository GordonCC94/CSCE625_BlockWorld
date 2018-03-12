package com.blockworld;

import java.util.ArrayList;
import java.util.List;

class Node implements Comparable<Node>{
    public List<List<Integer>> state=new ArrayList<>();
    Node parent;
    int depth;
    int f;
    public Node(List<List<Integer>> state,Node parent,int depth){
        this.state=new ArrayList<>(state);
        this.depth=depth;
        this.parent=parent;
        this.f=depth+utils.computeH(state);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node new_node= (Node) obj;
            if(new_node.toString().equals(this.toString()))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
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

    @Override
    public int compareTo(Node o) {
        return this.f-o.f;
    }
}



