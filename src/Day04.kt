import kotlin.math.pow

data class ScratchCard(val winningNumbers: List<Int>, val ownNumbers: List<Int>)

fun main() {
    fun part1(input: List<String>): Int {
        val scratchCards = mutableListOf<ScratchCard>()

        for (line in input) {
            val trimmedLine = line.substring(line.indexOf(':') + 1)
            val (winningNumbersStr, ownNumbersStr) = trimmedLine.split('|')

            val winningNumbers = winningNumbersStr.trim().split("\\s+".toRegex()).map { s -> s.toInt() }.toList()
            val ownNumbers = ownNumbersStr.trim().split("\\s+".toRegex()).map { s -> s.toInt() }.toList()

            scratchCards.add(ScratchCard(winningNumbers, ownNumbers))
        }

        var totalScore = 0

        for (scratchCard in scratchCards) {
            var matches = 0

            for (winningNumber in scratchCard.winningNumbers) {
                matches += scratchCard.ownNumbers.filter { ownNumber -> ownNumber == winningNumber }.size
            }

            // skip if there are no matching numbers
            if (matches == 0) continue

            totalScore += 2.0.pow((matches - 1).toDouble()).toInt()
        }

        return totalScore
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    check(part1(readInput("Day04_test")) == 13)

    val input = readInput("Day04")

    println(part1(input))
    println(part2(input))
}