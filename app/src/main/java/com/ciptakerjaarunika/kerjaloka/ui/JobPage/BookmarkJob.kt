package com.ciptakerjaarunika.kerjaloka.ui.JobPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Adapter.RecommendationJobAdapter
import com.ciptakerjaarunika.kerjaloka.ui.HomePage.Model.rJobModel
import com.ciptakerjaarunika.kerjaloka.ui.JobPage.Adapter.BookmarkedJobAdapter
import com.google.android.material.button.MaterialButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarkJob.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarkJob : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<BookmarkedJobAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bookmark_job, container, false)
    }

    companion object {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = ArrayList<rJobModel>()
        val rJob1 = rJobModel(
            1,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val rJob2 = rJobModel(
            2,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val rJob3 = rJobModel(
            3,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val rJob4 = rJobModel(
            4,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )
        val rJob5 = rJobModel(
            5,
            "Software Engineer",
            "PT. KerjaLoka",
            "Jakarta",
            "satu jam lalu",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/Google_logo_%282013-2015%29.svg/2560px-Google_logo_%282013-2015%29.svg.png"
        )

        list.add(rJob1)
        list.add(rJob2)
        list.add(rJob3)
        list.add(rJob4)
        list.add(rJob5)
        val recyclerView = view.findViewById<RecyclerView>(R.id.bookmaredJoblist)
        layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = BookmarkedJobAdapter(list)
        recyclerView.adapter = adapter
    }
}