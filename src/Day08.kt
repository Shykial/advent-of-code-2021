private val UNIQUE_LENGTHS = setOf(2, 3, 4, 7)
private val DIGIT_LETTERS = "abcdefg".toSet()

fun main() {
    fun part1(input: List<SignalEntry>) = input.sumOf { (_, output) -> output.count { it.length in UNIQUE_LENGTHS } }

    fun part2(input: List<SignalEntry>) = input.sumOf { (signals, outputValues) ->
        val map = signals.toDigitPositionMap()
        outputValues.joinToString("") { map.getValue(it.toSet().toSortedString()) }.toInt()
    }

    val input: List<SignalEntry> = readInput("Day08").toSignalEntries()

    println(part1(input))
    println(part2(input))
}

private data class SignalEntry(val signals: List<String>, val outputValues: List<String>)

private fun List<String>.toSignalEntries() = map { line ->
    val (first, second) = line.split(" | ").map { it.split(' ') }
    SignalEntry(first, second)
}

private fun List<String>.toDigitPositionMap(): Map<String, String> {
    val firstOne = first { it.length == 2 }.toSet()
    val firstSeven = first { it.length == 3 }.toSet()
    val firstFour = first { it.length == 4 }.toSet()
    val u = firstSeven - firstOne
    val luM = firstFour - firstOne
    val firstZero = first { it.length == 6 && !it.toSet().containsAll(luM) }.toSet()
    val m = (DIGIT_LETTERS - firstZero).toSet()
    val lu = luM - m
    val firstSix = first { signal ->
        signal.length == 6 && firstOne.toList().let { (it[0] in signal) xor (it[1] in signal) }
    }.toSet()
    val ru = firstOne - firstSix
    val rd = firstOne - ru
    val firstNine = first { it.length == 6 && it.toSet().containsAll(u + lu + ru + m) }.toSet()
    val ld = DIGIT_LETTERS - firstNine

    return mapOf(
        firstZero.toSortedString() to "0",
        firstOne.toSortedString() to "1",
        (DIGIT_LETTERS - lu - rd).toSortedString() to "2",
        (DIGIT_LETTERS - lu - ld).toSortedString() to "3",
        firstFour.toSortedString() to "4",
        (DIGIT_LETTERS - ru - ld).toSortedString() to "5",
        firstSix.toSortedString() to "6",
        firstSeven.toSortedString() to "7",
        DIGIT_LETTERS.toSortedString() to "8",
        firstNine.toSortedString() to "9"
    )
}

private fun Collection<Char>.toSortedString() = sorted().joinToString("")
