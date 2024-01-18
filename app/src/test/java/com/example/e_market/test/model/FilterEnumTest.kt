package com.example.e_market.test.model

import com.example.e_market.models.FilterEnum
import junit.framework.TestCase.assertEquals
import org.junit.Test

class FilterEnumTest {

    @Test
    fun enumValues_shouldReturnCorrectValues() {
        val oldToNew = FilterEnum.OLDTONEW
        val newToOld = FilterEnum.NEWTOOLD
        val priceHighToLow = FilterEnum.PRICEHIGHTTOLOW
        val priceLowToHigh = FilterEnum.PRICELOWTOHIGH

        assertEquals(0, oldToNew.id)
        assertEquals(1, newToOld.id)
        assertEquals(2, priceHighToLow.id)
        assertEquals(3, priceLowToHigh.id)
    }

    @Test
    fun enumValueOf_shouldReturnCorrectEnum() {
        val oldToNew = FilterEnum.valueOf("OLDTONEW")
        val newToOld = FilterEnum.valueOf("NEWTOOLD")
        val priceHighToLow = FilterEnum.valueOf("PRICEHIGHTTOLOW")
        val priceLowToHigh = FilterEnum.valueOf("PRICELOWTOHIGH")

        assertEquals(FilterEnum.OLDTONEW, oldToNew)
        assertEquals(FilterEnum.NEWTOOLD, newToOld)
        assertEquals(FilterEnum.PRICEHIGHTTOLOW, priceHighToLow)
        assertEquals(FilterEnum.PRICELOWTOHIGH, priceLowToHigh)
    }
}