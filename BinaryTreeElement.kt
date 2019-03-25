public class BinaryTreeElement (
        var element: Int,
        var leftChild: BinaryTreeElement?,
        var rightChild: BinaryTreeElement?) {
    fun isNull(a: BinaryTreeElement): Boolean = a.element == null
    fun isNotNull (a: BinaryTreeElement): Boolean = a.element != null
}