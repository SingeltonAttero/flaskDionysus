package ru.yweber.flaskdionysus.ui.filter.chooser

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.transition.Slide
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.dialog_chooser.*
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseDialog
import ru.yweber.flaskdionysus.core.adapter.filter.FilterChooserDelegateAdapter
import ru.yweber.flaskdionysus.model.entity.ItemTypeFilter
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.system.toast
import ru.yweber.flaskdionysus.ui.filter.chooser.state.ChooserState
import toothpick.Scope
import toothpick.ktp.binding.module
import toothpick.ktp.delegate.inject

/**
 * Created on 16.04.2020
 * @author YWeber */

class ChooserDialog : BaseDialog(R.layout.dialog_chooser) {

    private val viewModel by inject<ChooserViewModel>()
    private val adapter by lazy {
        FilterChooserDelegateAdapter().createAdapter(viewModel::selectComponent)
    }

    override fun installModule(scope: Scope) {
        scope.installModules(module {
            val type = arguments?.get(TYPE_FILTER_EXTRA) as ItemTypeFilter
            bind(ItemTypeFilter::class.java).toInstance(type)
        })
        scope.installViewModel<ChooserViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvFilterIngredient.adapter = adapter
        textFieldSearch.addTextChangedListener {
            viewModel.searchComponent(it?.toString())
        }
        btnDone.setOnClickListener {
            viewModel.approveFilter()
        }
        subscribe(viewModel.state, ::renderState)
    }

    private fun renderState(state: ChooserState) {
        adapter.items = state.items
        tvSearchHint.isVisible = state.searchEmpty
        tvAdvice.text = state.searchAdvice
        tvSearchHint.text = getString(R.string.empty_search_component, state.search)
        if (state.isInitWindows) {
            textFieldSearch.isVisible = state.showSearch
            if (state.items.isEmpty()) {
                toast(getString(R.string.filter_empty))
                dismiss()
            }
            TransitionManager.beginDelayedTransition(rootContainerFilter, Slide(Gravity.BOTTOM))
            rootContainerFilter.isInvisible = false
        }
    }

    companion object {
        private const val TYPE_FILTER_EXTRA = "filter type extra"
        fun newInstance(type: ItemTypeFilter) = with(ChooserDialog()) {
            val bundle = Bundle()
            bundle.putSerializable(TYPE_FILTER_EXTRA, type)
            arguments = bundle
            this
        }
    }

}