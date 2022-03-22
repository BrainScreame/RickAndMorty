package com.osenov.rickandmorty.ui.list_characters

import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.osenov.rickandmorty.R
import com.osenov.rickandmorty.databinding.BottomSheetFilterFragmentBinding
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.osenov.rickandmorty.data.model.FilterCharacter
import com.osenov.rickandmorty.util.appComponent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class BottomFilterFragment : BottomSheetDialogFragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        BottomSheetFilterFragmentBinding.inflate(layoutInflater)
    }

    private val viewModel: CharactersListViewModel by activityViewModels() {
        requireContext().appComponent.viewModelsFactory()
    }

    private val statuses by lazy(LazyThreadSafetyMode.NONE) {
        resources.getStringArray(R.array.status)
    }

    private val genders by lazy(LazyThreadSafetyMode.NONE) {
        resources.getStringArray(R.array.gender)
    }

    private val filterCharacter = FilterCharacter("", "", "")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener { dialog ->
            val coordinator = (dialog as BottomSheetDialog)
                .findViewById<CoordinatorLayout>(com.google.android.material.R.id.coordinator)
            val containerLayout =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.container)

            val buttons =
                bottomSheetDialog.layoutInflater.inflate(R.layout.button_bottom_sheet, null)

            buttons.layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM
            }
            containerLayout?.addView(buttons)

            buttons.post {
                (coordinator?.layoutParams as ViewGroup.MarginLayoutParams).apply {
                    buttons.measure(
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    )
                    this.bottomMargin = buttons.measuredHeight
                    containerLayout?.requestLayout()
                }
            }

            buttons.findViewById<Button>(R.id.button_bottom_sheet).setOnClickListener {
                viewModel.setFilter(filterCharacter)
                dialog.dismiss()
            }

        }
        return bottomSheetDialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val statusAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, statuses)
        binding.autoCompleteTextViewStatusCharacter.setAdapter(statusAdapter)

        val genderAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, genders)
        binding.autoCompleteTextViewGenderCharacter.setAdapter(genderAdapter)

        binding.autoCompleteTextViewStatusCharacter.setOnItemClickListener { _, _, position, _ ->
            filterCharacter.status = if(position == 0) "" else statuses[position]
        }

        binding.autoCompleteTextViewGenderCharacter.setOnItemClickListener { _, _, position, _ ->
            filterCharacter.gender = if(position == 0) "" else genders[position]
        }

        viewModel.filter
            .flowWithLifecycle(lifecycle, Lifecycle.State.CREATED)
            .onEach(::updateFilter)
            .launchIn(lifecycleScope)
    }

    private fun updateFilter(filter : FilterCharacter) {
        filterCharacter.query = filter.query
        if(filter.status.isNotEmpty()){
            binding.autoCompleteTextViewStatusCharacter.setText(filter.status, false)
            filterCharacter.status = filter.status
        } else {
            binding.autoCompleteTextViewStatusCharacter.setText(statuses[0], false)
        }

        if(filter.gender.isNotEmpty()){
            binding.autoCompleteTextViewGenderCharacter.setText(filter.gender, false)
            filterCharacter.gender = filter.gender
        } else {
            binding.autoCompleteTextViewGenderCharacter.setText(genders[0], false)
        }
    }

}