package io.github.fuadreza.academy.ui.reader

import androidx.lifecycle.ViewModel
import io.github.fuadreza.academy.data.ContentEntity
import io.github.fuadreza.academy.data.ModuleEntity
import io.github.fuadreza.academy.data.source.AcademyRepository
import io.github.fuadreza.academy.utils.DataDummy

class CourseReaderViewModel(private val academyRepository: AcademyRepository) : ViewModel() {

    private lateinit var courseId: String
    private lateinit var moduleId: String

    fun setSelectedCourse(courseId: String) {
        this.courseId = courseId
    }

    fun setSelectedModule(moduleId: String) {
        this.moduleId = moduleId
    }

    fun getModules(): ArrayList<ModuleEntity> = academyRepository.getAllModulesByCourse(courseId)

    fun getSelectedModule(): ModuleEntity = academyRepository.getContent(courseId, moduleId)
}