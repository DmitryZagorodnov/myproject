import junit.framework.Assert.assertTrue
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Tests {
    @Test
    fun isEmpty() {
        val root = BinaryTreeElement(4, null, null)
        var tree = BinaryTree(root)
        assertFalse(tree.isEmpty())
    }

    @Test
    fun add(n: Int) {
        var tree = BinaryTree(root = BinaryTreeElement(4, null, null))
        var n = 5
        assertEquals("Добавлено", tree.add(n))
    }

    @Test
    fun hasAlready() {
        var tree = BinaryTree(root = BinaryTreeElement(4, BinaryTreeElement(3, null, null), null))
        var n = 3
        assertTrue(tree.hasAlready(n))
    }

    @Test
    fun findLeftChild() {
        var root = BinaryTree(root = BinaryTreeElement(4, BinaryTreeElement(3, null, null), null))
        var x = 4
        assertEquals(3, root.findLeftChild(x))
    }

    @Test
    fun findRightChild() {
        var root = BinaryTree(root = BinaryTreeElement(4, BinaryTreeElement(3, null, null), null))
        var x = 5
        assertEquals("Такого числа нет в дереве", root.findRightChild(x))
    }
}