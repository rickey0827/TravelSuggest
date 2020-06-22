package com.soft507.travelsuggest.adapter

import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.soft507.travelsuggest.R
import com.soft507.travelsuggest.bean.PersonalBean

/**
 * @author LRQ-Pro
 * @description:
 * @date : 2020/6/10 0:12
 */
class PersonalCenterAdapter(recourse: Int, data: MutableList<PersonalBean>) :
    BaseQuickAdapter<PersonalBean, BaseViewHolder>(recourse, data) {

    override fun convert(holder: BaseViewHolder, item: PersonalBean) {
        holder.setBackgroundResource(R.id.personal_icon, item.icon)
        holder.setText(R.id.person_text, item.text)

        if ("设置" == item.text) {
            holder.getView<LinearLayout>(R.id.personal_background).visibility= View.GONE
        }

    }
}