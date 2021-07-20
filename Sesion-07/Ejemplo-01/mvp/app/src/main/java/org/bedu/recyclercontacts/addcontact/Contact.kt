package org.bedu.recyclercontacts.addcontact

import android.os.Parcel
import android.os.Parcelable
import org.bedu.recyclercontacts.R

data class Contact (
    var name: String = "",
    var status: String = "disponible",
    var phone: String = "",
    var idImage: Int = R.drawable.unknown
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(status)
        parcel.writeString(phone)
        parcel.writeInt(idImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}