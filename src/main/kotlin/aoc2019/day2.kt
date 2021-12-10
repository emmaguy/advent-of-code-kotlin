package aoc2019

val staticInput = arrayOf(1, 0, 0, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 10, 1, 19, 2, 19, 6, 23, 2, 13, 23, 27, 1, 9, 27, 31, 2, 31, 9, 35, 1, 6, 35, 39, 2, 10, 39, 43, 1, 5, 43, 47, 1, 5, 47, 51, 2, 51, 6, 55, 2, 10, 55, 59, 1, 59, 9, 63, 2, 13, 63, 67, 1, 10, 67, 71, 1, 71, 5, 75, 1, 75, 6, 79, 1, 10, 79, 83, 1, 5, 83, 87, 1, 5, 87, 91, 2, 91, 6, 95, 2, 6, 95, 99, 2, 10, 99, 103, 1, 103, 5, 107, 1, 2, 107, 111, 1, 6, 111, 0, 99, 2, 14, 0, 0)

fun main() {
    // Find the noun and verb that result in 19690720
    for (noun in 0..99) {
        for (verb in 0..99) {
            val memory = staticInput.clone()
            memory[1] = noun
            memory[2] = verb
            val value = processIntcode(memory)
            if (value == 19690720) {
                print("Output: $value from noun: $noun, verb: $verb. Solution: ${100 * noun + verb}\n")
                return
            }
        }
    }
}

fun processIntcode(input: Array<Int>): Int {
    var index = 0
    while (true) {
        when (input[index]) {
            1 -> processOpcode1(input, index + 1, index + 2, index + 3)
            2 -> processOpcode2(input, index + 1, index + 2, index + 3)
            99 -> return input[0]
        }
        index += 4
    }
}

fun processOpcode1(input: Array<Int>, index1: Int, index2: Int, index3: Int) {
    input[input[index3]] = input[input[index1]] + input[input[index2]]
}

fun processOpcode2(input: Array<Int>, index1: Int, index2: Int, index3: Int) {
    input[input[index3]] = input[input[index1]] * input[input[index2]]
}
