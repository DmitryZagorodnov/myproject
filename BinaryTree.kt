class BinaryTree(var root: BinaryTreeElement) {
    fun isEmpty(): Boolean {                    //Проверяет, есть ли элементы в дереве, или же оно пустое
        return root == null
    }

    fun isNotEmpty(): Boolean {
        return root != null
    }

    fun plantTheTree(n: Int) = BinaryTree(BinaryTreeElement(n, null, null))       //Создаем дерево

    private fun compare(a: BinaryTreeElement, b: Int): Int {    //Сравнивает узел дерева и целое число
        return when {
            a.element > b -> 1
            a.element < b -> -1
            else -> 0
        }
    }

    private fun toTheLeftChild(a: BinaryTreeElement) =
            BinaryTreeElement(a.leftChild!!.element, a.leftChild?.leftChild, a.leftChild?.rightChild)   //Переходим к левому потомку узла

    private fun toTheRightChild(a: BinaryTreeElement) =
            BinaryTreeElement(a.rightChild!!.element, a.rightChild?.leftChild, a.rightChild?.rightChild)   //Переходим к правому потомку узла

    fun add(element: Int): Any {
        if (this.isEmpty()) return BinaryTreeElement(element, null, null)
        var a = BinaryTreeElement(this.root.element, this.root.leftChild, this.root.rightChild)
        println(a.element)
        while ((compare(a, element) != 1 && a.leftChild != null) ||       //Пока не сможем вставить число налево
                (compare(a, element) != -1 && a.rightChild != null) ||    //направо
                compare(a, element) != 0) {                               //или не обнаружим, что такое число уже есть
            when {
                compare(a, element) == 1 -> {                             //Переходим к левому элементу
                    a = toTheLeftChild(a); println(a.element)
                }
                compare(a, element) == -1 -> {                            //Или к правому
                    a = toTheRightChild(a); println(a.element)
                }
                else -> a                                                 //Просто для else, такого не будет
            }
        }
        when {
            compare(a, element) == 1 && a.leftChild == null -> {
                a.leftChild = BinaryTreeElement(element, null, null); return "Добавлено"  //Добавляем элемент
            }
            compare(a, element) == -1 && a.rightChild == null -> {
                a.rightChild = BinaryTreeElement(element, null, null); return "Добавлено"
            }
            else -> {
                println("Такое число уже есть в дереве"); return "Уже есть"
            }
        }
    }


    fun hasAlready(x: Int): Boolean = add(x) == "Уже есть"

    fun findLeftChild(x: Int): Any? {
        if (!hasAlready(x)) return "Такого числа нет в дереве"
        else {
            var a = BinaryTreeElement(this.root.element, this.root.leftChild, this.root.rightChild)
            while (compare(a, x) != 0) {
                when {
                    compare(a, x) == 1 -> a = toTheLeftChild(a)
                    compare(a, x) == -1 -> a = toTheRightChild(a)
                    else -> a
                }
            }
            return a.leftChild
        }
    }

    fun findRightChild(x: Int): Any? {
        if (!hasAlready(x)) return "Такого числа нет в дереве"
        else {
            var a = BinaryTreeElement(this.root.element, this.root.leftChild, this.root.rightChild)
            while (compare(a, x) != 0) {
                when {
                    compare(a, x) == 1 -> a = toTheLeftChild(a)
                    compare(a, x) == -1 -> a = toTheRightChild(a)
                    else -> a
                }
            }
            return a.rightChild
        }
    }

    fun findParent(x: Int): Any? {
        if (!hasAlready(x)) return "Такого числа нет в дереве"
        if (this.root.element == x) return "У этого числа нет родителя"
        else {
            var a = BinaryTreeElement(this.root.element, this.root.leftChild, this.root.rightChild)
            while (toTheLeftChild(a).element != x || toTheRightChild(a).element != x) {   //Пока какой-нибудь из потомков
                when {                                                                    //не будет равен искомому,
                    compare(a, x) == 1 -> a = toTheLeftChild(a)                           //спускаемся по дереву
                    compare(a, x) == -1 -> a = toTheRightChild(a)
                    else -> a
                }
            }
        return a.element                                                 //Когда нашли искомое число среди потомков,
        }                                                                //выводим родителя
    }

    fun remove(x: Int): Any? {
        if (!hasAlready(x)) return "Такого числа нет в дереве"

    }

    private fun replaceNodeAndLeftChild(treeElement: BinaryTreeElement) {
        var t = treeElement.element
        treeElement.element = treeElement.leftChild!!.element
        treeElement.leftChild = treeElement.leftChild?.leftChild
        treeElement.leftChild = treeElement.leftChild?.rightChild
    }


}



