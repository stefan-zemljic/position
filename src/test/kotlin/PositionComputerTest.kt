package ch.bytecraft.position

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class PositionComputerTest {
    @Nested
    @DisplayName("Basic Functionality")
    inner class BasicFunctionalityTests {
        @Test
        fun `should compute Position correctly for single-line source`() {
            val source = "Hello, World!"
            val computer = PositionComputer(source)

            // Test various indices
            val positions = listOf(
                0 to Position(0, 1, 1),
                5 to Position(5, 1, 6),
                12 to Position(12, 1, 13)
            )

            positions.forEach { (index, expected) ->
                val actual = computer(index)
                assertEquals(expected, actual, "Index $index should be $expected but was $actual")
            }
        }

        @Test
        fun `should compute Position correctly for multi-line source with mixed line breaks`() {
            val source = "Line1\nLine2\r\nLine3\rLine4"
            val computer = PositionComputer(source)

            val positions = listOf(
                0 to Position(0, 1, 1),    // L
                5 to Position(5, 1, 6),    // \n
                6 to Position(6, 2, 1),    // L of Line2
                11 to Position(11, 2, 6),  // \r
                12 to Position(12, 2, 7),  // \n
                13 to Position(13, 3, 1),  // L of Line3
                18 to Position(18, 3, 6),  // \r
                19 to Position(19, 4, 1),  // L of Line4
                24 to Position(24, 4, 6)   // End of source
            )

            positions.forEach { (index, expected) ->
                val actual = computer(index)
                assertEquals(expected, actual, "Index $index should be $expected but was $actual")
            }
        }
    }

    @Nested
    @DisplayName("Boundary Conditions")
    inner class BoundaryConditionTests {
        @Test
        fun `should compute Position at the start of the source`() {
            val source = "Start of source"
            val computer = PositionComputer(source)
            val position = computer(0)
            assertEquals(Position(0, 1, 1), position)
        }

        @Test
        fun `should compute Position at the end of the source`() {
            val source = "End"
            val computer = PositionComputer(source)
            val position = computer(source.length - 1)
            assertEquals(Position(source.length - 1, 1, source.length), position)
        }

        @Test
        fun `should compute Position immediately before a line break`() {
            val source = "Line1\nLine2"
            val computer = PositionComputer(source)
            val index = 4 // '1' in "Line1"
            val expected = Position(4, 1, 5)
            val actual = computer(index)
            assertEquals(expected, actual)
        }

        @Test
        fun `should compute Position immediately after a line break`() {
            val source = "Line1\nLine2"
            val computer = PositionComputer(source)
            val index = 6 // 'L' in "Line2"
            val expected = Position(6, 2, 1)
            val actual = computer(index)
            assertEquals(expected, actual)
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    inner class EdgeCaseTests {
        @Test
        fun `should handle empty source string`() {
            val source = ""
            val computer = PositionComputer(source)

            // Index 0 should be the only valid index
            val position = computer(0)
            assertEquals(Position(0, 1, 1), position)

            // Any other index should be out of bounds
            assertThrows<IndexOutOfBoundsException> {
                computer(1)
            }
        }

        @Test
        fun `should handle sources with no line breaks`() {
            val source = "No line breaks in this source"
            val computer = PositionComputer(source)

            val positions = listOf(
                0 to Position(0, 1, 1),
                10 to Position(10, 1, 11),
                source.length - 1 to Position(source.length - 1, 1, source.length)
            )

            positions.forEach { (index, expected) ->
                val actual = computer(index)
                assertEquals(expected, actual, "Index $index should be $expected but was $actual")
            }
        }

        @Test
        fun `should handle sources with consecutive line breaks`() {
            val source = "Line1\n\nLine3\r\rLine5"
            val computer = PositionComputer(source)

            val positions = listOf(
                0 to Position(0, 1, 1),
                5 to Position(5, 1, 6),   // \n
                6 to Position(6, 2, 1),   // \n
                7 to Position(7, 3, 1),   // L of Line3
                12 to Position(12, 3, 6), // \r
                13 to Position(13, 4, 1), // \r
                14 to Position(14, 5, 1), // L of Line5
                19 to Position(19, 5, 6)  // End of source
            )

            positions.forEach { (index, expected) ->
                val actual = computer(index)
                assertEquals(expected, actual, "Index $index should be $expected but was $actual")
            }
        }

        @Test
        fun `should handle very large indices`() {
            val source = "A".repeat(1000)
            val computer = PositionComputer(source)

            val index = 999
            val expected = Position(999, 1, 1000)
            val actual = computer(index)
            assertEquals(expected, actual)
        }

        @Test
        fun `should throw exception for negative indices`() {
            val source = "Negative index test"
            val computer = PositionComputer(source)

            assertThrows<IndexOutOfBoundsException> {
                computer(-1)
            }
        }

        @Test
        fun `should throw exception for indices beyond source length`() {
            val source = "Short"
            val computer = PositionComputer(source)

            val position = computer(source.length)
            assertEquals(Position(source.length, 1, source.length + 1), position)
            assertThrows<IndexOutOfBoundsException> {
                computer(source.length + 1)
            }
        }
    }

    @Nested
    @DisplayName("Line Break Variations")
    inner class LineBreakVariationTests {
        @Test
        fun `should handle sources with only newline line breaks`() {
            val source = "Line1\nLine2\nLine3"
            val computer = PositionComputer(source)

            val positions = listOf(
                0 to Position(0, 1, 1),
                5 to Position(5, 1, 6),   // \n
                6 to Position(6, 2, 1),
                11 to Position(11, 2, 6), // \n
                12 to Position(12, 3, 1),
                17 to Position(17, 3, 6)
            )

            positions.forEach { (index, expected) ->
                val actual = computer(index)
                assertEquals(expected, actual)
            }
        }

        @Test
        fun `should handle sources with only carriage return and newline line breaks`() {
            val source = "Line1\r\nLine2\r\nLine3"
            val computer = PositionComputer(source)

            val positions = listOf(
                0 to Position(0, 1, 1),
                5 to Position(5, 1, 6),   // \r
                6 to Position(6, 1, 7),   // \n
                7 to Position(7, 2, 1),
                12 to Position(12, 2, 6), // \r
                13 to Position(13, 2, 7), // \n
                14 to Position(14, 3, 1),
                19 to Position(19, 3, 6)
            )

            positions.forEach { (index, expected) ->
                val actual = computer(index)
                assertEquals(expected, actual)
            }
        }

        @Test
        fun `should handle sources with only carriage return line breaks`() {
            val source = "Line1\rLine2\rLine3"
            val computer = PositionComputer(source)

            val positions = listOf(
                0 to Position(0, 1, 1),
                5 to Position(5, 1, 6),   // \r
                6 to Position(6, 2, 1),
                11 to Position(11, 2, 6), // \r
                12 to Position(12, 3, 1),
                17 to Position(17, 3, 6)
            )

            positions.forEach { (index, expected) ->
                val actual = computer(index)
                assertEquals(expected, actual)
            }
        }

        @Test
        fun `should handle sources with mixed line break types`() {
            val source = "Line1\nLine2\r\nLine3\rLine4"
            val computer = PositionComputer(source)

            val positions = listOf(
                0 to Position(0, 1, 1),
                5 to Position(5, 1, 6),   // \n
                6 to Position(6, 2, 1),
                11 to Position(11, 2, 6), // \r
                12 to Position(12, 2, 7), // \n
                13 to Position(13, 3, 1),
                18 to Position(18, 3, 6), // \r
                19 to Position(19, 4, 1),
                24 to Position(24, 4, 6)
            )

            positions.forEach { (index, expected) ->
                val actual = computer(index)
                assertEquals(expected, actual)
            }
        }
    }
}
