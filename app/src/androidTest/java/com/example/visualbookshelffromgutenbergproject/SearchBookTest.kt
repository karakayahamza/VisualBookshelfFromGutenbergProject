package com.example.visualbookshelffromgutenbergproject

import com.example.visualbookshelffromgutenbergproject.data.models.BookModel
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchBookTest {
    @Test
    fun testBookModel() {
        // Create a mock BookModel for testing
        val bookModel = createMockBookModel()

        // Verify the values of the BookModel instance
        assertEquals(2, bookModel.count)
        assertEquals("nextPage", bookModel.next)
        assertEquals("prevPage", bookModel.previous)

        // Verify the values of the first result
        val result1 = bookModel.results[0]
        assertEquals(1, result1.id)
        assertEquals("Title1", result1.title)
        assertEquals("Author1", result1.authors?.get(0)?.name)
        assertEquals(true, result1.copyright)
        assertEquals("Type1", result1.media_type)

        // Verify the values of the second result
        val result2 = bookModel.results[1]
        assertEquals(2, result2.id)
        assertEquals("Title2", result2.title)
        assertEquals("Author2", result2.authors?.get(0)?.name)
        assertEquals(false, result2.copyright)
        assertEquals("Type2", result2.media_type)
    }

    private fun createMockBookModel(): BookModel {
        // Create a mock BookModel for testing
        val author1 = BookModel.Author(name = "Author1")
        val author2 = BookModel.Author(name = "Author2")

        val formats1 = BookModel.Formats(
            application_epub_zip = "Epub1",
            application_x_mobipocket_ebook = "Mobipocket1",
            image_jpeg = "Image1",
            application_rdf_xml = "RDF1",
            textPlainCharsetusAscii = "Text1",
            application_octet_stream = "Octet1",
            text_html = "HTML1",
            text_html_charsetiso_8859_1 = "HTMLCharset1",
            audio_mpeg = "Audio1",
            text_plain = "Plain1",
            text_plain_charset_utf8 = "UTF81"
        )

        val formats2 = BookModel.Formats(
            application_epub_zip = "Epub2",
            application_x_mobipocket_ebook = "Mobipocket2",
            image_jpeg = "Image2",
            application_rdf_xml = "RDF2",
            textPlainCharsetusAscii = "Text2",
            application_octet_stream = "Octet2",
            text_html = "HTML2",
            text_html_charsetiso_8859_1 = "HTMLCharset2",
            audio_mpeg = "Audio2",
            text_plain = "Plain2",
            text_plain_charset_utf8 = "UTF82"
        )

        val result1 = BookModel.Result(
            id = 1,
            title = "Title1",
            authors = arrayListOf(author1),
            translators = null,
            subjects = null,
            bookshelves = null,
            languages = null,
            copyright = true,
            media_type = "Type1",
            formats = formats1,
            download_count = 100
        )

        val result2 = BookModel.Result(
            id = 2,
            title = "Title2",
            authors = arrayListOf(author2),
            translators = null,
            subjects = null,
            bookshelves = null,
            languages = null,
            copyright = false,
            media_type = "Type2",
            formats = formats2,
            download_count = 200
        )

        return BookModel(count = 2, next = "nextPage", previous = "prevPage", results = arrayListOf(result1, result2))
    }
}
