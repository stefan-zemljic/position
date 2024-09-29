package ch.bytecraft.position

class Position(val index: Int, val line: Int, val column: Int): Comparable<Position> {
    override fun toString(): String {
        return "$line:$column"
    }

    override fun compareTo(other: Position): Int {
        return index.compareTo(other.index)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Position) return false
        return index == other.index && line == other.line && column == other.column
    }

    override fun hashCode(): Int {
        var result = index
        result = 31 * result + line
        result = 31 * result + column
        return result
    }
}