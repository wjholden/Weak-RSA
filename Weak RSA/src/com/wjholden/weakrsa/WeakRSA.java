package com.wjholden.weakrsa;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class WeakRSA {
    static int collisions = 0;
    
    public static void main (String args[]) {
        RANDU prng = new RANDU();
        List<Key> keys = new ArrayList<>();
        
        int COUNT = 1000;
        try {
            if (args.length > 0)
                COUNT = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
        }
        
        for (int i = 0; i < COUNT; i++) {
            keys.add(new Key(prng));
        }
        
        System.out.println("Using a pool of " + COUNT + " RSA keys generated with a weak PRNG (RANDU), we find collisions:");
        
        keys.forEach(k1 -> {
            keys.forEach(k2 -> {
                if (k1 != k2) {
                    BigInteger gcd = k1.n.gcd(k2.n);
                    if (!gcd.equals(BigInteger.ONE)) {
                        BigInteger pq1 = k1.n.divide(gcd);
                        BigInteger pq2 = k2.n.divide(gcd);
                        BigInteger d1 = k1.e.modInverse(gcd.subtract(BigInteger.ONE).multiply(pq1.subtract(BigInteger.ONE)));
                        BigInteger d2 = k2.e.modInverse(gcd.subtract(BigInteger.ONE).multiply(pq2.subtract(BigInteger.ONE)));
                        if (d1.equals(k1.d)) {
                            collisions++;
                            System.out.println(collisions + ") " + k1);
                        }
                    }
                }
            });
        });
        
        if (collisions == 0) {
            System.out.println("No collisions found. Try again!");
        }
    }
    
}

class Key {
    BigInteger p, q, e, n, d;
    
    Key(Supplier<Long> prng) {
        e = BigInteger.valueOf(0x10001);
        do {
            p = BigInteger.valueOf(prng.get()).nextProbablePrime();
            q = BigInteger.valueOf(prng.get()).nextProbablePrime();
            n = p.multiply(q);
        } while (p.equals(q) || // p should not equal q and their toitient (p-1)*(q-1) must be coprime to e
                !e.gcd(p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE))).equals(BigInteger.ONE));
        d = e.modInverse(p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
    }

    @Override
    public String toString() {
        return "Key{" + "p=" + p + ", q=" + q + ", e=" + e + ", n=" + n + ", d=" + d + '}';
    }    
}

// https://en.wikipedia.org/wiki/RANDU
// Wikipedia says it's bad. It really is bad.
class RANDU implements Supplier<Long> {

    private static long v;
    private static final long MODULUS = ((long)1 << 31);
    
    static {
        v = System.currentTimeMillis();
        if (RANDU.v % 2 == 0) {
            RANDU.v++;
        }
    }
    
    @Override
    public Long get() {
        RANDU.v = (65539 * RANDU.v) % RANDU.MODULUS;
        return RANDU.v;
    }
    
}