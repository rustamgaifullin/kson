package com.rm.kson

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken


inline fun JsonReader.start(body: () -> Unit) {
    beginObject {
        while (hasNext()) {
            body()
        }
    }
}

inline fun JsonReader.beginObject(body: () -> Unit) {
    beginObject()
    body()
    endObject()
}

inline fun JsonReader.beginArray(body: () -> Unit) {
    beginArray()
    body()
    endArray()
}

inline fun JsonReader.nextObject(body: () -> Unit) {
    skipName()

    if (peek() == JsonToken.BEGIN_OBJECT) {
        beginObject(body)
    } else {
        skipValue()
    }
}

fun JsonReader.skipName() {
    if (peek() == JsonToken.NAME) {
        nextName()
    }
}

fun JsonReader.skipObject() {
    while (peek() != JsonToken.END_OBJECT) {
        skipValue()
    }
}

fun JsonReader.skipArray() {
    while (peek() != JsonToken.END_ARRAY) {
        skipValue()
    }
}

fun JsonReader.findArray(value: String, found: () -> Unit) {
    while (hasNext()) {
        var fieldName: String

        if (peek() == JsonToken.NAME) {
            fieldName = nextName()

            if (fieldName == value) {
                beginArray {
                    found()
                }
            }
        } else if (peek() == JsonToken.BEGIN_OBJECT) {
            beginObject {
                findArray(value, found)
            }
        } else if (peek() == JsonToken.BEGIN_ARRAY) {
            beginArray {
                findArray(value, found)
            }
        } else {
            skipValue()
        }
    }
}

fun JsonReader.whileHasNext(body: () -> Unit) {
    while (hasNext()) {
        body()
    }
}

fun JsonReader.nextStringValue(): String {
    nextName()
    return nextString()
}

fun JsonReader.nextIntValue(): Int {
    nextName()
    return nextInt()
}

fun JsonReader.nextLongValue(): Long {
    nextName()
    return nextLong()
}

fun JsonReader.nextDoubleValue(): Double {
    nextName()
    return nextDouble()
}

fun JsonReader.nextBooleanValue(): Boolean {
    nextName()
    return nextBoolean()
}

fun JsonReader.nextStringPrimitive() = nextString()
fun JsonReader.nextIntPrimitive() = nextInt()
fun JsonReader.nextBooleanPrimitive() = nextBoolean()