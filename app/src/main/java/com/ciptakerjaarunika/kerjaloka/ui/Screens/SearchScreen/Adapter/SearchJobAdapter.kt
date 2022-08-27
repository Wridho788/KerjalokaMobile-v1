package com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.ciptakerjaarunika.kerjaloka.R
import com.ciptakerjaarunika.kerjaloka.ui.Screens.SearchScreen.Model.general_search_model

class SearchJobAdapter(private val general_search_list: List<general_search_model>) :
    RecyclerView.Adapter<SearchJobAdapter.ViewHolder>() {

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            var keyword: SearchView

            init {
                keyword = itemView.findViewById(R.id.search_bar)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}