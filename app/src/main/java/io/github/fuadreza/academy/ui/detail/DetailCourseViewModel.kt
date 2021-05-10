package io.github.fuadreza.academy.ui.detail

import androidx.lifecycle.ViewModel
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.data.ModuleEntity
import io.github.fuadreza.academy.data.source.AcademyRepository
import io.github.fuadreza.academy.utils.DataDummy

class DetailCourseViewModel(private var mAcademyRepository: AcademyRepository) : ViewModel() {
    private lateinit var courseId: String

    fun setSelectedCourse(courseId: String) {
        this.courseId = courseId
    }

    fun getCourse(): CourseEntity = mAcademyRepository.getCourseWithModules(courseId)

    fun getModules(): List<ModuleEntity> = mAcademyRepository.getAllModulesByCourse(courseId)
}