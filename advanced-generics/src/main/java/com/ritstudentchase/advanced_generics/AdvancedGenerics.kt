package com.ritstudentchase.advanced_generics

import sun.reflect.generics.tree.BaseType

/* Producer Example */

interface Producer<out T> {
    fun next(): T
    // The following does not compile as this interface's T type parameter cannot be used as a consumer
    // Meaning, no instance of T in any capacity can be given to this interface's functions
    // Instances of T can only flow out, not in
    // Read-only
    // fun add(item: T)
}

class ProviderInstance(private val items: List<String>) : Producer<String> {
    private var index = 0

    override fun next(): String {
        return items.elementAt(index++)
    }
}

fun producerDemo(strs: Producer<String>) {
    // Here we are taking a Producer<String> and treating it as a Producer<Any>
    // This works because the type parameter is not mutable from here
    // Being immutable means we cannot attempt to add a Integer to the Producer<Any> which is actually a Producer<String>
    // Hence, it is safe to treat Producer<String> as any base type we want
    // Therefore, T is a "covariant" type parameter
    // Therefore, Producer is a producer of T
    val objects: Producer<Any> = strs // This is OK, since T is an out-parameter
    println(objects.next())
    println(objects.next())
}

/* Consumer Example */

interface Consumer<in T> {
    fun add(item: T)
    // The following does not compile as this interface's T type parameter cannot be used as a producer
    // Meaning, no instance of T in any capacity can be given to a caller
    // Instances of T can only flow in, not out
    // Write-only
    // fun next(): T
}

class ConsumerInstance(private var items: MutableList<String>) : Consumer<String> {
    override fun add(item: String) {
        items.add(item)
    }
}

fun consumerDemo(strs: Consumer<String>) {
    // We cannot treat a Consumer<String> as a Consumer<Any> as that would allow us to add any types
    // We then would have a mix of Consumer<String> and Consumer<Int> which wouldn't be type safe
    // val items: Consumer<Any> = strs
    val items: Consumer<String> = strs
    items.add("Str 4")
    items.add("Str 5")
}

/* Entry Point */

fun main() {
    producerDemo(ProviderInstance(listOf("Str 1", "Str 2", "Str 3")))
    consumerDemo(ConsumerInstance(mutableListOf("Str 1", "Str 2", "Str 3")))
    consumerAdvancedDemo(AdvancedConsumerInstance(mutableListOf(TypeDerived(), TypeDerived(), TypeDerived())))
}

/* Consumer Advanced */

interface ConsumerAdvanced<in T> {
    fun add(item: T)
}
// Types to fill our generic type parameters
class TypeSuperDerived : TypeDerived() { }
open class TypeDerived : TypeBase() { }
class TypeBranchOff : TypeBase() { }
open class TypeBase { }

// Notice we take a TypeBase, not the Derived
class AdvancedConsumerInstance(private var items: MutableList<TypeBase>) : ConsumerAdvanced<TypeBase> {
    override fun add(item: TypeBase) {
        items.add(item)
    }
}

fun consumerAdvancedDemo(args: ConsumerAdvanced<TypeBase>) {
    // Here we are treating our ConsumerAdvanced<TypeBase> as a more derived version ConsumerAdvanced<TypeDerived>
    // This works because the "in" keyword associated with the ConsumerAdvanced's generic type parameter is forcing
    // the sub-typeing of instances of T to be either exactly the same type or derived
    // See examples below to really understand
    val items: ConsumerAdvanced<TypeDerived> = args
    // Cannot add a TypeBase because it is more generic than TypeDerived
    // items.add(TypeBase())
    // Can add the following because they are either the same level of generic or less
    items.add(TypeDerived())
    items.add(TypeSuperDerived())
    // Cannot add the following because TypeBranchOff is a child of TypeBase, not TypeDerived
    // items.add(TypeBranchOff())
    // Can add the following because TypeBranchOff is the same level of generic or less than TypeBase
    // We are adding to the args collection here!!!
    args.add(TypeBranchOff())
}

/**
 * So the "out" keyword when applied to a generic type parameter allows typing to flow upwards, derived instances being treated as base type instances
 * This flow is otherwise called covariance, so the generic type parameter can be said to be covariant
 * Example:
 *  Treating a List<String> as a List<Any> for reading
 * Remember this only works because the "out" keyword makes the type a producer, therefore no instances of the generic type parameter can flow into the type *(can only be returned)*
 * This flow is otherwise called contravariance, so the generic type parameter can be said to be contravariant
 *
 * So the "in" keyword when applied to a generic type parameter allows typing to flow downwards
 * Example:
 *  Treating a List<TypeBase> as a List<TypeDerived> for writing
 * Remember this only works because the "in" keyword makes the type a consumer, therefore no instances of the generic type parameter can flow out of the type *(can only be in parameters)*
 */