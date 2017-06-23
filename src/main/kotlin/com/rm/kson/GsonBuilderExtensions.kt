package com.rm.kson

import com.google.gson.JsonArray
import com.google.gson.JsonObject

fun json(init: JsonObject.() -> Unit): JsonObject {
    val jsonObject = JsonObject()
    jsonObject.init()
    return jsonObject
}

fun JsonObject.json(name: String, init: JsonObject.() -> Unit): JsonObject {
    val jsonObject = JsonObject()
    jsonObject.init()
    this.add(name, jsonObject)
    return jsonObject
}

fun JsonObject.array(name: String, init: JsonArray.() -> Unit): JsonArray {
    val jsonArray = JsonArray()
    jsonArray.init()
    this.add(name, jsonArray)
    return jsonArray
}

fun JsonArray.json(init: JsonObject.() -> Unit): JsonObject {
    val jsonObject = JsonObject()
    jsonObject.init()
    this.add(jsonObject)
    return jsonObject
}

fun JsonArray.array(init: JsonArray.() -> Unit): JsonArray {
    val jsonArray = JsonArray()
    jsonArray.init()
    this.add(jsonArray)
    return jsonArray
}

fun JsonObject.property(createProperty: () -> Property) {
    val property = createProperty.invoke()
    when (property) {
        is StringProperty -> this.addProperty(property.name, property.value)
        is NumberProperty -> this.addProperty(property.name, property.value)
        is BooleanProperty -> this.addProperty(property.name, property.value)
        is CharProperty -> this.addProperty(property.name, property.value)
    }
}

fun JsonObject.property(property: Property) = when (property) {
    is StringProperty -> this.addProperty(property.name, property.value)
    is NumberProperty -> this.addProperty(property.name, property.value)
    is BooleanProperty -> this.addProperty(property.name, property.value)
    is CharProperty -> this.addProperty(property.name, property.value)
}

infix fun String.value(value: String): Property = StringProperty(this, value)
infix fun String.value(value: Number): Property = NumberProperty(this, value)
infix fun String.value(value: Boolean): Property = BooleanProperty(this, value)
infix fun String.value(value: Char): Property = CharProperty(this, value)

sealed class Property
data class StringProperty(val name: String, val value: String) : Property()
data class NumberProperty(val name: String, val value: Number) : Property()
data class BooleanProperty(val name: String, val value: Boolean) : Property()
data class CharProperty(val name: String, val value: Char) : Property()

/**
 * Insert primitives in json array
 */
fun JsonArray.primitive(value: String) = add(value)
fun JsonArray.primitive(value: Number) = add(value)
fun JsonArray.primitive(value: Boolean) = add(value)
fun JsonArray.primitive(value: Char) = add(value)