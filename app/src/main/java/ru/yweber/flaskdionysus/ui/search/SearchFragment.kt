package ru.yweber.flaskdionysus.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.fragment_search.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.core.adapter.search.SearchDelegateAdapter
import ru.yweber.flaskdionysus.core.decorator.PaddingItemDecorator
import ru.yweber.flaskdionysus.system.hideKeyboard
import ru.yweber.flaskdionysus.system.showKeyboard
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.search.state.SearchState
import toothpick.Scope
import toothpick.ktp.delegate.inject

/**
 * Created on 14.05.2020
 * @author YWeber */

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    private val viewModel by inject<SearchViewModel>()

    private val adapter by lazy {
        SearchDelegateAdapter().createAdapter {
            viewModel.clickComponent(it)
            textFieldSearch.hideKeyboard()
        }
    }

    override fun installModule(scope: Scope) {
        super.installModule(scope)
        scope.installViewModel<SearchViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(viewModel.state, ::renderState)
        rvSearch.adapter = adapter
        rvSearch.addItemDecoration(PaddingItemDecorator())
        textFieldSearch.addTextChangedListener {
            viewModel.search(it?.toString())
        }
    }

    private fun renderState(state: SearchState) {
        if (state.init) {
            textFieldSearch.showKeyboard()
        } else {
            textFieldSearch.hideKeyboard()
        }
        adapter.submitList(state.searchItems)
        rvSearch.isVisible = !state.emptySearch
        holderEmpty.isVisible = state.emptySearch

    }

    override fun onDestroyView() {
        rvSearch.adapter = null
        super.onDestroyView()
    }

    override fun backPressed(): Boolean {
        viewModel.backTo()
        return true
    }
}