/*
 * Copyright (c) 2024 Oleg Yukhnevich. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.whyoleg.cryptography.providers.apple.algorithms

import dev.whyoleg.cryptography.algorithms.symmetric.*
import dev.whyoleg.cryptography.operations.cipher.*
import dev.whyoleg.cryptography.providers.apple.internal.*
import platform.CoreCrypto.*

internal object CCAesEcb : CCAes<AES.ECB.Key>(), AES.ECB {
    override fun wrapKey(key: ByteArray): AES.ECB.Key = AesEcbKey(key)

    private class AesEcbKey(private val key: ByteArray) : AES.ECB.Key {
        override fun cipher(padding: Boolean): Cipher = AesEcbCipher(key, padding)
        override fun encodeToBlocking(format: AES.Key.Format): ByteArray = when (format) {
            AES.Key.Format.RAW -> key.copyOf()
            AES.Key.Format.JWK -> error("JWK is not supported")
        }
    }
}

private const val blockSizeBytes = 16 //bytes for ECB

private class AesEcbCipher(key: ByteArray, padding: Boolean) : Cipher {
    private val cipher = CCCipher(
        algorithm = kCCAlgorithmAES,
        mode = kCCModeECB,
        padding = if (padding) ccPKCS7Padding else ccNoPadding,
        key = key
    )

    override fun encryptBlocking(plaintextInput: ByteArray): ByteArray {
        return cipher.encrypt(null, plaintextInput)
    }

    override fun decryptBlocking(ciphertextInput: ByteArray): ByteArray {
        require(ciphertextInput.size % blockSizeBytes == 0) { "Ciphertext is not padded" }

        return cipher.decrypt(
            iv = null,
            ciphertext = ciphertextInput,
            ciphertextStartIndex = 0
        )
    }
}
