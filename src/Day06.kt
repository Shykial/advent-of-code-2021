fun main() {
    fun part1(input: List<Int>): Int {
        val lanternfishSchool = input.map(::Lanternfish).toMutableList()
        (1..80).forEach { _ ->
            lanternfishSchool.count { it.proceedToNextDayCheckingIfGaveBirth() }
                .also { lanternfishSchool += (1..it).map { Lanternfish() } }
        }
        return lanternfishSchool.size
    }

    fun part2(input: List<Int>): Long {
        val lanternfishOffsetCounter = input.groupingBy { it }.eachCount()
            .mapValues { it.value.toLong() }.toMutableMap()
        val newlyBreadedOffsetCounters = mutableMapOf<Int, Long>().withDefault { 0 }

        (1..255).forEach { day ->
            val currentDayOfCycle = day % 7
            lanternfishOffsetCounter[currentDayOfCycle]?.let {
                val newlyBreaded = it - newlyBreadedOffsetCounters.getValue(currentDayOfCycle)
                newlyBreadedOffsetCounters[currentDayOfCycle] = 0
                lanternfishOffsetCounter.merge((currentDayOfCycle + 2) % 7, newlyBreaded, Long::plus)
                newlyBreadedOffsetCounters[(currentDayOfCycle + 2) % 7] = newlyBreaded
            }
        }
        return lanternfishOffsetCounter.values.sum()
    }

    val input = readRawInput("Day06").split(',').map(String::toInt)
    println(part1(input))
    println(part2(input))
}

private class Lanternfish(var timer: Int = 8) {
    fun proceedToNextDayCheckingIfGaveBirth(): Boolean =
        if (timer == 0) {
            timer = 6
            true
        } else {
            timer--
            false
        }
}