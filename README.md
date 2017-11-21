# Weak-RSA
Generates RSA keys with RANDU (a weak PRNG) and discovers common factors.

![screenshot](https://pbs.twimg.com/media/DPJ0RTwV4AUKo_b.jpg:large)

## Description
This pedalogical program generates a set of RSA key pairs (e, n). It intentionally uses a weak PRNG named RANDU. Once all the keys have been generated it iterates through the set and attempts to discover common factors among the moduli between each pair of key pairs.

The program "cheats" to make sure that the decryption exponent is correct.

## Operation
Execute ``java -jar .\Weak_RSA.jar`` at the command line. Optionally, specify the number of keys to generate. Each key is compared to each other key, so the program runs in O(n^2) time.
