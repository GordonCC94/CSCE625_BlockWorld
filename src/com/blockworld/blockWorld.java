package com.blockworld;

import java.util.*;


public class blockWorld {
    public static int blocks,stacks;
    public static void main(String args[]){
        Scanner sc=new Scanner(System.in);
        System.out.println("Please input the number of blocks");
        blocks=sc.nextInt();
        System.out.println("Please input the number of stacks");
        stacks=sc.nextInt();


        /* Initialize the original state and final state of blocks world */
            List<List<Integer>> original = utils.generateOriginal(blocks, stacks);
            List<List<Integer>> destination = utils.generateFinal(blocks, stacks);
            Node origin = new Node(original, null, 0);

            System.out.println("Initial State is:");
            System.out.println(origin);


        /* A* Search() */
            int maxSize = 0, maxDepth = 0, iter = 0, depth = 0, queue = 0;
            HashMap<String, Node> map = new HashMap<>();
            PriorityQueue<Node> pq = new PriorityQueue<>();
            HashSet<String> set = new HashSet<>();
            pq.add(origin);
            set.add(origin.toString());
            map.put(origin.toString(), origin);
            Node temp;

            while (true) {
                temp = pq.poll();
                //iter++;
                utils.record(iter++, pq.size(), temp.f, temp.depth);
                maxDepth = Math.max(maxDepth, temp.depth);
                maxSize = Math.max(maxSize, pq.size());
                if (temp.toString().equals(utils.toStr(destination))) {
                    break;
                }
                List<Node> neighbors = utils.getNeighbor(temp);
                for (Node neighbor : neighbors) {
                    if (!set.contains(neighbor.toString())) {
                        pq.add(neighbor);
                        set.add(neighbor.toString());
                        map.put(neighbor.toString(), neighbor);
                    } else {
                        Node node = map.get(neighbor.toString());
                        if (node.depth < neighbor.depth) {
                            pq.remove(node);
                            pq.add(neighbor);
                            map.put(neighbor.toString(), neighbor);
                        }
                    }

                }
            }

        /*Output the path*/
            List<Node> path = new ArrayList<>();
            Node node = temp;
            while (node != null) {
                path.add(node);
                node = node.parent;
            }
            System.out.print("Success!" + "Depth=" + maxDepth + "," + "total_goal_tests=" + iter + "," + "max_queue_size=" + maxSize + "\n");

        for(int i=0;i<path.size();i++)
            System.out.println(path.get(path.size()-1-i));

            System.out.println(path.size());

    }
}
