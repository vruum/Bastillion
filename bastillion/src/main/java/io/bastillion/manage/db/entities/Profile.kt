package io.bastillion.manage.db.entities

import io.bastillion.manage.model.HostSystem

data class Profile(var id: Long,
                   var nm: String,
                   var desc: String?) {
}