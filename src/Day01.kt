fun main() {
    fun part1(input: List<Int>): Int = input.zipWithNext().count { it.second > it.first }

    fun part2(input: List<Int>): Int = input.windowed(3).zipWithNext().count { it.second.sum() > it.first.sum() }

    val input = readInput("Day01").map(String::toInt)
    println(part1(input))
    println(part2(input))
}
