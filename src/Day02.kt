fun main() {
    fun part1(input: List<Pair<String, Int>>): Int =
        input.groupingBy { it.first }.fold(0) { sum, pair ->
            sum + pair.second
        }.run {
            getValue("forward") * (getValue("down") - getValue("up"))
        }

    fun part2(input: List<Pair<String, Int>>): Int {
        var (depth, aim, horizontalPosition) = listOf(0, 0, 0)
        input.forEach { (command, value) ->
            when (command) {
                "up" -> aim -= value
                "down" -> aim += value
                "forward" -> {
                    horizontalPosition += value
                    depth += aim * value
                }
            }
        }
        return depth * horizontalPosition
    }

    val input = readInput("Day02").toStringIntPairs()
    println(part1(input))
    println(part2(input))
}

private fun List<String>.toStringIntPairs() = map {
    val (direction, number) = it.split(" ")
    direction to number.toInt()
}