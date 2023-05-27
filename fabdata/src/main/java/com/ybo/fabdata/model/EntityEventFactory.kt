package com.ybo.fabdata.model

import com.ybo.fabdata.api.ApiModelEvent

internal class EntityEventFactory : EntityEvent.Factory {
    override fun create(inEventFromAPI: ApiModelEvent): EntityEvent {
        return _EntityEvent(inEventFromAPI)
    }
}