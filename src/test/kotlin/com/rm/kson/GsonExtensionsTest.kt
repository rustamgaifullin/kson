package com.rm.kson

import org.junit.Assert.*
import org.junit.Test

class GsonExtensionsTest {

    @Test
    fun checkJsonBuilder() {
        val json = json {
            json("Object") {
                property("String" to "string")
                property("Number" to 123)
                property("Boolean" to true)
                property("Char" to '1')
            }

            array("Array") {
                json {
                    property("name" to "value")
                }
                json {
                    property("name2" to 321)
                }
                array {

                }
            }
        }

        val expectedJson = json.getAsJsonObject("Object")
        assertNotNull(expectedJson)
        assertEquals(expectedJson.get("String").asString, "string")
        assertEquals(expectedJson.get("Number").asInt, 123)
        assertEquals(expectedJson.get("Boolean").asBoolean, true)
        assertEquals(expectedJson.get("Char").asCharacter, '1')

        val expectedArray = json.getAsJsonArray("Array")
        assertTrue(expectedArray.size() == 3)
        assertEquals(expectedArray[0].asJsonObject.get("name").asString, "value")
        assertEquals(expectedArray[1].asJsonObject.get("name2").asInt, 321)
        assertTrue(expectedArray[2].asJsonArray.size() == 0)
    }
}