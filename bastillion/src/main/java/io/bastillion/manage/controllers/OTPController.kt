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
        app.get("/admin/viewOTP") { viewOTP() }
                .post("/admin/otpSubmit") { otpSubmit() }
                .get("/admin/qrImage") { qrImage() }
    }

    private fun viewOTP() {
    }


    private fun otpSubmit() {

    }

    private fun qrImage() {

    }
}