import java.lang.IllegalStateException

enum class CubeColor {
    RED, GREEN, BLUE
}

data class Game(val id: Int, val cubeDraws: MutableList<MutableMap<CubeColor, Int>>)

fun main() {
    fun parseGames(input: List<String>): List<Game> {
        val games: MutableList<Game> = mutableListOf()

        for (line in input) {
            val (gameIdStr, gameDrawsStr) = line.split(":")

            // game id is what follows after "Game" at the start of the line
            val gameId = gameIdStr.substring("Game".length + 1).toInt()

            // draws from the bag are separated by semicolons
            val gameDrawsList = gameDrawsStr.trim().split(";")
            val cubeDraws: MutableList<MutableMap<CubeColor, Int>> = mutableListOf()

            for (gameDraw in gameDrawsList) {
                // individual cubes are separated by commas
                val cubeDrawsList = gameDraw.split(",")
                val cubeDrawMap: MutableMap<CubeColor, Int> = mutableMapOf()

                for (cubeDraw in cubeDrawsList) {
                    val (cubeCountStr, cubeColor) = cubeDraw.trim().split(" ")
                    val cubeCount = cubeCountStr.toInt()

                    when (cubeColor) {
                        "red" -> cubeDrawMap[CubeColor.RED] = cubeCount
                        "green" -> cubeDrawMap[CubeColor.GREEN] = cubeCount
                        "blue" -> cubeDrawMap[CubeColor.BLUE] = cubeCount
                        else -> throw IllegalStateException("illegal color $cubeColor")
                    }
                }

                cubeDraws.add(cubeDrawMap)
            }

            games.add(Game(gameId, cubeDraws))
        }

        return games
    }

    fun part1(input: List<String>): Int {
        return parseGames(input).filter { game ->
            for (draw in game.cubeDraws) {
                for ((color, count) in draw.entries) {
                    if (
                        (color == CubeColor.RED && count > 12) ||
                        (color == CubeColor.GREEN && count > 13) ||
                        (color == CubeColor.BLUE && count > 14)
                    ) {
                        return@filter false
                    }
                }
            }

            return@filter true
        }.sumOf { game -> game.id }
    }

    fun part2(input: List<String>): Int {
        return parseGames(input).map { game ->
            val cubeMap = mutableMapOf(
                CubeColor.RED to 0,
                CubeColor.GREEN to 0,
                CubeColor.BLUE to 0
            )

            // this feels dirty
            for (draw in game.cubeDraws) {
                cubeMap[CubeColor.RED] = cubeMap[CubeColor.RED]!!.coerceAtLeast(draw.getOrDefault(CubeColor.RED, 0))
                cubeMap[CubeColor.GREEN] =
                    cubeMap[CubeColor.GREEN]!!.coerceAtLeast(draw.getOrDefault(CubeColor.GREEN, 0))
                cubeMap[CubeColor.BLUE] = cubeMap[CubeColor.BLUE]!!.coerceAtLeast(draw.getOrDefault(CubeColor.BLUE, 0))
            }

            return@map cubeMap[CubeColor.RED]!! * cubeMap[CubeColor.GREEN]!! * cubeMap[CubeColor.BLUE]!!
        }.sum()
    }

    check(part1(readInput("Day02_part1_test")) == 8)
    check(part2(readInput("Day02_part1_test")) == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
