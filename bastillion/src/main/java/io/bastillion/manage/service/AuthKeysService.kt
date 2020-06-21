///**
// * Copyright (C) 2013 Loophole, LLC
// *
// *
// * This program is free software: you can redistribute it and/or  modify
// * it under the terms of the GNU Affero General Public License, version 3,
// * as published by the Free Software Foundation.
// *
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU Affero General Public License for more details.
// *
// *
// * You should have received a copy of the GNU Affero General Public License
// * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
// *
// *
// * As a special exception, the copyright holders give permission to link the
// * code of portions of this program with the OpenSSL library under certain
// * conditions as described in each individual source file and distribute
// * linked combinations including the program with the OpenSSL library. You
// * must comply with the GNU Affero General Public License in all respects for
// * all of the code used other than as permitted herein. If you modify file(s)
// * with this exception, you may extend this exception to your version of the
// * file(s), but you are not obligated to do so. If you do not wish to do so,
// * delete this exception statement from your version. If you delete this
// * exception statement from all source files in the program, then also delete
// * it in the license file.
// */
//package io.bastillion.manage.service
//
//import com.jcraft.jsch.JSch
//import com.jcraft.jsch.KeyPair
//import io.bastillion.common.util.AppConfig
//import io.bastillion.common.util.AuthUtil
//import io.bastillion.manage.db.*
//import io.bastillion.manage.model.*
//import io.bastillion.manage.util.EncryptionUtil
//import io.bastillion.manage.util.RefreshAuthKeyUtil
//import io.bastillion.manage.util.SSHUtil
//import org.apache.commons.lang3.StringUtils
//import org.slf4j.LoggerFactory
//import java.io.ByteArrayOutputStream
//import java.io.OutputStream
//import javax.servlet.http.HttpServletRequest
//import javax.servlet.http.HttpServletResponse
//
///**
// * Action to generate and distribute auth keys for systems or users
// */
//class AuthKeysService {
//
//    fun enablePublicKey(publicKey: PublicKey, profileList: List<Profile?>?, userList: List<User?>?, sortedSet: SortedSet<User>?): String {
//
//    }
//
//    fun disablePublicKey(publicKey: PublicKey, profileList: List<Profile?>?, userList: List<User?>?, sortedSet: SortedSet?) {
//    }
//
//    fun manageViewKeys(publicKey: PublicKey?, profileList: List<Profile?>?, userList: List<User?>?, sortedSet: SortedSet<User>?) {
//
//    }
//
//    fun adminViewKeys(request: HttpServletRequest, publicKey: PublicKey?, profileList: List<Profile?>?, userList: List<User?>?, sortedSet: SortedSet?) {
//        var profileList = profileList
//        var sortedSet = sortedSet
//        val userId = AuthUtil.getUserId(request.session)
//        val userType = AuthUtil.getUserType(request.session)
//        profileList = if (Auth.MANAGER == userType) {
//            ProfileDB.getAllProfiles()
//        } else {
//            UserProfileDB.getProfilesByUser(userId)
//        }
//        sortedSet = PublicKeyDB.getPublicKeySet(sortedSet, userId)
//
////        userPublicKeyList = PublicKeyDB.getUniquePublicKeysForUser(userId);
//    }
//
//    fun savePublicKeys(request: HttpServletRequest, publicKey: PublicKey) {
//        val userId = AuthUtil.getUserId(request.session)
//        val userType = AuthUtil.getUserType(request.session)
//        publicKey.userId = userId
//        if (Auth.MANAGER == userType || UserProfileDB.checkIsUsersProfile(userId, publicKey.profile.id)) {
//            if (publicKey.id != null) {
//                PublicKeyDB.updatePublicKey(publicKey)
//            } else {
//                PublicKeyDB.insertPublicKey(publicKey)
//            }
//            distributePublicKeys(publicKey)
//        }
//
////        return "redirect:/admin/viewKeys.ktrl?sortedSet.orderByDirection=" + sortedSet.getOrderByDirection() + "&sortedSet.orderByField=" + sortedSet.getOrderByField() + "&keyNm=" + publicKey.getKeyNm();
//    }
//
//    fun deletePublicKey(request: HttpServletRequest, publicKey: PublicKey) {
//        var publicKey = publicKey
//        if (publicKey.id != null) {
//            //get public key then delete
//            publicKey = PublicKeyDB.getPublicKey(publicKey.id)
//            PublicKeyDB.deletePublicKey(publicKey.id, AuthUtil.getUserId(request.session))
//        }
//        distributePublicKeys(publicKey)
//        //        return "redirect:/admin/viewKeys.ktrl?sortedSet.orderByDirection=" + sortedSet.getOrderByDirection() + "&sortedSet.orderByField=" + sortedSet.getOrderByField();
//    }
//
//    fun downloadPvtKey(request: HttpServletRequest, response: HttpServletResponse, publicKey: PublicKey): String? {
//        val privateKey = EncryptionUtil.decrypt(request.session.getAttribute(PVT_KEY) as String)
//        if (StringUtils.isNotEmpty(publicKey.keyNm) && StringUtils.isNotEmpty(privateKey)) {
//            try {
//                response.contentType = "application/octet-stream"
//                response.setHeader("Content-Disposition", "attachment;filename=" + publicKey.keyNm + ".key")
//                response.outputStream.write(privateKey.toByteArray())
//                response.outputStream.flush()
//                response.outputStream.close()
//            } catch (ex: Exception) {
//                log.error(ex.toString(), ex)
//            }
//        }
//        //remove pvt key
//        request.session.setAttribute(PVT_KEY, null)
//        request.session.removeAttribute(PVT_KEY)
//        return null
//    }
//
//    /**
//     * generates public private key from passphrase
//     *
//     * @param username username to set in public key comment
//     * @param keyname  keyname to set in public key comment
//     * @return public key
//     */
//    fun generateUserKey(publicKey: PublicKey, request: HttpServletRequest, username: String, keyname: String): String? {
//
//        //set key type
//        var type = KeyPair.RSA
//        if ("dsa" == SSHUtil.KEY_TYPE) {
//            type = KeyPair.DSA
//        } else if ("ecdsa" == SSHUtil.KEY_TYPE) {
//            type = KeyPair.ECDSA
//        }
//        val jsch = JSch()
//        var pubKey: String? = null
//        try {
//            val keyPair = KeyPair.genKeyPair(jsch, type, SSHUtil.KEY_LENGTH)
//            var os: OutputStream = ByteArrayOutputStream()
//            keyPair.writePrivateKey(os, publicKey.passphrase.toByteArray())
//            //set private key
//            request.session.setAttribute(PVT_KEY, EncryptionUtil.encrypt(os.toString()))
//            os = ByteArrayOutputStream()
//            keyPair.writePublicKey(os, "$username@$keyname")
//            pubKey = os.toString()
//            keyPair.dispose()
//        } catch (ex: Exception) {
//            log.error(ex.toString(), ex)
//        }
//        return pubKey
//    }
//
//    fun validateSavePublicKeys(publicKey: PublicKey?, request: HttpServletRequest, existingKeyId: Long?) {
//        val userId = AuthUtil.getUserId(request.session)
//        //
////        if (publicKey == null
////                || publicKey.getKeyNm() == null
////                || publicKey.getKeyNm().trim().equals("")) {
////            addFieldError("publicKey.keyNm", REQUIRED);
////
////        }
//        if (publicKey != null) {
//            if (existingKeyId != null) {
//                publicKey.publicKey = PublicKeyDB.getPublicKey(existingKeyId).publicKey
//            } else if ("true" == AppConfig.getProperty("forceUserKeyGeneration")) {
//
////                if (publicKey.getPassphrase() == null ||
////                        publicKey.getPassphrase().trim().equals("")) {
////                    addFieldError("publicKey.passphrase", REQUIRED);
////                } else if (publicKey.getPassphraseConfirm() == null ||
////                        publicKey.getPassphraseConfirm().trim().equals("")) {
////                    addFieldError("publicKey.passphraseConfirm", REQUIRED);
////                } else if (!publicKey.getPassphrase().equals(publicKey.getPassphraseConfirm())) {
////                    addError("Passphrases do not match");
////                } else if (!PasswordUtil.isValid(publicKey.getPassphrase())) {
////                    addError(PasswordUtil.PASSWORD_REQ_ERROR_MSG);
////                } else {
////                    publicKey.setPublicKey(generateUserKey(UserDB.getUser(userId).getUsername(), publicKey.getKeyNm()));
////                }
//            }
//
////            if (publicKey.getPublicKey() == null || publicKey.getPublicKey().trim().equals("")) {
////                addFieldError(PUBLIC_KEY_PUBLIC_KEY, REQUIRED);
////
////            } else if (SSHUtil.getFingerprint(publicKey.getPublicKey()) == null || SSHUtil.getKeyType(publicKey.getPublicKey()) == null) {
////                addFieldError(PUBLIC_KEY_PUBLIC_KEY, INVALID);
////
////            } else if (PublicKeyDB.isKeyDisabled(SSHUtil.getFingerprint(publicKey.getPublicKey()))) {
////                addError("This key has been disabled. Please generate and set a new public key.");
////                addFieldError(PUBLIC_KEY_PUBLIC_KEY, INVALID);
////
////            } else if (PublicKeyDB.isKeyRegistered(userId, publicKey)) {
////                addError("This key has already been registered under selected profile.");
////                addFieldError(PUBLIC_KEY_PUBLIC_KEY, INVALID);
////
////            }
//        }
//
////        if (!this.getFieldErrors().isEmpty()) {
////
////            String userType = AuthUtil.getUserType(getRequest().getSession());
////
////            if (Auth.MANAGER.equals(userType)) {
////                profileList = ProfileDB.getAllProfiles();
////            } else {
////                profileList = UserProfileDB.getProfilesByUser(userId);
////            }
////
////            sortedSet = PublicKeyDB.getPublicKeySet(sortedSet, userId);
////            userPublicKeyList = PublicKeyDB.getUniquePublicKeysForUser(userId);
////        }
//    }
//
//    /**
//     * distribute public keys to all systems or to profile
//     *
//     * @param publicKey public key to distribute
//     */
//    private fun distributePublicKeys(publicKey: PublicKey) {
//        if (publicKey.profile != null && publicKey.profile.id != null) {
//            RefreshAuthKeyUtil.refreshProfileSystems(publicKey.profile.id)
//        } else {
//            RefreshAuthKeyUtil.refreshAllSystems()
//        }
//    }
//
//    companion object {
//        private const val REQUIRED = "Required"
//        private const val INVALID = "Invalid"
//        private const val PUBLIC_KEY_PUBLIC_KEY = "publicKey.publicKey"
//        private const val PVT_KEY = "privateKey"
//        private val log = LoggerFactory.getLogger(AuthKeysService::class.java)
//    }
//}