package id.binar.fp.secondhand.ui.main.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.binar.fp.secondhand.data.source.network.response.CategoryDto
import id.binar.fp.secondhand.databinding.ItemProductCategoryBinding

class CategoryAdapter(
    private val onClick: (CategoryDto) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var oldCategoryList = emptyList<CategoryDto>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemProductCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(oldCategoryList[position])
    }

    override fun getItemCount(): Int = oldCategoryList.size

    fun submitList(newCategoryList: List<CategoryDto>) {
        val diffUtil = CategoryDiffUtil(oldCategoryList, newCategoryList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldCategoryList = newCategoryList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CategoryViewHolder(private val binding: ItemProductCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryDto) {
            binding.tvCategory.text = category.name
            itemView.setOnClickListener { onClick(category) }
        }
    }
}