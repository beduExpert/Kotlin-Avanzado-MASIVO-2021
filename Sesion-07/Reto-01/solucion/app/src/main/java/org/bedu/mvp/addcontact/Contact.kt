package org.bedu.mvp.addcontact

import android.os.Parcel
import android.os.Parcelable
import org.bedu.mvp.R

data class Contact(
    var name: String? = "",
    var status: String? = "disponible",
    var phone: String? = "",
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

        //generamos datos dummy con este método
        fun initContacts() = mutableListOf(
            Contact(
                "Pablo Perez",
                "disponible",
                "5535576823",
                R.drawable.unknown
            ),
            Contact(
                "Juan Magaña",
                "on smash",
                "553552432",
                R.drawable.unknown
            ),
            Contact(
                "Leticia Pereda",
                "disponible",
                "5553454363",
                R.drawable.unknown
            ),
            Contact(
                "Manuel Lozano",
                "livin' la vida loca",
                "9613245432",
                R.drawable.unknown
            ),
            Contact(
                "Ricardo Belmonte",
                "disponible",
                "6332448739",
                R.drawable.unknown
            ),
            Contact(
                "Rosalina",
                "lookin' to the stars",
                "7546492750",
                R.drawable.unknown
            ),
            Contact(
                "Thalía Fernandez",
                "no fear",
                "5587294655",
                R.drawable.unknown
            ),
            Contact(
                "Sebastián Cárdenas",
                "no molestar",
                "4475655578",
                R.drawable.unknown
            )
        )
    }
}