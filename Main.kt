package chucknorris

fun encryption() {
    println("Input string:")
    var last = ' '
    var str = ""
    // Changing every character of input string to 7 binary bits
    readln().forEach { str += String.format("%07d", it.code.toString(radix = 2).toInt()) }

    // Encoding string by changing bits 1 for 0 0 and 0 for 00 0, if there is multiple of them next to each other
    // we add one 0 to the sequence
    println("Encoded string")
    str.forEach {
        if (it != last) {
            last = it
            print(if (last == '1') " 0 0" else " 00 0")
        } else {
            print("0")
        }
    }
    println()
}

fun decryption() {
    println("Input encoded string:")
    val str = readln().split(" ").filter { it != "" }
    var binaryStr = ""

    // Checking if there is any symbol other than 0.
    if (str.any { !it.matches("""0+""".toRegex()) }) return println("Encoded string is not valid.")

    for (i in str.indices step 2) {
        // Checking if block starts with 0 or 00.
        if (str[i].length > 2) return println("Encoded string is not valid.")
        try {
            // Changing to binary strings.
            binaryStr += if (str[i] == "0") "1".repeat(str[i + 1].length) else "0".repeat(str[i + 1].length)
        } catch (e: IndexOutOfBoundsException) {
            return println("Encoded string is not valid.")
        }
    }
    // binaryStr length must be multiple of 7 bits
    if (binaryStr.length % 7 != 0) return println("Encoded string is not valid.")

    // Decoding string by parting it by 7 bits, changing it to characters and joining into a string.
    println("Decoded string:")
    println(binaryStr.chunked(7).map { it.toInt(2).toChar() }.joinToString(""))
}

fun main() {
    while (true) {
        println("Please input operation (encode/decode/exit):")
        when (val action = readln()) {
            "encode" -> encryption()
            "decode" -> decryption()
            "exit" -> break
            else -> println("There is no '$action' operation")
        }
    }
    println("Bye!")
}