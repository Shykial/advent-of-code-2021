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
            from.x == to.x -> sortedIntRange(from.y, to.y).map { Coordinate(from.x, it) }
            from.y == to.y -> sortedIntRange(from.x, to.x).map { Coordinate(it, from.y) }
            else -> {
                val yRange = sortedIntRange(from.y, to.y).let {
                    if ((to.x - from.x) * (to.y - from.y) > 0) it else it.reversed()
                }
                (sortedIntRange(from.x, to.x) zip yRange).map { (x, y) -> Coordinate(x, y) }
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

private fun sortedIntRange(first: Int, second: Int) = if (second > first) first..second else second..first