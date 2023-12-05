typealias Point2 = Pair<Int, Int>

fun main() {

    fun getAdjacentPoints(point: Point2): List<Point2> {
        val adjacentPoints = mutableListOf<Point2>()

        for (i in -1..1) {
            for (j in -1..1) {
                if (i == 0 && j == 0) {
                    continue
                }

                adjacentPoints.add(Point2(point.first + i, point.second + j))
            }
        }

        return adjacentPoints
    }

    fun parseInput(input: List<String>): Pair<Map<Point2, Char>, Map<Point2, Char>> {
        val digits = "0123456789"
        val pointToDigitMap = mutableMapOf<Point2, Char>()
        val pointToSymbolMap = mutableMapOf<Point2, Char>()

        input.forEachIndexed { yCoord, line ->
            line.forEachIndexed { xCoord, char ->
                val thisPoint = Point2(xCoord, yCoord)

                if (digits.contains(char)) {
                    pointToDigitMap[thisPoint] = char
                } else if (char != '.') {
                    pointToSymbolMap[thisPoint] = char
                }
            }
        }

        return Pair(pointToDigitMap, pointToSymbolMap)
    }

    fun part1(input: List<String>): Int {
        val (pointToDigitMap, pointToSymbolMap) = parseInput(input)
        val symbolPointList = pointToSymbolMap.keys

        val consideredDigitPointList = mutableListOf<Point2>()
        val numberList = mutableListOf<Int>()

        // get all symbols
        for (symbolPoint in symbolPointList) {
            // consider only points that are adjacent to a symbol
            for (adjacentPoint in getAdjacentPoints(symbolPoint)) {
                // skip if the adjacent point has already been evaluated
                if (consideredDigitPointList.contains(adjacentPoint)) {
                    continue
                }

                // skip if the adjacent point doesn't contain a digit
                if (!pointToDigitMap.containsKey(adjacentPoint)) {
                    continue
                }

                // mark this point as processed
                consideredDigitPointList.add(adjacentPoint)

                // start the number string
                val charAtPoint = pointToDigitMap[adjacentPoint]
                var numberStr = "$charAtPoint"

                // setup stuff for loop
                var offset = 1
                var prevEnd = false
                var nextEnd = false

                while (true) {
                    // determine neighboring points
                    val prevPoint = Point2(adjacentPoint.first - offset, adjacentPoint.second)
                    val nextPoint = Point2(adjacentPoint.first + offset, adjacentPoint.second)

                    val prevChar = pointToDigitMap[prevPoint]
                    val nextChar = pointToDigitMap[nextPoint]

                    // check if we hit a non-digit at the start
                    if (prevChar == null && !prevEnd) {
                        prevEnd = true
                    }

                    // check if we hit a non-digit at the end
                    if (nextChar == null && !nextEnd) {
                        nextEnd = true
                    }

                    // break if both ends have been reached
                    if (prevEnd && nextEnd) break

                    // prepend digit
                    if (!prevEnd) {
                        numberStr = "$prevChar$numberStr"
                        consideredDigitPointList.add(prevPoint)
                    }

                    // append digit
                    if (!nextEnd) {
                        numberStr = "$numberStr$nextChar"
                        consideredDigitPointList.add(nextPoint)
                    }

                    offset++
                }

                numberList.add(numberStr.toInt())
            }
        }

        return numberList.sum()
    }

    fun part2(input: List<String>): Int {
        val (pointToDigitMap, pointToSymbolMap) = parseInput(input)
        // keep only the points that map to a gear (*)
        val gearPointList = pointToSymbolMap.filter { (_, v) -> v == '*' }.keys
        // keep track of all points that have been visited
        val consideredDigitPointList = mutableListOf<Point2>()

        // tally up all gear ratios
        var totalGearRatio = 0

        // iterate over the points for gears
        for (gearPoint in gearPointList) {
            // keep track of numbers that are adjacent to this gear
            val numberList = mutableListOf<Int>()

            // iterate over the adjacent points
            for (adjacentPoint in getAdjacentPoints(gearPoint)) {
                // skip if already visited
                if (consideredDigitPointList.contains(adjacentPoint)) continue

                // skip if there's no digit at this point
                if (!pointToDigitMap.containsKey(adjacentPoint)) continue

                // mark point as visited
                consideredDigitPointList.add(adjacentPoint)

                // start the number string
                val charAtPoint = pointToDigitMap[adjacentPoint]
                var numberStr = "$charAtPoint"

                // setup stuff for loop
                var offset = 1
                var prevEnd = false
                var nextEnd = false

                // literally the same code as above
                while (true) {
                    // determine neighboring points
                    val prevPoint = Point2(adjacentPoint.first - offset, adjacentPoint.second)
                    val nextPoint = Point2(adjacentPoint.first + offset, adjacentPoint.second)

                    val prevChar = pointToDigitMap[prevPoint]
                    val nextChar = pointToDigitMap[nextPoint]

                    // check if we hit a non-digit at the start
                    if (prevChar == null && !prevEnd) {
                        prevEnd = true
                    }

                    // check if we hit a non-digit at the end
                    if (nextChar == null && !nextEnd) {
                        nextEnd = true
                    }

                    // break if both ends have been reached
                    if (prevEnd && nextEnd) break

                    // prepend digit
                    if (!prevEnd) {
                        numberStr = "$prevChar$numberStr"
                        consideredDigitPointList.add(prevPoint)
                    }

                    // append digit
                    if (!nextEnd) {
                        numberStr = "$numberStr$nextChar"
                        consideredDigitPointList.add(nextPoint)
                    }

                    offset++
                }

                numberList.add(numberStr.toInt())
            }

            // there must be exactly two numbers around this gear
            if (numberList.size != 2) continue

            totalGearRatio += numberList[0] * numberList[1]
        }

        return totalGearRatio
    }

    check(part1(readInput("Day03_test")) == 4361)
    check(part2(readInput("Day03_test")) == 467835)

    val input = readInput("Day03")

    println(part1(input))
    println(part2(input))
}