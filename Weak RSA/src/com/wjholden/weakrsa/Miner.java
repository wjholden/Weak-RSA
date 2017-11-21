package com.wjholden.weakrsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Miner {

    public static void main(String[] args) {
        final BigInteger e = BigInteger.valueOf(65537);
        
        final List<BigInteger> n = new ArrayList<>();
        n.add(new BigInteger("7178ff388c2862e1764705fab6e6744ff42ede0c71791c255c065e2689970238e1e919929dde6d9114d4f7635f8b0bfa9b171f3e53cb6b0d1f367072cbc1acf129c59ca9d508d03d6c23e80026d1a9b99de4c9ab6bc446c0dbd2eee486e6477741526bf38f28ae52dc805b68ad4ebc3489656c4000bf7c2496a30844507efe19", 16));
        n.add(new BigInteger("64b29780395f52b36655ca4c68efc130d6c999f30ca59fc15fb28276e80073df6539d03c6d459caa116e94b56d4f2f5123a89b753c67c34e76c5e25409938360b09c7782db74a734b36e8ed4388b7ec812f665e9d7c042ee030c6160aebdb3e5fd09047069be7df45ba4d6a2e1e2124945ed163b12bf2113b620ef4a89eddd0b", 16));
        n.add(new BigInteger("630f329a7a49b55eabf152d764e7ee290937fd772fdd22a35b621bfdb52483b336f813dea9212487abab9e00eb87b18c9f3c140ef19464409f064e71db4229529f9f44350c59fdc7f6bc14659a942d0503c566a34ee89f83624ea3911ce77f01de5a9f261504fe10d97a356d6eadc25dd343993cfd1c0b45c47e891ea86542bf", 16));
        n.add(new BigInteger("6456c49d35d2c7726d477cf596f806054ffd635e818cd3458bf99e7530554cad799cf62d91d95c3691e9e8f9f7b08da05fc7567e417c80755fdb859020932feaae07dfac84535c6ab7c9f47c022a8df12dafd7b5aa2bbac6f674a83998e07711674a3602f9c0134607f14904e492181de82022b6b8de147134506ae2b653188f", 16));
        n.forEach(n1 -> {
            n.forEach(n2 -> {
                if (!n1.equals(n2)) {
                    BigInteger p = n1.gcd(n2);
                    
                    if (!p.equals(BigInteger.ONE)) {
                        System.out.println("e = " + e.toString(16));
                        System.out.println("n = pq = " + n1.toString(16));
                        System.out.println("p = " + p.toString(16));
                        BigInteger q = n1.divide(p);
                        System.out.println("q = " + q.toString(16));
                        BigInteger d = e.modInverse(p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
                        System.out.println("d = " + d.toString(16));
                    }
                }
                System.out.println();
            });
        });
    }
}
