/*
 * Copyright (c) 2020 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package im.vector.riotx.features.widgets.permissions

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import im.vector.matrix.android.api.session.widgets.model.Widget
import im.vector.riotx.features.widgets.WidgetArgs

data class RoomWidgetPermissionViewState(
        val roomId: String,
        val widgetId: String,
        val permissionData: Async<WidgetPermissionData> = Uninitialized
) : MvRxState {

    constructor(widgetArgs: WidgetArgs) : this(
            roomId = widgetArgs.roomId,
            widgetId = widgetArgs.widgetId!!
    )

    data class WidgetPermissionData(
            val widget: Widget,
            val permissionsList: List<Int> = emptyList(),
            val isWebviewWidget: Boolean = true,
            val widgetDomain: String? = null
    )
}


