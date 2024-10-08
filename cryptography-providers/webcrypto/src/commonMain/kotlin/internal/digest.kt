/*
 * Copyright (c) 2024 Oleg Yukhnevich. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.whyoleg.cryptography.providers.webcrypto.internal

import dev.whyoleg.cryptography.*
import dev.whyoleg.cryptography.algorithms.*

internal fun CryptographyAlgorithmId<Digest>.blockSize(): Int = when (this) {
    SHA1   -> 64
    SHA256 -> 64
    SHA384 -> 128
    SHA512 -> 128
    else   -> throw CryptographyException("Unsupported hash algorithm: $this")
} * 8

internal fun CryptographyAlgorithmId<Digest>.hashAlgorithmName(): String = when (this) {
    SHA1   -> "SHA-1"
    SHA256 -> "SHA-256"
    SHA384 -> "SHA-384"
    SHA512 -> "SHA-512"
    else   -> throw CryptographyException("Unsupported hash algorithm: ${this}")
}

internal fun hashSize(hashAlgorithmName: String): Int = when (hashAlgorithmName) {
    "SHA-1"   -> 20
    "SHA-256" -> 32
    "SHA-384" -> 48
    "SHA-512" -> 64
    else      -> throw CryptographyException("Unsupported hash algorithm: $hashAlgorithmName")
}
