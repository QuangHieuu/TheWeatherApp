package test.dn.weather.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import test.dn.weather.R
import test.dn.weather.databinding.ViewHolderDayBinding
import test.dn.weather.model.LocationDay
import test.dn.weather.utils.TimeConvert

class DayAdapter(
    private val listener: OnItemClick
) : ListAdapter<LocationDay, DayAdapter.DayViewHolder>(LocationDay.DiffUtil_CALLBACK) {

    interface OnItemClick {
        fun onClick(position: Int, locationDay: LocationDay)
    }

    private var current: Int = 0

    fun setCurrent(new: Int) {
        val old = current
        current = new
        notifyItemChanged(current)
        notifyItemChanged(old)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = ViewHolderDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val locationDay = getItem(position)
        holder.bind(locationDay)
        holder.binding.apply {
            if (current == position) {
                viewContainer.background =
                    ContextCompat.getDrawable(this.root.context, R.drawable.color_gradient_today)
            } else {
                viewContainer.background =
                    ContextCompat.getDrawable(this.root.context, R.color.transparent)
            }
        }
    }

    inner class DayViewHolder(
        val binding: ViewHolderDayBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(locationDay: LocationDay) {
            with(binding) {
                time = locationDay.date

                root.setOnClickListener { listener.onClick(adapterPosition, locationDay) }
            }
        }
    }
}

@BindingAdapter("setTextDay")
fun setTextDay(view: AppCompatTextView, day: String) {
    val time = TimeConvert.parseTimestampToString(day)!!
    view.text = TimeConvert.checkDate(time)
}

@BindingAdapter("setTextDate")
fun setTextDate(view: AppCompatTextView, day: String) {
    view.text = TimeConvert.formatDate(day, formatOutput = "MM/dd")
}
