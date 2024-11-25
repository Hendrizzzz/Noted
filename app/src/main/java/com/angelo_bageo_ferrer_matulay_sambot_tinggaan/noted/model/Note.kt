package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.model

import java.time.LocalDate

class Note {
    private var title: String
    private var content: String
    private var date: LocalDate
    private val author : User
    private var isDeleted : Boolean

    constructor(title : String, content : String, date : LocalDate, author : User) {
        this.title = title
        this.content = content
        this.date = date
        this.author = author
        this.isDeleted = false
    }

    fun getTitle() : String {
        return this.title
    }

    fun getContent() : String {
        return this.content
    }

    fun getDate() : LocalDate {
        return this.date
    }

    fun getAuthor() : User {
        return this.author
    }

    fun isDeleted() : Boolean {
        return this.isDeleted
    }

    fun setTitle(title : String) {
        this.title = title
    }

    fun setContent(content : String) {
        this.content = content
    }

    fun setDate(date : LocalDate) {
        this.date = date
    }

    fun setDeleted(isDeleted : Boolean) {
        this.isDeleted = isDeleted
    }
}