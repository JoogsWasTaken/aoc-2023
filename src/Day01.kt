import kotlin.collections.mapOf as mapOf

fun main() {
    fun part1(input: List<String>): Int {
        val digits = "0123456789"
        var total = 0

        for (line in input) {
            val chars = line.toCharArray()

            // search forwards
            for (chr in chars) {
                if (digits.contains(chr)) {
                    total += chr.toString().toInt() * 10;
                    break;
                }
            }

            // search backwards
            for (i in chars.indices.reversed()) {
                val chr = chars[i];

                if (digits.contains(chr)) {
                    total += chr.toString().toInt();
                    break;
                }
            }
        }

        return total
    }

    fun part2(input: List<String>): Int {
        val wordToNumber = mapOf(
            "one" to 1,
            "1" to 1,
            "two" to 2,
            "2" to 2,
            "three" to 3,
            "3" to 3,
            "four" to 4,
            "4" to 4,
            "five" to 5,
            "5" to 5,
            "six" to 6,
            "6" to 6,
            "seven" to 7,
            "7" to 7,
            "eight" to 8,
            "8" to 8,
            "nine" to 9,
            "9" to 9
        )

        var total = 0

        for (line in input) {
            var lowestIdx = line.length
            var highestIdx = -1

            var lowValue = 0
            var highValue = 0

            wordToNumber.forEach { (key, value) ->
                val i = line.indexOf(key)

                if (i == -1) {
                    return@forEach
                }

                if (i < lowestIdx) {
                    lowestIdx = i
                    lowValue = value
                }

                val j = line.lastIndexOf(key)

                if (j > highestIdx) {
                    highestIdx = j
                    highValue = value
                }
            }

            total += lowValue * 10 + highValue
        }

        return total
    }

    check(part1(readInput("Day01_part1_test")) == 142)
    check(part2(readInput("Day01_part2_test")) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
