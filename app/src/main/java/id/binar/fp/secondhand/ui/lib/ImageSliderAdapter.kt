package id.binar.fp.secondhand.ui.lib

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import id.binar.fp.secondhand.databinding.ItemImageSliderBinding
import id.binar.fp.secondhand.util.Extensions.loadImage

class ImageSliderAdapter : RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {

    private var items: MutableList<String> = mutableListOf()
    private var errorImage: Int = 0
    private var placeholder: Int = 0
    private var background: Int = 0
    private var withTitle: Boolean = true
    private var withBackground: Boolean = true
    private var titleAlignment: Int = 0
    private var titleColor: Int = -0x10000
    private var imageScaleType: ImageView.ScaleType? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setItem(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setErrorImage(image: Int) {
        errorImage = image
    }

    fun setPlaceholderImage(image: Int) {
        placeholder = image
    }

    fun setBackgroundImage(image: Int) {
        background = image
    }

    fun setWithTitle(it: Boolean) {
        withTitle = it
    }

    fun setWithBackground(it: Boolean) {
        withBackground = it
    }

    fun setTitleAlignment(value: Int) {
        titleAlignment = value
    }

    fun setTitleColor(value: Int) {
        titleColor = value
    }

    fun setImageScaleType(value: ImageView.ScaleType) {
        imageScaleType = value
    }

    inner class ViewHolder(private val binding: ItemImageSliderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: String) {
            binding.ivSlider.loadImage(data)
        }
    }
}