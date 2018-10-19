package misk.digester

import com.squareup.digester.protos.service.DigestData

/**
 * Wraps an adapted t-digest implementation from Stripe's Veneur project
 */
class FakeDigest : TDigest<FakeDigest> {

  internal val addedValues: MutableList<Double>
  private var count: Long = 0
  private var sum: Double = 0.0

  constructor() {
    addedValues = mutableListOf()
  }

  constructor (values: List<Double>) {
    addedValues = values.toMutableList()
  }

  /** Adds a new observation to the t-digest */
  override fun add(value: Double) {
    addedValues.add(value)
    count += 1
    sum += value
  }

  override fun quantile(quantile: Double): Double {
    if (addedValues.count() > 0) {
      return addedValues.last()
    }
    return Double.NaN
  }

  /** Returns the count of the number of observations recorded within the t-digest */
  override fun count(): Long {
    return count
  }

  /** Returns the sum of all values added into the digest, or NaN if no values have been added */
  override fun sum(): Double {
    if (count > 0) {
      return sum
    }
    return Double.NaN
  }

  /** Merges this t-digest into another t-digest */
  override fun mergeInto(other:  TDigest<FakeDigest>) {
    (other as FakeDigest).addedValues.addAll(addedValues)
    other.count += count
    other.sum += sum
  }

  /** Returns a representation fo the t-digest that can be later be reconstituted into an instance of the same type */
  override fun proto(): DigestData {
    throw Exception("proto is not implemented for Fake Digest")
  }
}