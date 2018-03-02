package com.zenaptix.generatortest

import com.google.common.primitives.Longs
import scorex.core.transaction.state.PrivateKey25519
import scorex.crypto.signatures.Curve25519

import scala.util.Try

object MathFunctions{

  def add(a: Int, b: Int): Int = {
    a + b
  }

  def subtract(a: Int, b: Int): Int = {
    a - b
  }

  def multiply(a: Int, b: Int): Int = {
    a * b
  }

  def divide(a: Int, b: Int): Try[Int] = Try {
    require(b != 0, "Cannot divide by zero")
    a / b
  }

  //Extracts data elements from the block that should remain constant and returns a Byte-Array
  def getMessageToSign(block: Block): Array[Byte] = {
    block.data.getBytes ++
      block.generator.proposition.pubKeyBytes ++
      Longs.toByteArray(block.generator.value) ++
      Longs.toByteArray(block.timestamp)
  }

  //Add a cryptographic signature to a Block using the provided Private Key
  def signBlock(unsignedBlock: Block, privKey: PrivateKey25519): Try[Block] = Try {
    require(privKey.publicImage.bytes sameElements unsignedBlock.generator.proposition.bytes, "GeneratorBox does not belong to provided PrivateKey")

    val messageToSign = getMessageToSign(unsignedBlock)
    val signature = Curve25519.sign(privKey.privKeyBytes, messageToSign)
    unsignedBlock.copy(signature = signature)
  }

  //Validates if a Block was signed correctly
  def isValidSignature(signedBlock: Block): Boolean = {
    val proposition = signedBlock.generator.proposition
    val sig = signedBlock.signature
    val messageToSign = getMessageToSign(signedBlock)

    proposition.verify(messageToSign, sig)
  }

}