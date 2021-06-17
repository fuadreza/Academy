package io.github.fuadreza.academy.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.github.fuadreza.academy.R
import io.github.fuadreza.academy.data.CourseEntity
import io.github.fuadreza.academy.databinding.FragmentBookmarkBinding
import io.github.fuadreza.academy.viewmodel.ViewModelFactory

class BookmarkFragment : Fragment(), BookmarkFragmentCallback {

    lateinit var fragmentBookmarkBinding: FragmentBookmarkBinding

    private lateinit var viewModel: BookmarkViewModel
    private lateinit var bookmarkAdapter: BookmarkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        fragmentBookmarkBinding = FragmentBookmarkBinding.inflate(inflater, container, false)
        return fragmentBookmarkBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(fragmentBookmarkBinding?.rvBookmark)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireContext())
            viewModel = ViewModelProvider(
                this,
                factory
            )[BookmarkViewModel::class.java]

            bookmarkAdapter = BookmarkAdapter(this)

            fragmentBookmarkBinding.progressBar.visibility = View.VISIBLE
            viewModel.getBookmarks().observe(viewLifecycleOwner, { courses ->
                Log.d("BOOKMARK", "COURSES: $courses")
                fragmentBookmarkBinding.progressBar.visibility = View.GONE
                bookmarkAdapter.submitList(courses)
            })

            with(fragmentBookmarkBinding?.rvBookmark) {
                this?.layoutManager = LinearLayoutManager(context)
                this?.setHasFixedSize(true)
                this?.adapter = bookmarkAdapter
            }

        }
    }

    override fun onShareClick(course: CourseEntity) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder
                .from(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi ini sekarang.")
                .setText(resources.getString(R.string.share_text, course.title))
                .startChooser()
        }
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean = true
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val courseEntity = bookmarkAdapter.getSwipedData(swipedPosition)
                courseEntity?.let { viewModel.setBookmark(it) }
                val snackbar = Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { v ->
                    courseEntity?.let { viewModel.setBookmark(it) }
                }
                snackbar.show()
            }
        }
    })
}