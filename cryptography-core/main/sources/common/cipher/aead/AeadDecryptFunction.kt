package dev.whyoleg.cryptography.cipher.aead

import dev.whyoleg.cryptography.cipher.*
import dev.whyoleg.cryptography.io.*

public interface AeadDecryptFunction : DecryptFunction {
    //TODO: naming?
    public fun putAssociatedData(associatedData: Buffer)
}
