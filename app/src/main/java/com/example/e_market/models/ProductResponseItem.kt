package com.example.e_market.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductResponseItem(
    @PrimaryKey
    val id: Int,
    val brand: String? = null,
    val createdAt: String? = null,
    val description: String? = null,
    val image: String? = null,
    val model: String? = null,
    val name: String? = null,
    val price: String? = null,
    var isFavorited:Boolean = false
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(brand)
        parcel.writeString(createdAt)
        parcel.writeString(description)
        parcel.writeString(image)
        parcel.writeString(model)
        parcel.writeString(name)
        parcel.writeString(price)
        parcel.writeByte(if (isFavorited) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductResponseItem> {
        override fun createFromParcel(parcel: Parcel): ProductResponseItem {
            return ProductResponseItem(parcel)
        }

        override fun newArray(size: Int): Array<ProductResponseItem?> {
            return arrayOfNulls(size)
        }
    }
}