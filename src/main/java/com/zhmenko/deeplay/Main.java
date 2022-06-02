package com.zhmenko.deeplay;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        long bfr = System.currentTimeMillis();
        int ret = Solution.getResult("STWSWTPPTPTTPWPP","Human",new File("config.json"));
        long aft = System.currentTimeMillis();
        System.out.println(ret);
        System.out.println(aft - bfr + "ms");
    }
}
