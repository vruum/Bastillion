package io.bastillion.manage.controllers

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import io.bastillion.common.util.AuthUtil
import io.bastillion.manage.control.OTPKtrl
import io.bastillion.manage.db.AuthDB
import io.bastillion.manage.db.UserDB
import io.bastillion.manage.util.OTPUtil
import io.javalin.Javalin
import io.javalin.http.Context
import loophole.mvc.annotation.Kontrol
import loophole.mvc.annotation.MethodType
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.net.URLEncoder
import java.util.*
import javax.imageio.ImageIO
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class OTPController(app: Javalin) {
    init {
        app.get("/admin/viewOTP") { viewOTP(it) }
                .post("/admin/otpSubmit") { otpSubmit(it) }
                .get("/admin/qrImage") { qrImage(it) }
    }

    private fun viewOTP(ctx: Context) {
        ctx.render("/admin/two-factor_otp.html")
    }


    private fun otpSubmit(ctx: Context) {
        ctx.render("/admin/two-factor_otp.html")
    }

    private fun qrImage(ctx: Context) {
        ctx.render("/admin/two-factor_otp.html")
    }
}