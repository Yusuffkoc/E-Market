package com.example.e_market.repository

import com.example.e_market.models.FilterEnum

interface FilterCommunication {
    fun onFilterEnumSelected(value: FilterEnum)
}