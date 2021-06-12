package io.github.fuadreza.academy.data.source

import androidx.lifecycle.LiveData
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.data.CourseWithModule
import io.github.fuadreza.academy.data.ModuleEntity
import io.github.fuadreza.academy.data.vo.Resource

interface AcademyDataSource {

    fun getAllCourses(): LiveData<Resource<List<CourseEntity>>>

    fun getCourseWithModules(courseId: String): LiveData<Resource<CourseWithModule>>

    fun getAllModulesByCourse(courseId: String): LiveData<Resource<List<ModuleEntity>>>

    fun getContent(moduleId: String): LiveData<Resource<ModuleEntity>>

    fun getBookmarkedCourses(): LiveData<List<CourseEntity>>

    fun setCourseBookmark(course: CourseEntity, state: Boolean)
    fun setReadModule(module: ModuleEntity)
}