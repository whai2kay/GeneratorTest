package com.zenaptix.generatortest

import scorex.core.transaction.box.proposition.PublicKey25519Proposition
import scorex.crypto.signatures.Signature

case class Block(data: String, timestamp: Long, generator: GeneratorBox, signature: Signature)
case class GeneratorBox(proposition: PublicKey25519Proposition, value: Long)