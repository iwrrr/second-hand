package id.binar.fp.secondhand.ui.main.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import id.binar.fp.secondhand.BaseType
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.databinding.ItemProductCategoryBinding
import id.binar.fp.secondhand.domain.model.Category
import id.binar.fp.secondhand.ui.base.BaseAdapter

@Suppress("UNCHECKED_CAST")
class CategoryAdapter(
    private val onClick: (Category) -> Unit,
) : BaseAdapter<BaseType>() {

    private var selectedItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseType> {
        val binding = ItemProductCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding) as BaseViewHolder<BaseType>
    }

    inner class CategoryViewHolder(private val binding: ItemProductCategoryBinding) :
        BaseViewHolder<Category>(binding.root) {

        override fun onBind(data: Category, position: Int) {
            with(binding) {
                tvCategory.text = data.name

                if (selectedItem == position) {
                    tvCategory.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.white
                        )
                    )
                    cvCategory.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorPrimary
                        )
                    )
                } else {
                    tvCategory.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.colorAccent
                        )
                    )
                    cvCategory.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.white
                        )
                    )
                }

                itemView.setOnClickListener {
                    onClick(data)
                    val previousItem = selectedItem
                    selectedItem = position

                    notifyItemChanged(previousItem)
                    notifyItemChanged(position)
                }
            }
        }
    }
}