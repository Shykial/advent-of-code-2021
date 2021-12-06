fun main() {
    fun part1(inputLines: List<VentLine>) = inputLines
        .filter { (from, to) -> from.x == to.x || from.y == to.y }
        .flatMap { it.overlappedCoordinates }.groupingBy { it }.eachCount()
        .count { it.value >= 2 }

    fun part2(inputLines: List<VentLine>) = inputLines
        .flatMap { it.overlappedCoordinates }.groupingBy { it }.eachCount()
        .count { it.value >= 2 }

    val input = readVentLinesFromInput("Day05")
    println(part1(input))
    println(part2(input))
}

private data class Coordinate(val x: Int, val y: Int)

private data class VentLine(val from: Coordinate, val to: Coordinate) {
    val overlappedCoordinates: List<Coordinate> by lazy {
        when {
            from.x == to.x -> {
                val (fromY, toY) = listOf(from.y, to.y).sorted()
                (fromY..toY).map { Coordinate(from.x, it) }
            }
            from.y == to.y -> {
                val (fromX, toX) = listOf(from.x, to.x).sorted()
                (fromX..toX).map { Coordinate(it, from.y) }
            }
            else -> {
                val (fromX, toX) = listOf(from.x, to.x).sorted()
                val (fromY, toY) = listOf(from.y, to.y).sorted()
                val yRange = if ((to.x - from.x) * (to.y - from.y) > 0) fromY..toY else toY downTo fromY
                (fromX..toX).zip(yRange).map { (x, y) -> Coordinate(x, y) }
            }
        }
    }
}

private fun readVentLinesFromInput(inputFile: String) = readInput(inputFile).map { line ->
    val (from, to) = line.split(" -> ").map {
        val (x, y) = it.split(",").map(String::toInt)
        Coordinate(x, y)
    }
    VentLine(from, to)
}