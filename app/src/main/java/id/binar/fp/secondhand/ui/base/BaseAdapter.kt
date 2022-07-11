package id.binar.fp.secondhand.ui.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter<T : Any> :
    ListAdapter<T, BaseAdapter.BaseViewHolder<T>>(BaseItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return BaseViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.onBind(currentList[position])
        holder.onBind(currentList[position], position)
    }

    open class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView),
        BindRecyclerViewHolder<T> {

        override fun onBind(data: T) {}

        override fun onBind(data: T, position: Int) {}
    }

    interface BindRecyclerViewHolder<T> {

        fun onBind(data: T)
        fun onBind(data: T, position: Int)
    }
}