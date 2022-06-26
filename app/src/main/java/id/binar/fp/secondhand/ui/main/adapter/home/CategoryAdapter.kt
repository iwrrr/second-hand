package id.binar.fp.secondhand.ui.main.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.binar.fp.secondhand.databinding.ItemProductCategoryBinding
import id.binar.fp.secondhand.util.dummy.Category

class CategoryAdapter(
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var oldCategoryList = emptyList<Category>()

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

    fun submitList(newCategoryList: List<Category>) {
        val diffUtil = CategoryDiffUtil(oldCategoryList, newCategoryList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldCategoryList = newCategoryList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CategoryViewHolder(private val binding: ItemProductCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.tvCategory.text = category.name
            itemView.setOnClickListener { onClick(category) }
        }
    }
}