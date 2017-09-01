package com.fuh.chattie.utils.ui

import android.support.v7.widget.RecyclerView
import android.view.*
import com.fuh.chattie.R

/**
 * Created by lll on 29.08.2017.
 */
abstract class SelectableAdapter<VH : RecyclerView.ViewHolder>(
        private val onSelectionSuccess: (List<Int>) -> Unit
) : RecyclerView.Adapter<VH>() {

    private var actionMode: ActionMode? = null

    private val selected: MutableMap<Int, View> = mutableMapOf()

    override fun onBindViewHolder(holder: VH, position: Int) {
        with(holder.itemView) {
            val isSelectedItem = selected.contains(position)
            isSelected = isSelectedItem

            setOnClickListener {
                actionMode?.let {
                    toggleSelection(position, this)
                }
            }

            setOnLongClickListener {
                if (actionMode == null) {
                    startSelection(this)

                    toggleSelection(position, this)
                }

                true
            }
        }
    }

    private fun startSelection(view: View) {
        actionMode = view.startActionMode(object : ActionMode.Callback {

            override fun onCreateActionMode(actionMode: ActionMode, menu: Menu): Boolean {
                actionMode.menuInflater.inflate(R.menu.action_menu_choose, menu)

                return true
            }

            override fun onPrepareActionMode(actionMode: ActionMode, menu: Menu): Boolean = false

            override fun onDestroyActionMode(actionMode: ActionMode) {
                finishSelection()
            }

            override fun onActionItemClicked(actionMode: ActionMode, menuItem: MenuItem): Boolean {
                when(menuItem.itemId) {
                    R.id.itemChooseActionMenuConfirm -> {
                        val selectedPositions = selected.keys.toList()

                        onSelectionSuccess(selectedPositions)
                    }
                }
                finishSelection()

                return true
            }
        })
    }

    private fun finishSelection() {
        selected.forEach { (_, itemView) -> itemView.isSelected = false }
        selected.clear()

        actionMode?.let {
            it.finish()
            actionMode = null
        }
    }

    private fun toggleSelection(listPosition: Int, itemView: View) {
        val isAlreadySelected = selected.contains(listPosition)
        itemView.isSelected = !isAlreadySelected

        if (isAlreadySelected) {
            selected.remove(listPosition)
        } else {
            selected.put(listPosition, itemView)
        }

        actionMode?.title = "${selected.size} selected"

        if (selected.isEmpty()) {
            finishSelection()
        }
    }
}