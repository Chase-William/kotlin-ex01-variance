/**
 *
 *  Written By: Your boi, Chase Roth
 *  Finished on 9/3/2022
 *
 *  So far, I like kotlin more so than last time I was messing around with it, very much like TypeScript
 *
 */

package com.ritstudentchase.kotlinexercise

import org.w3c.dom.css.Rect
import java.util.*

class KotlineExercise {
}

data class Person(val name: String, val age: Int? = null) {
    override fun toString(): String {
        return "Person(name=${name}, age=${age})"
    }
}

class Rectangle(var width: Int, var height: Int = 15) {
    val isSquare get() = width == height
}

// Thought about using functional generic type parameters
// https://kotlinlang.org/docs/generics.html#variance
// Below is the same function in c# for a comparison...
// I do prefer the kotlin / typescript location of the return value over C#'s
// Not fond of the <T> location in kolin, I prefer the C# there
// I also prefer the C# comma separation for "where type constraints" as it's less verbose
/**
    static T max<T>(T left, T right) where T : Number, Comparable<T>
        => left > right ? left : right;
 */
fun <T> max(left: T, right: T): T where T : Number, T : Comparable<T> {
    // Weird turnary operator bro
    return if (left > right) left else right
}

enum class Color {
    RED,
    ORANGE,
    YELLOW,
    GREEN,
    BLUE,
    INDIGO,
    VIOLET
}

fun getMnemomic(color: Color): String {
    return when(color) {
        Color.RED -> "Richard"
        Color.ORANGE -> "Of"
        Color.YELLOW -> "York"
        Color.GREEN -> "Gave"
        Color.BLUE -> "Battle"
        Color.INDIGO -> "In"
        Color.VIOLET -> "Vain"
    }
}

val WARM_COLOR_STR = "warm"
val NEUTRAL_COLOR_STR = "neutral"
val COLD_COLOR_STR = "cold"

fun getWarmth(color: Color): String {
    // Damn i'm good, I just guessed the comma separated values would work and it does
    return when (color) {
        Color.RED, Color.ORANGE, Color.YELLOW -> WARM_COLOR_STR
        Color.GREEN -> NEUTRAL_COLOR_STR
        else -> COLD_COLOR_STR
    }
}

fun String.lastChar(): Char {
    return this.last()
}

fun printAllCaps(str: String? = null) {
    /** Didn't use null chain because I don't want to call println with null arg
    if (str != null)
        println(str.uppercase())
    */
    // changed off example output
    println(str?.uppercase())
}

fun strLenSafe(str: String? = null): Int {
    // Wtf how did someone come up with elvis operator for this format of null coalescing operator..
    return str?.length ?: 0
}

fun main() {
    // List of immutable persons
    val persons = listOf<Person>(
        Person("Frian Bench", 47),
        Person("Dheck Seck"),
        Person("Rhase Coth", 22)
    )

    // using the null coalescing operator, https://riptutorial.com/kotlin/example/12692/null-coalescing---elvis-operator
    // Using -1 as a value for when the age is null, never mind, the instructions say use 0
    // I thought of this before reading it in the instructions for null coalescing operator usage.. haha, changed -1 to 0
    val oldest = persons.maxByOrNull { it.age ?: 0 }
    // Woah, thought this was the same as JS, but removal of { } is new
    println("The oldest is: $oldest")
    // I had some fun ok
    println(max(1, 2).toString())
    println("Example of floating point accounted for. " + max(1.0, 2.3).toString())

    val rect = Rectangle(41, 43)
    println("Width: ${rect.width}")
    println("Height: ${rect.height}")
    println("Is Square: ${rect.isSquare}")

    val rectvii = Rectangle(15)
    println("Is Square: ${rectvii.isSquare}")

    println(getMnemomic(Color.BLUE))
    println(getWarmth(Color.ORANGE))

    // g
    // The "step" reminds me of "stride" from when I was working with bitmaps in C++, I had to stride by pixel size
    // something like sizeof(3 * sizeof(int))
    // In the past I wouldn't like this, but I've changed and I appreciate the unique approaches like these
    for (i in 100 downTo 1 step 2)
        print(i)
    print("\ndone\n")

    // h.
    val binaryReps = TreeMap<Char, String>()

    for (c in 'A'..'F') {
        val binary = Integer.toBinaryString(c.toInt())
        binaryReps[c] = binary
    }

    for (item in binaryReps) {
        println(item.toString())
    }

    println("Mobile App Dev II".lastChar())

    printAllCaps("abc")
    printAllCaps()

    println(strLenSafe("abc"))
    println(strLenSafe())
}

