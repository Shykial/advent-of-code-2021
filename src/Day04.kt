import java.io.File

fun main() {
    fun part1(bingoData: BingoData): Int {
        val (chosenNumbers, boards) = bingoData
        chosenNumbers.forEach { number ->
            boards.forEach { board ->
                board.bingoNumberOf(number)?.apply { marked = true }?.let { bingoNumber ->
                    if (board.rowOf(bingoNumber).areAllMarked() || board.columnOf(bingoNumber).areAllMarked()) {
                        return@part1 board.unmarkedNumbers.sumOf { it.value } * number
                    }
                }
            }
        }
        error("Did not find winning board")
    }

    fun part2(bingoData: BingoData): Int {
        val (chosenNumbers, boards) = bingoData
        val remainingBoards = boards.toMutableList()
        chosenNumbers.forEach { number ->
            boards.forEach { board ->
                board.bingoNumberOf(number)?.apply { marked = true }?.let { bingoNumber ->
                    if (board.rowOf(bingoNumber).areAllMarked() || board.columnOf(bingoNumber).areAllMarked()) {
                        remainingBoards -= board
                        if (remainingBoards.isEmpty()) {
                            return@part2 board.unmarkedNumbers.sumOf { it.value } * number
                        }
                    }
                }
            }
        }
        error("Not all board won")
    }

    val inputBingoData = bingoDataFromInput("Day04")

    println(part1(inputBingoData))
    println(part2(inputBingoData))
}

private fun bingoDataFromInput(name: String): BingoData {
    val inputList = File("src", "$name.txt").readText().split(Regex("""\R{2,}"""))

    val chosenNumbers = inputList.first().split(',').map(String::toInt)
    val rawBoards = inputList.subList(1, inputList.size)
    val rowsMapping: (String) -> List<List<String>> = { boardString ->
        boardString.lines().map { it.trim().split(Regex("""\s+""")) }
    }
    val bingoNumbersMapping: (List<List<String>>) -> List<BingoNumber> = { rows ->
        rows.flatMap { row ->
            row.map { BingoNumber(it.toInt(), rows.indexOf(row) + 1, row.indexOf(it) + 1) }
        }
    }
    val boards = rawBoards.map(rowsMapping).map(bingoNumbersMapping).map(::Board)
    return BingoData(chosenNumbers, boards)
}

private data class BingoData(val chosenNumbers: List<Int>, val boards: List<Board>)

@JvmInline
private value class Board(val bingoNumbers: List<BingoNumber>) {
    val unmarkedNumbers: List<BingoNumber>
        get() = bingoNumbers.filterNot { it.marked }

    fun rowOf(number: BingoNumber) = bingoNumbers.filter { it.row == number.row }
    fun columnOf(number: BingoNumber) = bingoNumbers.filter { it.column == number.column }
    fun bingoNumberOf(value: Int): BingoNumber? = bingoNumbers.firstOrNull { it.value == value }
}

private data class BingoNumber(
    val value: Int,
    val row: Int,
    val column: Int,
    var marked: Boolean = false
)

private fun Collection<BingoNumber>.areAllMarked() = all { it.marked }