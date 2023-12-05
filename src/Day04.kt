import kotlin.math.min
import kotlin.math.pow

data class ScratchCard(val winningNumbers: List<Int>, val ownNumbers: List<Int>)

fun main() {
    fun parseInput(input: List<String>): List<ScratchCard> {
        val scratchCards = mutableListOf<ScratchCard>()

        for (line in input) {
            val trimmedLine = line.substring(line.indexOf(':') + 1)
            val (winningNumbersStr, ownNumbersStr) = trimmedLine.split('|')

            val winningNumbers = winningNumbersStr.trim().split("\\s+".toRegex()).map { s -> s.toInt() }.toList()
            val ownNumbers = ownNumbersStr.trim().split("\\s+".toRegex()).map { s -> s.toInt() }.toList()

            scratchCards.add(ScratchCard(winningNumbers, ownNumbers))
        }

        return scratchCards
    }

    fun countMatches(scratchCard: ScratchCard): Int {
        var matches = 0

        for (winningNumber in scratchCard.winningNumbers) {
            matches += scratchCard.ownNumbers.filter { ownNumber -> ownNumber == winningNumber }.size
        }

        return matches
    }

    fun part1(input: List<String>): Int {
        var totalScore = 0

        for (scratchCard in parseInput(input)) {
            val matches = countMatches(scratchCard)

            // skip if there are no matching numbers
            if (matches == 0) continue

            totalScore += 2.0.pow((matches - 1).toDouble()).toInt()
        }

        return totalScore
    }

    fun part2(input: List<String>): Int {
        var totalScratchCardCount = 0
        val scratchCards = parseInput(input)

        scratchCards.forEachIndexed { i, _ ->
            val curIndices = mutableListOf(i)
            val nextIndices = mutableListOf<Int>()

            do {
                // update scratch card count
                totalScratchCardCount += curIndices.size
                // clear list of next indices for this pass
                nextIndices.clear()

                // get all scratch cards for the current pass
                curIndices.forEach { j ->
                    // count the matches for the current scratch card
                    val curScratchCard = scratchCards[j]
                    val matches = countMatches(curScratchCard)

                    // if no matches, simply abort here
                    if (matches == 0) return@forEach

                    // start of range
                    val nextIdxLow = j + 1
                    val nextIdxHigh = min(j + matches, scratchCards.size - 1)

                    // add indices of next scratch cards
                    for (k in nextIdxLow..nextIdxHigh) {
                        nextIndices.add(k)
                    }
                }

                curIndices.clear()
                curIndices.addAll(nextIndices)
            } while (nextIndices.isNotEmpty())
        }

        return totalScratchCardCount
    }

    check(part1(readInput("Day04_test")) == 13)
    check(part2(readInput("Day04_test")) == 30)

    val input = readInput("Day04")

    println(part1(input))
    println(part2(input))
}