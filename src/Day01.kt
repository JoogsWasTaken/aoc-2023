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
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
