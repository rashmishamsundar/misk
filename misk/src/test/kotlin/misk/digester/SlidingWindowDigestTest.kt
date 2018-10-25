package misk.digester

import misk.time.FakeClock
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.SortedMap
import java.util.concurrent.TimeUnit

class SlidingWindowDigestTest {
  private val baseClock: FakeClock = FakeClock()

  @Test
  fun slidingWindowDigestEmpty() {
    val digest = newSlidingWindowDigestTest()
    assertThat(digest.quantile(0.5)).isEqualTo(Double.NaN)
    expectQuantiles(digest, 0, Double.NaN, sortedMapOf())
    advanceWindows(1, digest)
    assertThat(digest.quantile(0.5)).isEqualTo(Double.NaN)
    expectQuantiles(digest, 0, Double.NaN, sortedMapOf())
  }

  @Test
  fun slidingWindowDigestObservation() {
    val digest = newSlidingWindowDigestTest()
    digest.observe(10.0)
    digest.observe(20.0)
    digest.observe(30.0)
    val windows = windows(digest)
    expectWindowDigests(
        digest.windows.toList(),
        listOf(
            newWindowDigest(windows[0], listOf(10.0, 20.0, 30.0)),
            newWindowDigest(windows[1], listOf(10.0, 20.0, 30.0)),
            newWindowDigest(windows[2], listOf(10.0, 20.0, 30.0))
        )
    )
    // No windows have closed yet so there is no reportable data yet
    assertThat(digest.quantile(0.5)).isEqualTo(Double.NaN)
    // Advance time so that one window is now closed
    advanceWindows(1, digest)
    assertThat(digest.quantile(0.5)).isEqualTo(30.0)
    expectQuantiles(digest, 3, 60.0,
        sortedMapOf(
            0.25 to 30.0,
            0.5 to 30.0))
  }

  @Test
  fun slidingWindowDigestObservationInMultipleWindows() {
    val digest = newSlidingWindowDigestTest()
    val windowsT0_2 = windows(digest)
    digest.observe(10.0) // in t0-t2 buckets
    advanceWindows(1, digest)
    digest.observe(20.0) // in t1-t3 buckets
    assertThat(digest.quantile(0.5)).isEqualTo(10.0)
    advanceWindows(1, digest)
    digest.observe(30.0) // in t2-t4 buckets
    assertThat(digest.quantile(0.5)).isEqualTo(20.0)
    val windowsT3_5 = advanceWindows(1, digest)
    assertThat(digest.quantile(0.5)).isEqualTo(30.0)
    expectWindowDigests(
        digest.windows,
        listOf(
            newWindowDigest(windowsT0_2[0], listOf(10.0)),
            newWindowDigest(windowsT0_2[1], listOf(10.0, 20.0)),
            newWindowDigest(windowsT0_2[2], listOf(10.0, 20.0, 30.0)),
            newWindowDigest(windowsT3_5[0], listOf(20.0, 30.0)),
            newWindowDigest(windowsT3_5[1], listOf(30.0))
        ))
  }

  @Test
  fun slidingWindowDigestClosedDigests() {
    val digest = newSlidingWindowDigestTest()
    val windows = windows(digest)
    digest.observe(10.0)
    expectWindowDigests(digest.closedDigests(windows[2].end.plusNanos(1)), listOf())
    expectWindowDigests(digest.closedDigests(windows[2].end), listOf(
        newWindowDigest(windows[2], listOf(10.0))
    ))
    expectWindowDigests(digest.closedDigests(windows[1].end.plusNanos(1)), listOf(
        newWindowDigest(windows[2], listOf(10.0))
    ))
    expectWindowDigests(digest.closedDigests(windows[1].end), listOf(
        newWindowDigest(windows[1], listOf(10.0)),
        newWindowDigest(windows[2], listOf(10.0))
    ))
    expectWindowDigests(digest.closedDigests(windows[0].end.minusNanos(0)), listOf(
        newWindowDigest(windows[0], listOf(10.0)),
        newWindowDigest(windows[1], listOf(10.0)),
        newWindowDigest(windows[2], listOf(10.0))
    ))
  }

  @Test
  fun slidingWindowDigestMergeInEmptyToEmpty() {
    val src = newSlidingWindowDigestTest()
    val dest = newSlidingWindowDigestTest()
    advanceWindows(3, src)
    advanceWindows(3, dest)
    dest.mergeIn(
        src.closedDigests(ZonedDateTime.ofInstant(baseClock.instant(), ZoneId.of("UTC"))))
    assertThat(dest.windows.count()).isEqualTo(0)
  }

