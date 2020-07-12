package io.bastillion.manage.db

import io.bastillion.manage.db.entities.HostSystem
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBeanList
import org.jdbi.v3.sqlobject.customizer.BindList
import org.jdbi.v3.sqlobject.statement.SqlBatch
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

interface ProfileSystemsRepo {
    @SqlBatch("insert into system_map (profile_id, system_id) values (:profileId,:systemId)")
    fun setSystemsForProfile(@Bind("profileId") profileId: Long,
                             @Bind("systemId") systemIdList: List<Long>)

    @SqlUpdate("delete from system_map where profileId = :profileId")
    fun clearCurrentSystemsForProfile(@Bind("profileId") profileId: Long)

    @SqlQuery("select * from  system s, system_map m where s.id=m.system_id and m.profile_id=:profileId" +
            " order by display_nm asc")
    fun getSystemsByProfile(profileId: Long): List<HostSystem>
}