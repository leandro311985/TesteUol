package com.example.myUol.model

import android.os.Parcel
import android.os.Parcelable

class Points() : Parcelable {

    var Id = 0
    var Descricao = ""
    var Latitude = 0f
    var Longitude = 0f
    var distance = 0f

    constructor(parcel: Parcel) : this() {
        Id = parcel.readInt()
        Descricao = parcel.readString().toString()
        Latitude = parcel.readFloat()
        Longitude = parcel.readFloat()
        distance = parcel.readFloat()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(Id)
        parcel.writeString(Descricao)
        parcel.writeFloat(Latitude)
        parcel.writeFloat(Longitude)
        parcel.writeFloat(distance)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Points(Id=$Id, Descricao='$Descricao', Latitude=$Latitude, Longitude=$Longitude, distance=$distance)"
    }

    companion object CREATOR : Parcelable.Creator<Points> {
        override fun createFromParcel(parcel: Parcel): Points {
            return Points(parcel)
        }

        override fun newArray(size: Int): Array<Points?> {
            return arrayOfNulls(size)
        }
    }


}