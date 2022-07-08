package id.binar.fp.secondhand.ui.main.adapter.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.data.source.network.response.CategoryDto
import id.binar.fp.secondhand.databinding.ItemProductCategoryBinding

class CategoryAdapter(
    private val onClick: (CategoryDto) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var oldCategoryList = emptyList<CategoryDto>()
    private var selectedItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemProductCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(oldCategoryList[position], position)
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

        fun bind(category: CategoryDto, position: Int) {
            binding.tvCategory.text = category.name

            if (selectedItem == position) {
                binding.tvCategory.setTextColor(ContextCompat.getColor(context, R.color.white))
                binding.cvCategory.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorPrimary
                    )
                )
            } else {
                binding.tvCategory.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.colorAccent
                    )
                )
                binding.cvCategory.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }

            itemView.setOnClickListener {
                onClick(category)
                val previousItem = selectedItem
                selectedItem = position

                notifyItemChanged(previousItem)
                notifyItemChanged(position)
            }
        }
    }
}