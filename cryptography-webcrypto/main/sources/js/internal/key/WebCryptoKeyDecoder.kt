package dev.whyoleg.cryptography.webcrypto.internal.key

import dev.whyoleg.cryptography.io.*
import dev.whyoleg.cryptography.materials.key.*
import dev.whyoleg.cryptography.webcrypto.external.*
import dev.whyoleg.cryptography.webcrypto.internal.*

internal class WebCryptoKeyDecoder<KF : KeyFormat, K : Key>(
    private val algorithm: KeyImportAlgorithm,
    private val keyUsages: Array<String>,
    private val keyFormat: (KF) -> String,
    private val keyWrapper: (CryptoKey) -> K,
) : KeyDecoder<KF, K> {
    override suspend fun decodeFrom(format: KF, input: Buffer): K {
        return keyWrapper(WebCrypto.subtle.importKey(keyFormat(format), input, algorithm, true, keyUsages).await())
    }

    override fun decodeFromBlocking(format: KF, input: Buffer): K = nonBlocking()
}
