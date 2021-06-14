package io.github.fuadreza.academy.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.github.fuadreza.academy.data.source.remote.RemoteDataSource
import io.github.fuadreza.academy.data.source.remote.response.ContentResponse
import io.github.fuadreza.academy.data.source.remote.response.CourseResponse
import io.github.fuadreza.academy.data.source.remote.response.ModuleResponse
import io.github.fuadreza.academy.utils.DataDummy
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.doAnswer
import io.github.fuadreza.academy.data.source.local.LocalDataSource
import io.github.fuadreza.academy.utils.AppExecutors
import io.github.fuadreza.academy.utils.LiveDataTestUtil
import org.junit.Rule

class AcademyRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = Mockito.mock(RemoteDataSource::class.java)
    private val local = Mockito.mock(LocalDataSource::class.java)
    private val appExecutors = Mockito.mock(AppExecutors::class.java)
    private val academyRepository = FakeAcademyRepository(remote, local, appExecutors)

    private val courseResponses = DataDummy.generateRemoteDummyCourses()
    private val courseId = courseResponses[0].id
    private val moduleResponses = DataDummy.generateRemoteDummyModules(courseId)
    private val moduleId = moduleResponses[0].moduleId
    private val content = DataDummy.generateRemoteDummyContent(moduleId)

//    @Test
//    fun getAllCourses() {
//        doAnswer { invocation ->
//            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
//                .onAllCoursesReceived(courseResponses)
//            null
//        }.`when`(remote).getAllCourses(any())
//        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllCourses())
//        verify(remote).getAllCourses(any())
//        assertNotNull(courseEntities)
//        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
//    }

    @Test
    fun getAllCourses() {
        val dummyCourses = MutableLiveData<List<CourseEntity>>()
        dummyCourses.value = DataDummy.generateDummyCourses()
        `when`(local.getAllCourses()).thenReturn(dummyCourses)

        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllCourses())
        verify(local).getAllCourses()
        assertNotNull(courseEntities.data)
        assertEquals(courseResponses.size.toLong(), courseEntities.data?.size?.toLong())
    }

//    @Test
//    fun getAllModulesByCourse() {
//        doAnswer { invocation ->
//            (invocation.arguments[1] as RemoteDataSource.LoadModulesCallback)
//                .onAllModulesReceived(moduleResponses)
//            null
//        }.`when`(remote).getModules(eq(courseId), any())
//
//        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId))
//
//        verify(remote).getModules(eq(courseId), any())
//        assertNotNull(courseEntities)
//        assertEquals(moduleResponses.size.toLong(), courseEntities.size.toLong())
//    }

    @Test
    fun getAllModulesByCourse() {
        val dummyModules = MutableLiveData<List<ModuleEntity>>()
        dummyModules.value = DataDummy.generateDummyModules(courseId)
        `when`(local.getAllModulesByCourse(courseId)).thenReturn(dummyModules)

        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getAllModulesByCourse(courseId))
        verify(local).getAllModulesByCourse(courseId)
        assertNotNull(courseEntities.data)
        assertEquals(moduleResponses.size.toLong(), courseEntities.data?.size?.toLong())
    }

//    @Test
//    fun getBookmarkedCourses() {
//        doAnswer { invocation ->
//            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
//                .onAllCoursesReceived(courseResponses)
//            null
//        }.`when`(remote).getAllCourses(any())
//
//        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourses())
//
//        verify(remote).getAllCourses(any())
//        assertNotNull(courseEntities)
//        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
//    }

    @Test
    fun getBookmarkedCourses() {
        val dummyCourses = MutableLiveData<List<CourseEntity>>()
        dummyCourses.value = DataDummy.generateDummyCourses()
        `when`(local.getBookmarkedCourses()).thenReturn(dummyCourses)

        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getBookmarkedCourses())
        verify(local).getBookmarkedCourses()
        assertNotNull(courseEntities)
        assertEquals(courseResponses.size.toLong(), courseEntities.size.toLong())
    }

//    @Test
//    fun getContent() {
//        doAnswer { invocation ->
//            (invocation.arguments[1] as RemoteDataSource.LoadModulesCallback)
//                .onAllModulesReceived(moduleResponses)
//            null
//        }.`when`(remote).getModules(eq(courseId), any())
//        doAnswer { invocation ->
//            (invocation.arguments[1] as RemoteDataSource.LoadContentCallback)
//                .onContentReceived(content)
//            null
//        }.`when`(remote).getContent(eq(moduleId), any())
//
//        val courseEntitiesContent = LiveDataTestUtil.getValue(academyRepository.getContent(courseId, moduleId))
//
//        verify(remote)
//            .getModules(eq(courseId), any())
//
//        verify(remote)
//            .getContent(eq(moduleId), any())
//
//        assertNotNull(courseEntitiesContent)
//        assertNotNull(courseEntitiesContent.contentEntity)
//        assertNotNull(courseEntitiesContent.contentEntity?.content)
//        assertEquals(content.content, courseEntitiesContent.contentEntity?.content)
//    }

    @Test
    fun getContent() {
        val dummyEntity = MutableLiveData<ModuleEntity>()
        dummyEntity.value = DataDummy.generateDummyModuleWithContent(moduleId)
        `when`(local.getModuleWithContent(courseId)).thenReturn(dummyEntity)

        val courseEntitiesContent = LiveDataTestUtil.getValue(academyRepository.getContent(courseId))
        verify(local).getModuleWithContent(courseId)
        assertNotNull(courseEntitiesContent)
        assertNotNull(courseEntitiesContent.data?.contentEntity)
        assertNotNull(courseEntitiesContent.data?.contentEntity?.content)
        assertEquals(content.content, courseEntitiesContent.data?.contentEntity?.content)
    }

//    @Test
//    fun getCourseWithModules() {
//        doAnswer { invocation ->
//            (invocation.arguments[0] as RemoteDataSource.LoadCoursesCallback)
//                .onAllCoursesReceived(courseResponses)
//            null
//        }.`when`(remote).getAllCourses(any())
//
//        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId))
//
//        verify(remote).getAllCourses(any())
//        assertNotNull(courseEntities)
//        assertEquals(courseResponses[0].title, courseEntities.title)
//    }

    @Test
    fun getCourseWithModules() {
        val dummyEntity = MutableLiveData<CourseWithModule>()
        dummyEntity.value = DataDummy.generateDummyCourseWithModules(DataDummy.generateDummyCourses()[0], false)
        `when`(local.getCourseWithModules(courseId)).thenReturn(dummyEntity)
        val courseEntities = LiveDataTestUtil.getValue(academyRepository.getCourseWithModules(courseId))
        verify(local).getCourseWithModules(courseId)
        assertNotNull(courseEntities.data)
        assertNotNull(courseEntities.data?.mCourse?.title)
        assertEquals(courseResponses[0].title, courseEntities.data?.mCourse?.title)
    }
}