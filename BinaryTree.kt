class BinaryTree(var root: BinaryTreeElement) {
    fun isEmpty(): Boolean {                    //Проверяет, есть ли элементы в дереве, или же оно пустое
        return this.root.element == null
    }

    fun isNotEmpty(): Boolean {
        return root != null
    }

    fun plantTheTree(n: Int) = BinaryTree(BinaryTreeElement(n, null, null))       //Создаем дерево

    private fun compare(a: BinaryTreeElement, b: Int?): Int {    //Сравнивает узел дерева и целое число
        return if (b != null) {
            when {
                a.element > b -> 1
                a.element < b -> -1
                else -> 0
            }
        } else 2
    }

    private fun toTheLeftChild(a: BinaryTreeElement) =
            BinaryTreeElement(a.leftChild!!.element, a.leftChild?.leftChild, a.leftChild?.rightChild)   //Переходим к левому потомку узла

    private fun toTheRightChild(a: BinaryTreeElement) =
            BinaryTreeElement(a.rightChild!!.element, a.rightChild?.leftChild, a.rightChild?.rightChild)   //Переходим к правому потомку узла

    fun add(element: Int): Unit {
        if (this.isEmpty()) BinaryTreeElement(element, null, null)
        var a = BinaryTreeElement(this.root.element, this.root.leftChild, this.root.rightChild)
        println(a.element)
        while ((compare(a, element) != 1 && a.leftChild != null) ||       //Пока не сможем вставить число налево
                (compare(a, element) != -1 && a.rightChild != null) ||    //направо
                compare(a, element) != 0 || compare(a, element) != 2) {                               //или не обнаружим, что такое число уже есть
            when {
                compare(a, element) == 1 -> {                             //Переходим к левому элементу
                    a = toTheLeftChild(a); println(this)
                }
                compare(a, element) == -1 -> {                       //Или к правому
                    a = toTheRightChild(a); println(this)
                }
                else -> a = a
            }
        }
        when {
            compare(a, element) == 1 && a.leftChild == null -> {
                a.leftChild = BinaryTreeElement(element, null, null)  //Добавляем элемент
            }
            compare(a, element) == -1 && a.rightChild == null -> {
                a.rightChild = BinaryTreeElement(element, null, null)
            }
            else -> {
                println("Такое число уже есть в дереве")
            }
        }
    }


    fun hasAlready(x: Int): Boolean {
        while (compare(this.root, x) != 0) {
            when {
                compare(this.root, x) == 1 -> toTheLeftChild(this.root)
                compare(this.root, x) == -1 -> toTheRightChild(this.root)
            }
        }
        return compare(this.root, x) == 0
    }

    private fun hasNotAlready(x: Int): Boolean = !hasAlready(x)

    fun findLeftChild(x: Int): BinaryTreeElement? {
        if (hasNotAlready(x)) return null
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

    fun findRightChild(x: Int): BinaryTreeElement? {
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

    fun remove(x: Int): Any {
        if (hasNotAlready(x)) return "Такого числа нет в дереве"
        else {
            var current = BinaryTreeElement(this.root.element, this.root.leftChild, this.root.rightChild)
            var parent = current
            var isLeftChild = false
            while (current.element != x) {
                parent = current
                when (compare(current, x)) {
                    1 -> {current = toTheLeftChild((current)); isLeftChild = true}
                    -1 -> current = toTheRightChild(current)
                }
            }
            /*  Первый случай, нет потомков  */
            if (current.leftChild == null && current.rightChild == null) {
                if (current.element == x && parent.leftChild === current) {
                    parent.leftChild = null
                }
                if (current.element == x && parent.rightChild === current) {
                    parent.rightChild = null
                }
            } else if (current.rightChild == null) {/*  Второй случай, нет правого потомка  */
                if (current == this.root) {
                    root = toTheLeftChild(current)
                } else if (isLeftChild) {
                    parent.leftChild = current.leftChild
                } else current.rightChild = current.leftChild
            } else if (current.leftChild == null) { /*Нет левого потомка*/
                if (current == root) {
                    root = current.rightChild!!
                } else if (isLeftChild) {
                    parent.leftChild = current.rightChild
                } else parent.rightChild = current.rightChild
            } else { /*  Третий случай, есть оба потомка  */
                val successor = findSuccessor(current.element)
                if (current == root) {
                    root = successor!!
                } else if (isLeftChild) {
                    parent.leftChild = successor
                } else parent.rightChild = successor
            }
        }
        return true
    }

    fun findSuccessor(x: Int): BinaryTreeElement? {
        var parentSuccessor = BinaryTreeElement(x, findLeftChild(x), findRightChild(x)) /*Родитель преемника*/
        var successor = BinaryTreeElement(x, findLeftChild(x), findRightChild(x)) /*Преемник*/
        var current = successor.rightChild  /*Бегающий узел*/
        while (current != null) {
            parentSuccessor = successor
            successor = current
            current = current.leftChild
        } /*Нашли преемника и его родителя*/
        if (successor != findRightChild(x)) { /*Если преемник не совпадает с правым потомком, то его родитель забирает*/
            parentSuccessor.leftChild = successor.rightChild /*себе потомка преемника, чтобы не потерять его*/
            successor.rightChild = findRightChild(x) /*Связываем преемника с правым потомком удаляемого узла*/
        }
        return successor
    }
}





