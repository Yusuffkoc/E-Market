package com.example.e_market.test.model

import com.example.e_market.models.MenuEnum
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MenuEnumTest {

    @Test
    fun enumValues_shouldReturnCorrectValues() {
        val home = MenuEnum.HOME
        val shoppingBox = MenuEnum.SHOPPINBOX
        val favourites = MenuEnum.FAVOURITES
        val profile = MenuEnum.PROFILE

        assertEquals(0, home.id)
        assertEquals(1, shoppingBox.id)
        assertEquals(2, favourites.id)
        assertEquals(3, profile.id)
    }

    @Test
    fun enumValueOf_shouldReturnCorrectEnum() {
        val home = MenuEnum.valueOf("HOME")
        val shoppingBox = MenuEnum.valueOf("SHOPPINBOX")
        val favourites = MenuEnum.valueOf("FAVOURITES")
        val profile = MenuEnum.valueOf("PROFILE")

        assertEquals(MenuEnum.HOME, home)
        assertEquals(MenuEnum.SHOPPINBOX, shoppingBox)
        assertEquals(MenuEnum.FAVOURITES, favourites)
        assertEquals(MenuEnum.PROFILE, profile)
    }
}