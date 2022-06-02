package com.zhmenko.deeplay;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Node {
    private int val;
    private List<Node> neighbors;
    public Node() {
        this.val = 0;
        this.neighbors = new ArrayList<Node>();
    }
    public Node(int val) {
        this.val = val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int val, ArrayList<Node> neighbors) {
        this.val = val;
        this.neighbors = neighbors;
    }
}
