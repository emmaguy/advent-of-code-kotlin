package `2019`

fun main() {
    val size = findValidValues(min = 246540, max = 787419)
    print("count: $size\n\n")
}

fun findValidValues(min: Int, max: Int): Int {
    val validNumbers = mutableListOf<Int>()
    for (x in min..max) {
        if (isValid(x)) {
            validNumbers.add(x)
        }
    }
    return validNumbers.size
}

fun isValid(number: Int): Boolean {
    var currentNumber = number
    val digitFrequencyMap = mutableMapOf<Int, Int>()

    // Iterate through all the digits
    while (true) {
        val currentDigit = currentNumber % 10
        val nextDigit = (currentNumber / 10) % 10

        if (!digitFrequencyMap.contains(currentDigit)) {
            digitFrequencyMap[currentDigit] = 1
        } else {
            digitFrequencyMap[currentDigit] = digitFrequencyMap[currentDigit]!! + 1
        }

        // From left to right, the digits never decrease
        // They only ever increase or stay the same (like 111123 or 135679)
        if (nextDigit > currentDigit) {
            return false
        }

        currentNumber /= 10
        if (currentNumber <= 0) {
            break
        }
    }
    return digitFrequencyMap.values.filter { it == 2 }.count() >= 1
}
