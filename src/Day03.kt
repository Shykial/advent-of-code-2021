import RatingType.C02SCRUBBER
import RatingType.OXYGEN_GENERATOR

fun main() {
    val oppositeBits = mapOf(
        '0' to '1',
        '1' to '0'
    )

    fun part1(input: List<String>): Int {
        val gammaRate = (0..input.first().lastIndex).mapNotNull { index ->
            input.map { it[index] }.groupingBy { it }.eachCount().maxByOrNull { it.value }?.key
        }.joinToString("")

        val epsilonRate = gammaRate.map(oppositeBits::getValue).joinToString("")
        return gammaRate.toInt(2) * epsilonRate.toInt(2)
    }

    fun part2(input: List<String>): Int {
        val oxygenValue = input.getRatingFilteredValue(OXYGEN_GENERATOR)
        val c02ScrubberValue = input.getRatingFilteredValue(C02SCRUBBER)
        return oxygenValue.toInt(2) * c02ScrubberValue.toInt(2)
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

enum class RatingType {
    OXYGEN_GENERATOR, C02SCRUBBER
}

fun List<String>.getRatingFilteredValue(ratingType: RatingType): String {
    var result = this
    var currentIndex = 0
    while (result.size > 1) {
        result = result.groupBy { it[currentIndex] }.run {
            getValue(
                when (ratingType) {
                    OXYGEN_GENERATOR -> if (get('1').orEmpty().size >= result.size / 2.0) '1' else '0'
                    C02SCRUBBER -> if (get('0').orEmpty().size <= result.size / 2.0) '0' else '1'
                }
            )
        }
        currentIndex++
    }
    return result.single()
}
