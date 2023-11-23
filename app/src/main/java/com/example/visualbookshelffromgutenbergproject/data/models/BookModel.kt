package com.example.visualbookshelffromgutenbergproject.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookModel(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: ArrayList<Result>
): Parcelable {

    @Parcelize
    data class Author(
        val name: String?,
        val birth_year: Int?,
        val death_year: Int?
    ): Parcelable

    @Parcelize
    data class Formats(
        @SerializedName("application/epub+zip")
        val application_epub_zip: String?,
        @SerializedName("application/x-mobipocket-ebook")
        val application_x_mobipocket_ebook: String?,
        @SerializedName("image/jpeg")
        val image_jpeg: String?,
        @SerializedName("application/rdf+xml")
        val application_rdf_xml: String?,
        @SerializedName("text/plain; charset=us-ascii")
        val text_plain_charsetus_ascii: String?,
        @SerializedName("application/octet-stream")
        val application_octet_stream: String?,
        @SerializedName("text/html")
        val text_html: String?,
        @SerializedName("text/plain; charset=iso-8859-1")
        val text_html_charsetiso_8859_1: String?,
        @SerializedName("audio/mpeg")
        val audio_mpeg: String?,
        @SerializedName("text/plain")
        val text_plain:String?,
        @SerializedName("text/plain; charset=utf-8")
        val text_plain_charset_utf8 : String?,

    ): Parcelable

    @Parcelize
    data class Result(
        val id: Int?,
        val title: String?,
        val authors: ArrayList<Author>?,
        val translators: ArrayList<Translator>?,
        val subjects: ArrayList<String>?,
        val bookshelves: ArrayList<String>?,
        val languages: ArrayList<String>?,
        val copyright: Boolean?,
        val media_type: String?,
        val formats: Formats?,
        val download_count: Int?
    ): Parcelable

    @Parcelize
    class Translator (
        var name: String? = null,
        var birth_year: Int = 0,
        var death_year: Int? = null
    ):Parcelable
}
