package dev.whyoleg.cryptography.webcrypto.internal.key

import dev.whyoleg.cryptography.materials.key.*
import dev.whyoleg.cryptography.webcrypto.external.*
import dev.whyoleg.cryptography.webcrypto.internal.*

internal class WebCryptoSymmetricKeyGenerator<K : Key>(
    private val algorithm: SymmetricKeyGenerationAlgorithm,
    private val keyUsages: Array<String>,
    private val keyWrapper: (CryptoKey) -> K,
) : KeyGenerator<K> {
    override suspend fun generateKey(): K {
        return keyWrapper(WebCrypto.subtle.generateKey(algorithm, true, keyUsages).await())
    }

    override fun generateKeyBlocking(): K = nonBlocking()
}
