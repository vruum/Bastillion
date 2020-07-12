package io.bastillion.manage.db.entities

import io.bastillion.manage.model.HostSystem
import org.jdbi.v3.core.mapper.reflect.ColumnName

/*
create table if not exists system (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  display_nm varchar not null,
  user varchar not null,
  host varchar not null,
  port INTEGER not null,
  authorized_keys varchar not null,
  status_cd varchar not null default 'INITIAL')
 */
data class HostSystem(@ColumnName("id") val id: Long? = null,
                 @ColumnName("display_nm") val displayNm: String? = null,
                 @ColumnName("user") val user: String? = "root",
                 @ColumnName("host") val host: String? = null,
                 @ColumnName("port") val port: Int? = 22,
                 @ColumnName("display_label") val displayLabel: String? = null,
                 @ColumnName("authorized_keys") val authorizedKeys: String = "~/.ssh/authorized_keys",
                 @ColumnName("checked") val checked: Boolean? = false,
                 @ColumnName("status_cd") val statusCd: String = HostSystem.INITIAL_STATUS,
                 @ColumnName("instance_id") val instanceId: Int? = null,
                 @ColumnName("use_sudo") val useSudo: Boolean = false,
                 @ColumnName("sudo_group") val sudoGroup: String = "wheel") {
}