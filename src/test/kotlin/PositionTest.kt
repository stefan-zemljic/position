package ch.bytecraft.position

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PositionTest {
    @Nested
    @DisplayName("Constructor and Property Accessors")
    inner class ConstructorTests {
        @Test
        fun `should initialize properties correctly`() {
            val position = Position(index = 1, line = 10, column = 20)
            assertEquals(1, position.index)
            assertEquals(10, position.line)
            assertEquals(20, position.column)
        }
    }

    @Nested
    @DisplayName("toString Method")
    inner class ToStringTests {
        @Test
        fun `should return string in correct format`() {
            val position = Position(index = 2, line = 15, column = 25)
            assertEquals("15:25", position.toString())
        }
    }

    @Nested
    @DisplayName("compareTo Method")
    inner class CompareToTests {
        @Test
        fun `should return negative when this index is less than other`() {
            val pos1 = Position(index = 1, line = 5, column = 5)
            val pos2 = Position(index = 2, line = 5, column = 5)
            assertTrue(pos1 < pos2)
        }

        @Test
        fun `should return zero when indices are equal`() {
            val pos1 = Position(index = 3, line = 10, column = 10)
            val pos2 = Position(index = 3, line = 20, column = 20)
            assertEquals(0, pos1.compareTo(pos2))
        }

        @Test
        fun `should return positive when this index is greater than other`() {
            val pos1 = Position(index = 5, line = 15, column = 15)
            val pos2 = Position(index = 4, line = 15, column = 15)
            assertTrue(pos1 > pos2)
        }
    }

    @Nested
    @DisplayName("equals Method")
    inner class EqualsTests {
        @Test
        fun `should be equal for same instance`() {
            val pos = Position(index = 6, line = 15, column = 25)
            assertEquals(pos, pos)
        }

        @Test
        fun `should be equal for same index, line, and column`() {
            val pos1 = Position(index = 7, line = 20, column = 30)
            val pos2 = Position(index = 7, line = 20, column = 30)
            assertEquals(pos1, pos2)
        }

        @Test
        fun `should not be equal for different indices`() {
            val pos1 = Position(index = 8, line = 25, column = 35)
            val pos2 = Position(index = 9, line = 25, column = 35)
            assertNotEquals(pos1, pos2)
        }

        @Test
        fun `should not be equal for different lines`() {
            val pos1 = Position(index = 10, line = 30, column = 40)
            val pos2 = Position(index = 10, line = 31, column = 40)
            assertNotEquals(pos1, pos2)
        }

        @Test
        fun `should not be equal for different columns`() {
            val pos1 = Position(index = 11, line = 35, column = 45)
            val pos2 = Position(index = 11, line = 35, column = 46)
            assertNotEquals(pos1, pos2)
        }

        @Test
        fun `should not be equal to null`() {
            val pos = Position(index = 12, line = 40, column = 50)
            assertNotEquals(pos, null)
        }

        @Test
        fun `should not be equal to different class instance`() {
            val pos = Position(index = 13, line = 45, column = 55)
            val other = "Not a Position"
            assertNotEquals(pos, other)
        }
    }

    @Nested
    @DisplayName("hashCode Method")
    inner class HashCodeTests {
        @Test
        fun `equal objects should have same hash code`() {
            val pos1 = Position(index = 14, line = 50, column = 60)
            val pos2 = Position(index = 14, line = 50, column = 60)
            assertEquals(pos1.hashCode(), pos2.hashCode())
        }

        @Test
        fun `non-equal objects should have different hash codes`() {
            val pos1 = Position(index = 15, line = 55, column = 65)
            val pos2 = Position(index = 16, line = 55, column = 65)
            assertNotEquals(pos1.hashCode(), pos2.hashCode())
        }
    }
}
