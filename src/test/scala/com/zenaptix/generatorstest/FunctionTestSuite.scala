package com.zenaptix.generatorstest

import com.zenaptix.generatortest.MathFunctions._
import com.zenaptix.generatorstest.generators.TestGenerator

import org.scalatest.prop.{GeneratorDrivenPropertyChecks, PropertyChecks}
import org.scalatest.{Matchers, PropSpec}

class FunctionTestSuite
  extends PropSpec
    with PropertyChecks
    with GeneratorDrivenPropertyChecks
    with Matchers
    with TestGenerator {

  property("Add function should add two Integers together") {
    forAll(positiveSmallNumberGen, minSuccessful(10)) {
      num =>
        val ans = add(num, num)
        ans shouldBe (num + num)
    }
  }

  property("Subtract function should subtract two Integers from another") {
    forAll(positiveSmallNumberGen, minSuccessful(10)) {
      num =>
        val ans = subtract(num, num)
        ans shouldBe (num - num)
    }
  }

  property("Multiply function should mutiply two Integers together") {
    forAll(positiveSmallNumberGen, minSuccessful(10)) {
      num =>
        val ans = multiply(num, num)
        ans shouldBe (num * num)
    }
  }

  property("Divide function should divide two Integers") {
    forAll(positiveSmallNumberGen, minSuccessful(10)) {
      num =>
        val ans = divide(num, num).get
        ans shouldBe (num / num)
    }
  }

  property("Block should have a valid signature") {
    forAll(unsignedBlockWithPrivKeyGen, minSuccessful(10)) {
      blockWithPrivKey =>
        val unsignedBlock = blockWithPrivKey._1
        val privKey = blockWithPrivKey._2

        val signedBlock = signBlock(unsignedBlock, privKey).get

        isValidSignature(signedBlock) shouldBe true
    }
  }
}