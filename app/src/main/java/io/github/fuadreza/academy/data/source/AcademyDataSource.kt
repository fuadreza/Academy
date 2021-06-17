package io.github.fuadreza.academy.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.data.CourseWithModule
import io.github.fuadreza.academy.data.ModuleEntity
import io.github.fuadreza.academy.data.vo.Resource

interface AcademyDataSource {

    fun getAllCourses(): LiveData<Resource<PagedList<CourseEntity>>>

    fun getCourseWithModules(courseId: String): LiveData<Resource<CourseWithModule>>

    fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>>

    fun getContent(moduleId: String): LiveData<Resource<ModuleEntity>>

    fun getBookmarkedCourses(): LiveData<PagedList<CourseEntity>>

    fun setCourseBookmark(course: CourseEntity, state: Boolean)
    fun setReadModule(module: ModuleEntity)
}