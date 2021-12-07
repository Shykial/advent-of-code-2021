import kotlin.math.abs

fun main() {
    fun part1(input: List<Int>) = input.rangeBetween.minOf { number -> input.sumOf { abs(number - it) } }

    fun part2(input: List<Int>) = input.rangeBetween.minOf { number ->
        input.sumOf {
            abs(number - it).let { distance -> distance * (distance + 1) / 2 }
        }
    }

    val input = readRawInput("Day07").split(',').map(String::toInt)
    println(part1(input))
    println(part2(input))
}

private val List<Int>.rangeBetween
    get() = (minOrNull() ?: error("empty list passed"))..(maxOrNull() ?: error("empty list passed"))