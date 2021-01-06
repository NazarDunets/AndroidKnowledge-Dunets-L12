package com.example.androidknowledgedunetsl12

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidknowledgedunetsl12.databinding.FragmentMsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

const val ARG_ITEM_COUNT = "item_count"

class MdBtSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentMsheetBinding? = null
    private val binding get() = requireNotNull(_binding)

    val sideColorsFromXml by lazy {
        listOf(
            requireContext().getColor(R.color.md_gradient_0),
            requireContext().getColor(R.color.md_gradient_1),
            requireContext().getColor(R.color.md_gradient_2),
            requireContext().getColor(R.color.md_gradient_3)
        )
    }

    companion object {
        fun newInstance(itemCount: Int): MdBtSheetFragment =
            MdBtSheetFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMsheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.msheetList
        recyclerView.layoutManager =
            LinearLayoutManager(context)
        recyclerView.adapter =
            arguments?.getInt(ARG_ITEM_COUNT)?.let { ItemAdapter(it) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class ViewHolder constructor(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.fragment_msheet_item,
            parent,
            false
        )
    ) {
        val itemText: TextView = itemView.findViewById(R.id.list_item_text)
    }


    private inner class ItemAdapter constructor(private val mItemCount: Int) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context), parent)

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val compoundDrawable = getDrawable(requireContext(), R.drawable.random_shape)
            compoundDrawable?.colorFilter =
                PorterDuffColorFilter(sideColorsFromXml[position % 4], PorterDuff.Mode.SRC)

            holder.itemText.also {
                it.text = getString(R.string.list_item_text, position)
                it.setCompoundDrawablesWithIntrinsicBounds(
                    compoundDrawable, null, null, null
                )
                it.setOnClickListener {
                    Toast.makeText(
                        requireContext(),
                        "Item #${position} pressed",
                        Toast.LENGTH_SHORT
                    ).show()
                    dialog?.dismiss()
                }
            }

        }

        override fun getItemCount(): Int = mItemCount
    }
}