package com.rm.kson.data

import com.rm.kson.*


fun jsonWithArrays() = json {
    property("some property" value 1)
    json("object1") {
        array("array1") {
            primitive("primitive0")
            primitive("primitive1")
            primitive("primitive2")
        }
        array("array2") {
            json {
                property("property0" value "string0")
                property("property1" value "string1")
                property("property2" value "string1")
            }

        }
        array("array3") {
            primitive(0)
            primitive(true)
            primitive('a')
        }
    }
}