package io.github.fuadreza.academy.data.source

import androidx.lifecycle.LiveData
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.data.ModuleEntity

interface AcademyDataSource {

    fun getAllCourses(): LiveData<ArrayList<CourseEntity>>

    fun getBookmarkedCourses(): LiveData<ArrayList<CourseEntity>>

    fun getCourseWithModules(courseId: String): LiveData<CourseEntity>

    fun getAllModulesByCourse(courseId: String): LiveData<ArrayList<ModuleEntity>>

    fun getContent(courseId: String, moduleId: String): LiveData<ModuleEntity>
}