  @Test
  fun slidingWindowDigestMergeInEmptyToValues() {
    val src = newSlidingWindowDigestTest()
    val dest = newSlidingWindowDigestTest()
    val windowsT0_2 = windows(dest)
    dest.observe(10.0)
    dest.mergeIn(
        src.closedDigests(ZonedDateTime.ofInstant(baseClock.instant(), ZoneId.of("UTC"))))
    expectWindowDigests(dest.windows, listOf(
        newWindowDigest(windowsT0_2[0], listOf(10.0)),
        newWindowDigest(windowsT0_2[1], listOf(10.0)),
        newWindowDigest(windowsT0_2[2], listOf(10.0))
    ))
  }

  @Test
  fun slidingWindowDigestMergeInValuesToValues() {
    val srcClock = FakeClock()
    val destClock = FakeClock()
    val src = newSlidingWindowDigestTest(srcClock)
    val dest = newSlidingWindowDigestTest(destClock)
    val windowsT0_2 = windows(src, srcClock)
    src.observe(100.0) // in t0-t2 buckets
    val windowsT3_5 = advanceWindows(3, src, srcClock)
    src.observe(200.0) // in t3-t5 buckets
    advanceWindows(3, src, srcClock)
    advanceWindows(1, dest, destClock)
    dest.observe(10.0) // in t1-t3 buckets
    assertThat(dest.openDigests(false).count()).isEqualTo(3)
    dest.mergeIn(src.closedDigests(windowsT0_2[0].end))
    expectWindowDigests(dest.windows, listOf(
        newWindowDigest(windowsT0_2[0], listOf(100.0)),
        newWindowDigest(windowsT0_2[1], listOf(10.0, 100.0)),
        newWindowDigest(windowsT0_2[2], listOf(10.0, 100.0)),
        newWindowDigest(windowsT3_5[0], listOf(10.0, 200.0)),
        newWindowDigest(windowsT3_5[1], listOf(200.0)),
        newWindowDigest(windowsT3_5[2], listOf(200.0)
        )))
  }

  @Test
  fun slidingWindowDigestGC() {
    val digest = newSlidingWindowDigestTest()
    digest.observe(10.0)
    // Move just past the threshold for collecting the last window
    baseClock.setNow(windows(digest)[2].end.toInstant())
    baseClock.add(1, TimeUnit.MINUTES)
    baseClock.add(1, TimeUnit.MILLISECONDS)

    digest.observe(20.0)
    val windows = windows(digest)
    expectWindowDigests(digest.windows, listOf(
        newWindowDigest(windows[0], listOf(20.0)),
        newWindowDigest(windows[1], listOf(20.0)),
        newWindowDigest(windows[2], listOf(20.0))
    ))
  }

  fun setClock(t: ZonedDateTime, clock: FakeClock) {
    require(!t.toInstant().isBefore(clock.instant())) {
      "Cannot go back in time"
    }

    clock.setNow(t.toInstant())
  }

  fun windows(slidingWindow: SlidingWindowDigest<FakeDigest>, clock: FakeClock = baseClock): List<Window> {
    return slidingWindow.windower.windowsContaining(
        ZonedDateTime.ofInstant(clock.instant(), ZoneId.of("UTC")))
  }

  fun advanceWindows(n: Int, digest: SlidingWindowDigest<FakeDigest>, clock: FakeClock = baseClock): List<Window> {
    repeat(n) {
      setClock(windows(digest)[0].end, clock)
    }
    return windows(digest)
  }

  fun newWindowDigest(window: Window, values: List<Double>): WindowDigest<FakeDigest> {
    return WindowDigest(
        window,
        FakeDigest(values)
    )
  }

  fun newSlidingWindowDigestTest(clock: FakeClock = baseClock): SlidingWindowDigest<FakeDigest> {
    return SlidingWindowDigest(
        Windower(10, 3),
        fun() = FakeDigest(),
        clock
    )
  }

  fun expectWindowDigests(
    actual: List<WindowDigest<FakeDigest>>,
    expected: List<WindowDigest<FakeDigest>>
  ) {
    assertThat(expected.count()).isEqualTo(actual.count())
    for (i in 0 until actual.count()) {
      if (i >= expected.count()) {
        break
      }
      // Compare window of each WindowDigest
      assertThat(actual[i].window).isEqualTo(expected[i].window)
      // Compare all values added within TDigest of each WindowDigest
      assertThat(actual[i].digest.addedValues).isEqualTo(expected[i].digest.addedValues)
    }
  }

  fun expectQuantiles(
    digest: SlidingWindowDigest<FakeDigest>,
    count: Long,
    sum: Double,
    quantileVals: SortedMap<Double, Double>
  ) {
    val snapshot = digest.snapshot(quantileVals.keys.toList()) //should this be keys or values?
    assertThat(snapshot.count).isEqualTo(count)
    assertThat(snapshot.sum).isEqualTo(sum)
    assertThat(snapshot.quantileVals.toDoubleArray()).isEqualTo(quantileVals.values.toDoubleArray())
  }
}