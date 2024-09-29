package ch.bytecraft.position

class PositionComputer(source: String, caching: Boolean? = CACHE_BY_DEFAULT) {
    companion object {
        private const val CACHE_BY_DEFAULT = false
        private val rLineBreak = Regex("\r\n?|\n")
    }

    private val len = source.length

    private val cache = when (caching ?: CACHE_BY_DEFAULT) {
        true -> mutableMapOf<Int, Position>()
        false -> null
    }

    private val lineStarts = mutableListOf(0).apply {
        rLineBreak.findAll(source).forEach { match ->
            add(match.range.last + 1)
        }
    }

    operator fun invoke(index: Int): Position {
        cache?.get(index)?.let { return it }
        if (index > len) throw IndexOutOfBoundsException(index)
        val lineIndex = lineStarts.binarySearch(index).let { if (it < 0) -it - 2 else it }
        val columnIndex = index - lineStarts[lineIndex]
        val result = Position(index, lineIndex + 1, columnIndex + 1)
        if (cache != null) cache[index] = result
        return result
    }
}