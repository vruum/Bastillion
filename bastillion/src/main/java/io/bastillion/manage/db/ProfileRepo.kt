package io.bastillion.manage.db

import io.bastillion.manage.db.entities.Profile
import io.bastillion.manage.db.entities.WildCardArgument
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.statement.SqlQuery

interface ProfileRepo {

    @SqlQuery("select * from  profiles order by nm asc")
    fun getAllProfiles(): List<Profile>;

    @SqlQuery("select distinct p.* from  profiles p , system_map m, system s" +
            " where m.profile_id = p.id " +
            " and m.system_id = s.id " +
            " and (lower(s.display_nm) like %:system% or lower(s.host) like %:system%)")
    fun getProfilesFilterBySystem(@Bind("system") system: String);


    @SqlQuery("select distinct p.* from  profiles p , user_map m, users u " +
            " where m.profile_id = p.id and m.user_id = u.id " +
            " and (lower(u.first_nm) like :userOrEmail or lower(u.last_nm) like :userOrEmail " +
            " or lower(u.email) like :userOrEmail or lower(u.username) like :userOrEmail)")
    fun getProfilesFilterByUsernameOrEmail(@Bind("userOrEmail") username: WildCardArgument);
}