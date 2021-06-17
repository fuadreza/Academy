package io.github.fuadreza.academy.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.data.source.AcademyRepository
import io.github.fuadreza.academy.utils.DataDummy

class BookmarkViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    fun getBookmarks(): LiveData<PagedList<CourseEntity>> = academyRepository.getBookmarkedCourses()

    fun setBookmark(courseEntity: CourseEntity) {
        val newState = !courseEntity.bookmarked
        academyRepository.setCourseBookmark(courseEntity, newState)
    }
}