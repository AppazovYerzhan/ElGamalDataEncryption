import java.io.File
import java.math.BigInteger
import java.util.*

fun main() {
    val primes = readPrimesFromFile("out/production/ElGamalDataEncryption/primes.txt")
    val random = Random()
    var p = BigInteger("0")
    var q = BigInteger("0")
    while (p <= q) {
        p = primes[random.nextInt(primes.size)]
        q = primes[random.nextInt(primes.size)]
    }
    println("P: $p")
    println("Q: $q")

    val x = BigInteger(random.nextInt(p.toInt()).toString())
    println("Secret key (x): $x")

    val y = q.modPow(x, p)
    println("Public key (y): $y")

    val M = BigInteger("5")
    println("Message: $M")


    var k = findK(p.toInt())
    println("Random number k: $k")

    val a = q.modPow(BigInteger(k.toString()), p)
    val b = (y.pow(k) * M).mod(p)
    println(
        "a: $a\n" +
                "b: $b"
    )

    val C = Pair(a.toInt(), b.toInt())
    println("Encrypted message: $C")

    val M2 = (b * a.pow(p.toInt()-1-x.toInt())).mod(p)
    println("Decrypted message: $M2")

}

fun readPrimesFromFile(fileName: String): List<BigInteger> {
    val primes = mutableListOf<BigInteger>()
    File(fileName).forEachLine {
        primes.add(BigInteger(it.trim()))
    }
    return primes
}

fun gcd(a: Int, b: Int): Int {
    var x = a
    var y = b
    while (y != 0) {
        val t = y
        y = x % y
        x = t
    }
    return x
}

fun findK(p: Int): Int {
    for (k in p-1 downTo 1) {
        if (gcd(k, p - 1) == 1) {
            return k
        }
    }
    throw Exception("No k found that satisfies the condition")
}