package com.roth.chase.kotlinexercise

import org.w3c.dom.css.Rect

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
// Holy shit, its basically C#
fun <T> max(left: T, right: T): T where T : Number, T : Comparable<T> {
    // Weird turnary operator bro
    return if (left > right) left else right
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
    // I thought of this before reading it in the instructions.. haha
    val oldest = persons.maxByOrNull { it.age ?: 0 }
    println("The oldest is: ${oldest}")
    // I had some fun ok
    println(max(1, 2).toString())
    println(max(1.0, 2.3).toString())

    val rect = Rectangle(41, 43)
    println("Width: ${rect.width}")
    println("Height: ${rect.height}")
    println("Is Square: ${rect.isSquare}")

}