package io.bastillion.manage.controllers


import io.javalin.Javalin

class SecureShellController(app: Javalin) {
    init {
        app
                .post("/admin/createTerms") { createTerms() }
                .get("/admin/getNextPendingSystemForTerms") { getNextPendingSystemForTerms() }
                .get("/admin/selectSystemsForCompositeTerms") { selectSystemsForCompositeTerms() }
                .get("/admin/exitTerms") { exitTerms() }
                .get("/admin/disconnectTerm") { disconnectTerm() }
                .get("/admin/createSession") { createSession() }
                .get("/admin/setPtyType") { setPtyType() }

    }

    fun createTerms() {
    }

    fun getNextPendingSystemForTerms() {
    }

    fun selectSystemsForCompositeTerms() {
    }

    fun exitTerms() {
    }

    fun disconnectTerm() {
    }

    fun createSession() {
    }

    fun setPtyType() {
    }

    /**
     * set system list once all connections have been attempted
     *
     * @param userId    user id
     * @param sessionId session id
     */
    private fun setSystemList(userId: Long, sessionId: Long) {}
}