package com.rm.kson

import com.google.gson.stream.JsonReader
import com.rm.kson.data.jsonWithArrays
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.StringReader

class GsonReaderExtensionsTest {

    @Test
    fun `should be able to read json`() {
        //given
        val json = jsonWithArrays()

        //when
        val jsonReader = JsonReader(StringReader(json.toString()))

        with (jsonReader) {
            start {
                assertEquals(1, nextIntValue())
                assertEquals(1L, nextLongValue())
                assertEquals(1.0, nextDoubleValue(), 1.0)
                beginObject {
                    beginArray {
                        assertEquals("primitive0", nextStringPrimitive())
                        assertEquals("primitive1", nextStringPrimitive())
                        assertEquals("primitive2", nextStringPrimitive())
                    }
                    beginArray {
                        beginObject {
                            assertEquals("string0", nextStringValue())
                            assertEquals("string1", nextStringValue())
                            assertEquals("string2", nextStringValue())
                        }
                    }
                    beginArray {
                        assertEquals(0, nextIntPrimitive())
                        assertEquals(true, nextBooleanPrimitive())
                        assertEquals("c", nextStringPrimitive())
                    }
                }

            }
        }
    }

    @Test
    fun `should be able to find array in json`() {
        //given
        val json = jsonWithArrays()

        //when
        val jsonReader = JsonReader(StringReader(json.toString()))

        var counter = 0
        with(jsonReader) {
            start {
                findArray("array2") {
                    beginObject {
                        skipObject()
                        counter++
                    }
                }
            }
        }

        //expect
        assertEquals(1, counter)
    }
}