package com.zenaptix.generatorstest.generators

import com.zenaptix.generatortest.{Block, GeneratorBox}
import org.scalacheck.{Arbitrary, Gen}
import scorex.core.transaction.box.proposition.PublicKey25519Proposition
import scorex.core.transaction.state.{PrivateKey25519, PrivateKey25519Companion}
import scorex.crypto.signatures.{Curve25519, Signature}

trait TestGenerator {

  lazy val positiveSmallNumberGen: Gen[Int] = Gen.choose(1,9999)

  lazy val positiveLongGen: Gen[Long] = Gen.choose(1, Long.MaxValue)

  //Generates a Byte-Array of given length
  def bytesGen(size: Int): Gen[Array[Byte]] = Gen.choose(size, size) flatMap {
    sz => Gen.listOfN(sz, Arbitrary.arbitrary[Byte]).map(_.toArray)
  }

  //Converts a Byte-Array of given length to a string
  def stringGen(size: Int): Gen[String] = {
    bytesGen(size).sample.get.map(_.toChar).mkString
  }

  //Creates a Cryptographic keypair
  lazy val keypairGen: Gen[(PrivateKey25519, PublicKey25519Proposition)] =
    bytesGen(Curve25519.KeyLength).map(s => PrivateKey25519Companion.generateKeys(s))

  //Creates a GeneratorBox and includes the Private key
  lazy val generatorBoxWithPrivkeyGen: Gen[(GeneratorBox, PrivateKey25519)] = for {
    keypair <- keypairGen
    value <- positiveLongGen
  } yield(GeneratorBox(keypair._2,value),keypair._1)

  //Creates a Block with a random signature and includes the Private Key
  lazy val unsignedBlockWithPrivKeyGen: Gen[(Block,PrivateKey25519)] = for {
    dataLen <- positiveSmallNumberGen
    data <- stringGen(dataLen)
    timestamp <- positiveLongGen
    generator <- generatorBoxWithPrivkeyGen
    fakeSignature <- bytesGen(Curve25519.SignatureLength)
  } yield (Block(data,timestamp,generator._1,Signature @@ fakeSignature),generator._2)

}