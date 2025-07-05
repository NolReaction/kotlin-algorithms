// Мой учебный проект: CLI-программа для сортировки массива чисел разными алгоритмами (Kotlin, июль 2025)
// Можно запускать через main(), поддерживаются пузырьковая и адаптивная пузырьковая сортировка.
// По мотивам самостоятельной работы

package org.example

fun main() {
    var bestBubbleTime: Double? = null
    var bestAdaptiveBubbleTime: Double? = null

    println("Здравствуй !\nНапиши первоначальный массив чисел:")
    val startInput = readln()
    var startInputArray = stringToIntArray(startInput)

    println()
    while (true) {
        println("Выберите доступную сортировку или команду:\n" +
                "0: END\n" +
                "1: Bubble Sort\n" +
                "2: Adaptive Bubble Sort\n" +
                "Bt: Узнать лучшее время \n" +
                "Ch: Для изменения массива"
        )

        val sortChoice = readln().lowercase()
        when (sortChoice) {
            "0" -> break
            "1" -> {
                val result = bubbleSort(startInputArray.copyOf())
                printSortArrayInfo(result)
                if (bestBubbleTime == null || result.finishTime!! < bestBubbleTime!! ) {
                    bestBubbleTime = result.finishTime
                    println("Новое лучшее время Bubble Sort: ${"%.4f".format(bestBubbleTime)} ms")
                }
                println()
            }
            "2" -> {
                val result = adaptiveBubbleSort(startInputArray.copyOf())
                printSortArrayInfo(result)
                if (bestAdaptiveBubbleTime == null || result.finishTime!! < bestAdaptiveBubbleTime!!) {
                    bestAdaptiveBubbleTime = result.finishTime
                    println("Новое лучшее время Adaptive Bubble Sort: ${"%.4f".format(bestAdaptiveBubbleTime)} ms")
                }
                println()
            }
            "bt" -> {
                println("Лучшее время Bubble Sort: ${bestBubbleTime?.let { "%.4f".format(it) } ?: "нет"} ms")
                println("Лучшее время Adaptive Bubble Sort: ${bestAdaptiveBubbleTime?.let { "%.4f".format(it) } ?: "нет"} ms")
                println()
            }
            "ch" -> {
                println("Лучшее время было сброшено !")
                println("Введите новый массив:")
                val newArray = readln()
                startInputArray = stringToIntArray(newArray)
                bestBubbleTime = null
                bestAdaptiveBubbleTime = null
            }
            else -> println("Неверный выбор, попробуйте снова.")
        }
    }
}

data class SortResult<T>(
    val sortName: String? = null,
    val startArray: List<T>? = null,
    val sortedArray: List<T>? = null,
    val iterations: Int? = null,
    val finishTime: Double? = null
)

fun stringToIntArray (str: String): IntArray {
    val numbers = str
        .split(" ")
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .toIntArray()
    return numbers
}

fun bubbleSort (array: IntArray) : SortResult<Int> {
    val startArrayCopy = array.copyOf().toList()
    var iterations = 0
    var finishTime: Double
    val startTime = System.nanoTime()
    for (i in 0 until array.size - 1) {
        for (j in 0 until array.size - 1 - i) {
            iterations++
            if (array[j] > array[j+1]) {
                val temp = array[j]
                array[j] = array[j+1]
                array[j+1] = temp
            }
        }
    }
    val endTime = System.nanoTime()
    finishTime = (endTime - startTime) / 1_000_000.0

    return SortResult(
        sortName = "Bubble Sort",
        startArray = startArrayCopy,
        sortedArray = array.toList(),
        iterations = iterations,
        finishTime = finishTime
    )
}

fun adaptiveBubbleSort (array: IntArray) : SortResult<Int> {
    val startArrayCopy = array.copyOf().toList()
    var iterations = 0
    var finishTime: Double
    val startTime = System.nanoTime()
    for (i in 0 until array.size - 1) {
        var swapped = false
        for (j in 0 until array.size - 1 - i) {
            iterations++
            if (array[j] > array[j+1]) {
                val temp = array[j]
                array[j] = array[j+1]
                array[j+1] = temp
                swapped = true
            }
        }
        if (!swapped) break
    }
    val endTime = System.nanoTime()
    finishTime = (endTime - startTime) / 1_000_000.0

    return SortResult(
        sortName = "Adaptive Bubble Sort",
        startArray = startArrayCopy,
        sortedArray = array.toList(),
        iterations = iterations,
        finishTime = finishTime
    )
}

fun printSortArrayInfo(arrayInfo: SortResult<*>) {
    println("Sort: ${arrayInfo.sortName}")
    println("Start array: ${arrayInfo.startArray}")
    println("Sorted array: [${arrayInfo.sortedArray?.joinToString()}]")
    println("Iterations: ${arrayInfo.iterations}")
    println("Finish time: ${arrayInfo.finishTime} ms")
}

