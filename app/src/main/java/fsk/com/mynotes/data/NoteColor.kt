package fsk.com.mynotes.data

import com.google.gson.annotations.SerializedName

enum class NoteColor {

    @SerializedName("yellow")
    YELLOW,
    @SerializedName("blue")
    BLUE,
    @SerializedName("green")
    GREEN,
    @SerializedName("pink")
    PINK,
    @SerializedName("grey")
    GREY,
    @SerializedName("purple")
    PURPLE;
}