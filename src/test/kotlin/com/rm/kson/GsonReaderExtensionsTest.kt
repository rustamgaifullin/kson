package com.rm.kson

import com.google.gson.stream.JsonReader
import com.rm.kson.data.jsonWithArrays
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.StringReader

class GsonReaderExtensionsTest {
    @Test
    fun `should be able to find array in json`() {
        //given
        val json = jsonWithArrays()

        //when
        val jsonReader = JsonReader(StringReader(json.toString()))

        var counter = 0
        with(jsonReader) {
            start {
                nextObject {
                    findArray("array2") {
                        beginObject {
                            skipObject()
                            counter++
                        }
                    }
                }
            }
        }

        //expect
        assertEquals(1, counter)
    }
}