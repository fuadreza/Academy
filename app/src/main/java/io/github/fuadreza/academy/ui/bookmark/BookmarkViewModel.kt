package io.github.fuadreza.academy.ui.bookmark

import androidx.lifecycle.ViewModel
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.utils.DataDummy

class BookmarkViewModel : ViewModel() {

    fun getBookmarks(): List<CourseEntity> = DataDummy.generateDummyCourses()
}