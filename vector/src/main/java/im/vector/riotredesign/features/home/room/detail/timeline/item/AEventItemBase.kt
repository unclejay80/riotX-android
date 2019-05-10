/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package im.vector.riotredesign.features.home.room.detail.timeline.item

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewStub
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.Guideline
import im.vector.riotredesign.R
import im.vector.riotredesign.core.epoxy.VectorEpoxyHolder
import im.vector.riotredesign.core.epoxy.VectorEpoxyModel

abstract class AEventItemBase<H : AEventItemBase.BaseHolder> : VectorEpoxyModel<H>() {

    var avatarStyle: AvatarStyle = Companion.AvatarStyle.MEDIUM

    override fun bind(holder: H) {
        super.bind(holder)
        //optimize?
        val px = dpToPx(avatarStyle.avatarSizeDP, holder.view.context)
        holder.leftGuideline.setGuidelineBegin(px)
    }


    override fun getViewType(): Int {
        return getStubType()
    }

    abstract fun getStubType(): Int


    abstract class BaseHolder : VectorEpoxyHolder() {

        val leftGuideline by bind<Guideline>(R.id.messageStartGuideline)

        @IdRes
        abstract fun getStubId(): Int

        override fun bindView(itemView: View) {
            super.bindView(itemView)
            inflateStub()
        }

        private fun inflateStub() {
            view.findViewById<ViewStub>(getStubId()).inflate()
        }

    }

    companion object {

        enum class AvatarStyle(val avatarSizeDP: Int) {
            BIG(50),
            MEDIUM(40),
            SMALL(30),
            NONE(0)
        }

        fun dpToPx(dp: Int, context: Context): Int {
            return TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dp.toFloat(),
                    context.resources.displayMetrics
            ).toInt()
        }
    }
}