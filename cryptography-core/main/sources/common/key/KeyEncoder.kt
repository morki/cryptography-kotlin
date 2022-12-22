package dev.whyoleg.cryptography.key

import dev.whyoleg.cryptography.*
import dev.whyoleg.cryptography.io.*

public typealias KeyEncoderFactory<P, KF> = CryptographyOperationFactory<P, KeyEncoder<KF>>
public typealias KeyEncoderProvider<P, KF> = CryptographyOperationProvider<P, KeyEncoder<KF>>

public interface KeyEncoder<KF : KeyFormat> : CryptographyOperation {
    public suspend fun encodeKey(format: KF): Buffer
    public suspend fun encodeKey(format: KF, keyDataOutput: Buffer): Buffer
    public fun encodeKeyBlocking(format: KF): Buffer
    public fun encodeKeyBlocking(format: KF, keyDataOutput: Buffer): Buffer
}
