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

package im.vector.riotx.features.widgets

import im.vector.matrix.android.api.session.widgets.model.Widget
import im.vector.riotx.core.di.ActiveSessionHolder
import javax.inject.Inject

class WidgetArgsBuilder @Inject constructor(private val sessionHolder: ActiveSessionHolder) {

    @Suppress("UNCHECKED_CAST")
    fun buildIntegrationManagerArgs(roomId: String, integId: String?, screen: String?): WidgetArgs {
        val session = sessionHolder.getActiveSession()
        val integrationManagerConfig = session.integrationManagerService().getPreferredConfig()
        val normalizedScreen = when {
            screen == null             -> null
            screen.startsWith("type_") -> screen
            else                       -> "type_$screen"
        }
        return WidgetArgs(
                baseUrl = integrationManagerConfig.uiUrl,
                kind = WidgetKind.INTEGRATION_MANAGER,
                roomId = roomId,
                urlParams = mapOf(
                        "screen" to normalizedScreen,
                        "integ_id" to integId,
                        "room_id" to roomId
                ).filterNotNull()
        )
    }

    @Suppress("UNCHECKED_CAST")
    fun buildStickerPickerArgs(roomId: String, widget: Widget): WidgetArgs {
        val widgetId = widget.widgetId
        val baseUrl = widget.computedUrl ?: throw IllegalStateException()
        return WidgetArgs(
                baseUrl = baseUrl,
                kind = WidgetKind.STICKER_PICKER,
                roomId = roomId,
                widgetId = widgetId,
                urlParams = mapOf(
                        "widgetId" to widgetId,
                        "room_id" to roomId
                ).filterNotNull()
        )
    }

    fun buildRoomWidgetArgs(roomId: String, widget: Widget): WidgetArgs {
        val widgetId = widget.widgetId
        val baseUrl = widget.computedUrl?: throw IllegalStateException()
        return WidgetArgs(
                baseUrl = baseUrl,
                kind = WidgetKind.ROOM,
                roomId = roomId,
                widgetId = widgetId
        )
    }

    @Suppress("UNCHECKED_CAST")
    private fun Map<String, String?>.filterNotNull(): Map<String, String> {
        return filterValues { it != null } as Map<String, String>
    }
}
