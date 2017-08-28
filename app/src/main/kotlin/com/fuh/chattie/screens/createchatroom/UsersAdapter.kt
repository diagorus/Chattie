package com.fuh.chattie.screens.createchatroom

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.*
import com.fuh.chattie.R
import com.fuh.chattie.model.User
import com.fuh.chattie.util.extentions.loadImageByUri
import kotlinx.android.synthetic.main.user_item.view.*

/**
 * Created by lll on 23.08.2017.
 */
class UsersAdapter(items: List<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    var actionMode: ActionMode? = null

    var items: List<User> = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val selected: MutableMap<User, View> = mutableMapOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]

        holder.bind(data)
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(data: User) {
            with(itemView) {
                val isSelectedItem = selected.contains(data)
                isSelected = isSelectedItem

                tvUserItemName.text = data.name

                data.photoUrl?.let {
                    val photoUri = Uri.parse(it)
                    ivUserItemPhoto.loadImageByUri(photoUri)
                } ?: ivUserItemPhoto.loadImageByUri(null)

                setOnClickListener {
                    actionMode?.let {
                        toggleSelection(this, data)
                    }
                }

                setOnLongClickListener {
                    if (actionMode == null) {
                        startSelection(this)
                        toggleSelection(this, data)
                    }

                    true
                }
            }
        }

        private fun startSelection(view: View) {
            actionMode = view.startActionMode(object : ActionMode.Callback {

                override fun onCreateActionMode(actionMode: ActionMode, menu: Menu): Boolean {
                    actionMode.menuInflater.inflate(R.menu.chat_menu_action, menu)

                    return true
                }

                override fun onPrepareActionMode(actionMode: ActionMode, menu: Menu): Boolean {
                    return false
                }

                override fun onDestroyActionMode(actionMode: ActionMode) {
                    selected.forEach { (_, view) -> view.isSelected = false }
                    selected.clear()
                    finishSelection()
                }

                override fun onActionItemClicked(actionMode: ActionMode, menuItem: MenuItem): Boolean {
                    selected.forEach { (_, view) -> view.isSelected = false }
                    selected.clear()
                    finishSelection()
                    return true
                }
            })
        }

        private fun finishSelection() {
            actionMode?.let {
                it.finish()
                actionMode = null
            }
        }

        private fun toggleSelection(itemView: View, data: User) {
            val isAlreadySelected = selected.contains(data)

            itemView.isSelected = !isAlreadySelected
            if (isAlreadySelected) {
                selected.remove(data)
            } else {
                selected.put(data, itemView)
            }

            if (selected.isEmpty()) {
                finishSelection()
            }
        }
    }
}