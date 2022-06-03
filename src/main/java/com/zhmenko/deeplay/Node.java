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
    public Node(int val) {
        this.val = val;
        neighbors = new ArrayList<>();
    }
}
