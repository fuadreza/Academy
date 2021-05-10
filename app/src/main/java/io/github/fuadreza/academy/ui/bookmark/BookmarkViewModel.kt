package io.github.fuadreza.academy.ui.bookmark

import androidx.lifecycle.ViewModel
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.data.source.AcademyRepository
import io.github.fuadreza.academy.utils.DataDummy

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getBookmarks(): List<CourseEntity> = academyRepository.getBookmarkedCourses()
}