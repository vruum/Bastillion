package io.bastillion.manage.controllers


import io.javalin.Javalin


class SessionAuditController(app: Javalin) {
    init {
        app.get("/manage/viewSessions") { viewSessions() }
                .get("/manage/getTermsForSession") { getTermsForSession() }
                .get("/manage/getJSONTermOutputForSession") { getJSONTermOutputForSession() }
    }


    fun viewSessions() {}


    fun getTermsForSession() {}

    fun getJSONTermOutputForSession() {
    }
}