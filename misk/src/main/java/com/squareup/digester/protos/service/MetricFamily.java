// Code generated by Wire protocol buffer compiler, do not edit.
// Source file: service.proto
package com.squareup.digester.protos.service;

import com.squareup.wire.FieldEncoding;
import com.squareup.wire.Message;
import com.squareup.wire.ProtoAdapter;
import com.squareup.wire.ProtoReader;
import com.squareup.wire.ProtoWriter;
import com.squareup.wire.WireField;
import com.squareup.wire.internal.Internal;
import java.io.IOException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.util.List;
import java.util.Map;
import okio.ByteString;

/**
 * A group of digest metrics all having the same name and label names.
 */
public final class MetricFamily extends Message<MetricFamily, MetricFamily.Builder> {
  public static final ProtoAdapter<MetricFamily> ADAPTER = new ProtoAdapter_MetricFamily();

  private static final long serialVersionUID = 0L;

  /**
   * Descriptor shared by each metric in this family.
   */
  @WireField(
      tag = 1,
      adapter = "com.squareup.digester.protos.service.MetricFamily$MetricDescriptor#ADAPTER"
  )
  public final MetricDescriptor metric_descriptor;

  /**
   * Metrics in the family, each having a unique set of labels.
   */
  @WireField(
      tag = 2,
      adapter = "com.squareup.digester.protos.service.MetricFamily$Metric#ADAPTER",
      label = WireField.Label.REPEATED
  )
  public final List<Metric> metrics;

  public MetricFamily(MetricDescriptor metric_descriptor, List<Metric> metrics) {
    this(metric_descriptor, metrics, ByteString.EMPTY);
  }

  public MetricFamily(MetricDescriptor metric_descriptor, List<Metric> metrics,
      ByteString unknownFields) {
    super(ADAPTER, unknownFields);
    this.metric_descriptor = metric_descriptor;
    this.metrics = Internal.immutableCopyOf("metrics", metrics);
  }

  @Override
  public Builder newBuilder() {
    Builder builder = new Builder();
    builder.metric_descriptor = metric_descriptor;
    builder.metrics = Internal.copyOf("metrics", metrics);
    builder.addUnknownFields(unknownFields());
    return builder;
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) return true;
    if (!(other instanceof MetricFamily)) return false;
    MetricFamily o = (MetricFamily) other;
    return unknownFields().equals(o.unknownFields())
        && Internal.equals(metric_descriptor, o.metric_descriptor)
        && metrics.equals(o.metrics);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode;
    if (result == 0) {
      result = unknownFields().hashCode();
      result = result * 37 + (metric_descriptor != null ? metric_descriptor.hashCode() : 0);
      result = result * 37 + metrics.hashCode();
      super.hashCode = result;
    }
    return result;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    if (metric_descriptor != null) builder.append(", metric_descriptor=").append(metric_descriptor);
    if (!metrics.isEmpty()) builder.append(", metrics=").append(metrics);
    return builder.replace(0, 2, "MetricFamily{").append('}').toString();
  }

  public static final class Builder extends Message.Builder<MetricFamily, Builder> {
    public MetricDescriptor metric_descriptor;

    public List<Metric> metrics;

    public Builder() {
      metrics = Internal.newMutableList();
    }

    /**
     * Descriptor shared by each metric in this family.
     */
    public Builder metric_descriptor(MetricDescriptor metric_descriptor) {
      this.metric_descriptor = metric_descriptor;
      return this;
    }

    /**
     * Metrics in the family, each having a unique set of labels.
     */
    public Builder metrics(List<Metric> metrics) {
      Internal.checkElementsNotNull(metrics);
      this.metrics = metrics;
      return this;
    }

    @Override
    public MetricFamily build() {
      return new MetricFamily(metric_descriptor, metrics, super.buildUnknownFields());
    }
  }

  /**
   * MetricDescriptor describes a named metric.
   */
  public static final class MetricDescriptor extends Message<MetricDescriptor, MetricDescriptor.Builder> {
    public static final ProtoAdapter<MetricDescriptor> ADAPTER = new ProtoAdapter_MetricDescriptor();

    private static final long serialVersionUID = 0L;

    public static final String DEFAULT_NAME = "";

    public static final String DEFAULT_HELP = "";

    /**
     * Name of the metric.
     */
    @WireField(
        tag = 1,
        adapter = "com.squareup.wire.ProtoAdapter#STRING"
    )
    public final String name;

    /**
     * User-friendly description of the metric.
     */
    @WireField(
        tag = 2,
        adapter = "com.squareup.wire.ProtoAdapter#STRING"
    )
    public final String help;

    public MetricDescriptor(String name, String help) {
      this(name, help, ByteString.EMPTY);
    }

    public MetricDescriptor(String name, String help, ByteString unknownFields) {
      super(ADAPTER, unknownFields);
      this.name = name;
      this.help = help;
    }

    @Override
    public Builder newBuilder() {
      Builder builder = new Builder();
      builder.name = name;
      builder.help = help;
      builder.addUnknownFields(unknownFields());
      return builder;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof MetricDescriptor)) return false;
      MetricDescriptor o = (MetricDescriptor) other;
      return unknownFields().equals(o.unknownFields())
          && Internal.equals(name, o.name)
          && Internal.equals(help, o.help);
    }

    @Override
    public int hashCode() {
      int result = super.hashCode;
      if (result == 0) {
        result = unknownFields().hashCode();
        result = result * 37 + (name != null ? name.hashCode() : 0);
        result = result * 37 + (help != null ? help.hashCode() : 0);
        super.hashCode = result;
      }
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      if (name != null) builder.append(", name=").append(name);
      if (help != null) builder.append(", help=").append(help);
      return builder.replace(0, 2, "MetricDescriptor{").append('}').toString();
    }

    public static final class Builder extends Message.Builder<MetricDescriptor, Builder> {
      public String name;

      public String help;

      public Builder() {
      }

      /**
       * Name of the metric.
       */
      public Builder name(String name) {
        this.name = name;
        return this;
      }

      /**
       * User-friendly description of the metric.
       */
      public Builder help(String help) {
        this.help = help;
        return this;
      }

      @Override
      public MetricDescriptor build() {
        return new MetricDescriptor(name, help, super.buildUnknownFields());
      }
    }

    private static final class ProtoAdapter_MetricDescriptor extends ProtoAdapter<MetricDescriptor> {
      public ProtoAdapter_MetricDescriptor() {
        super(FieldEncoding.LENGTH_DELIMITED, MetricDescriptor.class);
      }

      @Override
      public int encodedSize(MetricDescriptor value) {
        return ProtoAdapter.STRING.encodedSizeWithTag(1, value.name)
            + ProtoAdapter.STRING.encodedSizeWithTag(2, value.help)
            + value.unknownFields().size();
      }

      @Override
      public void encode(ProtoWriter writer, MetricDescriptor value) throws IOException {
        ProtoAdapter.STRING.encodeWithTag(writer, 1, value.name);
        ProtoAdapter.STRING.encodeWithTag(writer, 2, value.help);
        writer.writeBytes(value.unknownFields());
      }

      @Override
      public MetricDescriptor decode(ProtoReader reader) throws IOException {
        Builder builder = new Builder();
        long token = reader.beginMessage();
        for (int tag; (tag = reader.nextTag()) != -1;) {
          switch (tag) {
            case 1: builder.name(ProtoAdapter.STRING.decode(reader)); break;
            case 2: builder.help(ProtoAdapter.STRING.decode(reader)); break;
            default: {
              FieldEncoding fieldEncoding = reader.peekFieldEncoding();
              Object value = fieldEncoding.rawProtoAdapter().decode(reader);
              builder.addUnknownField(tag, fieldEncoding, value);
            }
          }
        }
        reader.endMessage(token);
        return builder.build();
      }

      @Override
      public MetricDescriptor redact(MetricDescriptor value) {
        Builder builder = value.newBuilder();
        builder.clearUnknownFields();
        return builder.build();
      }
    }
  }

  /**
   * Digest holds a t-digest of data points observed in a specific time range.
   */
  public static final class Digest extends Message<Digest, Digest.Builder> {
    public static final ProtoAdapter<Digest> ADAPTER = new ProtoAdapter_Digest();

    private static final long serialVersionUID = 0L;

    public static final Long DEFAULT_START_AT_MS = 0L;

    public static final Long DEFAULT_END_AT_MS = 0L;

    public static final Long DEFAULT_STAGGER = 0L;

    /**
     * Start time window of data points, inclusive.
     */
    @WireField(
        tag = 1,
        adapter = "com.squareup.wire.ProtoAdapter#INT64"
    )
    public final Long start_at_ms;

    /**
     * End time window of data points, exclusive.
     */
    @WireField(
        tag = 2,
        adapter = "com.squareup.wire.ProtoAdapter#INT64"
    )
    public final Long end_at_ms;

    /**
     * Number of overlapping windows.
     * This is not directly related to the data, but is used by the collector to validate
     * that registry and server configurations are compatible.
     */
    @WireField(
        tag = 3,
        adapter = "com.squareup.wire.ProtoAdapter#INT64"
    )
    public final Long stagger;

    /**
     * t-digest of observed values.
     * The registry and server must agree on the format of the digest.
     */
    @WireField(
        tag = 4,
        adapter = "com.squareup.digester.protos.service.DigestData#ADAPTER"
    )
    public final DigestData digest_data;

    public Digest(Long start_at_ms, Long end_at_ms, Long stagger, DigestData digest_data) {
      this(start_at_ms, end_at_ms, stagger, digest_data, ByteString.EMPTY);
    }

    public Digest(Long start_at_ms, Long end_at_ms, Long stagger, DigestData digest_data,
        ByteString unknownFields) {
      super(ADAPTER, unknownFields);
      this.start_at_ms = start_at_ms;
      this.end_at_ms = end_at_ms;
      this.stagger = stagger;
      this.digest_data = digest_data;
    }

    @Override
    public Builder newBuilder() {
      Builder builder = new Builder();
      builder.start_at_ms = start_at_ms;
      builder.end_at_ms = end_at_ms;
      builder.stagger = stagger;
      builder.digest_data = digest_data;
      builder.addUnknownFields(unknownFields());
      return builder;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Digest)) return false;
      Digest o = (Digest) other;
      return unknownFields().equals(o.unknownFields())
          && Internal.equals(start_at_ms, o.start_at_ms)
          && Internal.equals(end_at_ms, o.end_at_ms)
          && Internal.equals(stagger, o.stagger)
          && Internal.equals(digest_data, o.digest_data);
    }

    @Override
    public int hashCode() {
      int result = super.hashCode;
      if (result == 0) {
        result = unknownFields().hashCode();
        result = result * 37 + (start_at_ms != null ? start_at_ms.hashCode() : 0);
        result = result * 37 + (end_at_ms != null ? end_at_ms.hashCode() : 0);
        result = result * 37 + (stagger != null ? stagger.hashCode() : 0);
        result = result * 37 + (digest_data != null ? digest_data.hashCode() : 0);
        super.hashCode = result;
      }
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      if (start_at_ms != null) builder.append(", start_at_ms=").append(start_at_ms);
      if (end_at_ms != null) builder.append(", end_at_ms=").append(end_at_ms);
      if (stagger != null) builder.append(", stagger=").append(stagger);
      if (digest_data != null) builder.append(", digest_data=").append(digest_data);
      return builder.replace(0, 2, "Digest{").append('}').toString();
    }

    public static final class Builder extends Message.Builder<Digest, Builder> {
      public Long start_at_ms;

      public Long end_at_ms;

      public Long stagger;

      public DigestData digest_data;

      public Builder() {
      }

      /**
       * Start time window of data points, inclusive.
       */
      public Builder start_at_ms(Long start_at_ms) {
        this.start_at_ms = start_at_ms;
        return this;
      }

      /**
       * End time window of data points, exclusive.
       */
      public Builder end_at_ms(Long end_at_ms) {
        this.end_at_ms = end_at_ms;
        return this;
      }

      /**
       * Number of overlapping windows.
       * This is not directly related to the data, but is used by the collector to validate
       * that registry and server configurations are compatible.
       */
      public Builder stagger(Long stagger) {
        this.stagger = stagger;
        return this;
      }

      /**
       * t-digest of observed values.
       * The registry and server must agree on the format of the digest.
       */
      public Builder digest_data(DigestData digest_data) {
        this.digest_data = digest_data;
        return this;
      }

      @Override
      public Digest build() {
        return new Digest(start_at_ms, end_at_ms, stagger, digest_data, super.buildUnknownFields());
      }
    }

    private static final class ProtoAdapter_Digest extends ProtoAdapter<Digest> {
      public ProtoAdapter_Digest() {
        super(FieldEncoding.LENGTH_DELIMITED, Digest.class);
      }

      @Override
      public int encodedSize(Digest value) {
        return ProtoAdapter.INT64.encodedSizeWithTag(1, value.start_at_ms)
            + ProtoAdapter.INT64.encodedSizeWithTag(2, value.end_at_ms)
            + ProtoAdapter.INT64.encodedSizeWithTag(3, value.stagger)
            + DigestData.ADAPTER.encodedSizeWithTag(4, value.digest_data)
            + value.unknownFields().size();
      }

      @Override
      public void encode(ProtoWriter writer, Digest value) throws IOException {
        ProtoAdapter.INT64.encodeWithTag(writer, 1, value.start_at_ms);
        ProtoAdapter.INT64.encodeWithTag(writer, 2, value.end_at_ms);
        ProtoAdapter.INT64.encodeWithTag(writer, 3, value.stagger);
        DigestData.ADAPTER.encodeWithTag(writer, 4, value.digest_data);
        writer.writeBytes(value.unknownFields());
      }

      @Override
      public Digest decode(ProtoReader reader) throws IOException {
        Builder builder = new Builder();
        long token = reader.beginMessage();
        for (int tag; (tag = reader.nextTag()) != -1;) {
          switch (tag) {
            case 1: builder.start_at_ms(ProtoAdapter.INT64.decode(reader)); break;
            case 2: builder.end_at_ms(ProtoAdapter.INT64.decode(reader)); break;
            case 3: builder.stagger(ProtoAdapter.INT64.decode(reader)); break;
            case 4: builder.digest_data(DigestData.ADAPTER.decode(reader)); break;
            default: {
              FieldEncoding fieldEncoding = reader.peekFieldEncoding();
              Object value = fieldEncoding.rawProtoAdapter().decode(reader);
              builder.addUnknownField(tag, fieldEncoding, value);
            }
          }
        }
        reader.endMessage(token);
        return builder.build();
      }

      @Override
      public Digest redact(Digest value) {
        Builder builder = value.newBuilder();
        if (builder.digest_data != null) builder.digest_data = DigestData.ADAPTER.redact(builder.digest_data);
        builder.clearUnknownFields();
        return builder.build();
      }
    }
  }

  public static final class Metric extends Message<Metric, Metric.Builder> {
    public static final ProtoAdapter<Metric> ADAPTER = new ProtoAdapter_Metric();

    private static final long serialVersionUID = 0L;

    /**
     * Label values. There will be one value corresponding to each entry in the MetricDescriptor's label_names.
     */
    @WireField(
        tag = 1,
        keyAdapter = "com.squareup.wire.ProtoAdapter#STRING",
        adapter = "com.squareup.wire.ProtoAdapter#STRING"
    )
    public final Map<String, String> labels;

    /**
     * t-digests for the metric, each for a different window of time.
     */
    @WireField(
        tag = 2,
        adapter = "com.squareup.digester.protos.service.MetricFamily$Digest#ADAPTER",
        label = WireField.Label.REPEATED
    )
    public final List<Digest> digests;

    public Metric(Map<String, String> labels, List<Digest> digests) {
      this(labels, digests, ByteString.EMPTY);
    }

    public Metric(Map<String, String> labels, List<Digest> digests, ByteString unknownFields) {
      super(ADAPTER, unknownFields);
      this.labels = Internal.immutableCopyOf("labels", labels);
      this.digests = Internal.immutableCopyOf("digests", digests);
    }

    @Override
    public Builder newBuilder() {
      Builder builder = new Builder();
      builder.labels = Internal.copyOf("labels", labels);
      builder.digests = Internal.copyOf("digests", digests);
      builder.addUnknownFields(unknownFields());
      return builder;
    }

    @Override
    public boolean equals(Object other) {
      if (other == this) return true;
      if (!(other instanceof Metric)) return false;
      Metric o = (Metric) other;
      return unknownFields().equals(o.unknownFields())
          && labels.equals(o.labels)
          && digests.equals(o.digests);
    }

    @Override
    public int hashCode() {
      int result = super.hashCode;
      if (result == 0) {
        result = unknownFields().hashCode();
        result = result * 37 + labels.hashCode();
        result = result * 37 + digests.hashCode();
        super.hashCode = result;
      }
      return result;
    }

    @Override
    public String toString() {
      StringBuilder builder = new StringBuilder();
      if (!labels.isEmpty()) builder.append(", labels=").append(labels);
      if (!digests.isEmpty()) builder.append(", digests=").append(digests);
      return builder.replace(0, 2, "Metric{").append('}').toString();
    }

    public static final class Builder extends Message.Builder<Metric, Builder> {
      public Map<String, String> labels;

      public List<Digest> digests;

      public Builder() {
        labels = Internal.newMutableMap();
        digests = Internal.newMutableList();
      }

      /**
       * Label values. There will be one value corresponding to each entry in the MetricDescriptor's label_names.
       */
      public Builder labels(Map<String, String> labels) {
        Internal.checkElementsNotNull(labels);
        this.labels = labels;
        return this;
      }

      /**
       * t-digests for the metric, each for a different window of time.
       */
      public Builder digests(List<Digest> digests) {
        Internal.checkElementsNotNull(digests);
        this.digests = digests;
        return this;
      }

      @Override
      public Metric build() {
        return new Metric(labels, digests, super.buildUnknownFields());
      }
    }

    private static final class ProtoAdapter_Metric extends ProtoAdapter<Metric> {
      private final ProtoAdapter<Map<String, String>> labels = ProtoAdapter.newMapAdapter(ProtoAdapter.STRING, ProtoAdapter.STRING);

      public ProtoAdapter_Metric() {
        super(FieldEncoding.LENGTH_DELIMITED, Metric.class);
      }

      @Override
      public int encodedSize(Metric value) {
        return labels.encodedSizeWithTag(1, value.labels)
            + Digest.ADAPTER.asRepeated().encodedSizeWithTag(2, value.digests)
            + value.unknownFields().size();
      }

      @Override
      public void encode(ProtoWriter writer, Metric value) throws IOException {
        labels.encodeWithTag(writer, 1, value.labels);
        Digest.ADAPTER.asRepeated().encodeWithTag(writer, 2, value.digests);
        writer.writeBytes(value.unknownFields());
      }

      @Override
      public Metric decode(ProtoReader reader) throws IOException {
        Builder builder = new Builder();
        long token = reader.beginMessage();
        for (int tag; (tag = reader.nextTag()) != -1;) {
          switch (tag) {
            case 1: builder.labels.putAll(labels.decode(reader)); break;
            case 2: builder.digests.add(Digest.ADAPTER.decode(reader)); break;
            default: {
              FieldEncoding fieldEncoding = reader.peekFieldEncoding();
              Object value = fieldEncoding.rawProtoAdapter().decode(reader);
              builder.addUnknownField(tag, fieldEncoding, value);
            }
          }
        }
        reader.endMessage(token);
        return builder.build();
      }

      @Override
      public Metric redact(Metric value) {
        Builder builder = value.newBuilder();
        Internal.redactElements(builder.digests, Digest.ADAPTER);
        builder.clearUnknownFields();
        return builder.build();
      }
    }
  }

  private static final class ProtoAdapter_MetricFamily extends ProtoAdapter<MetricFamily> {
    public ProtoAdapter_MetricFamily() {
      super(FieldEncoding.LENGTH_DELIMITED, MetricFamily.class);
    }

    @Override
    public int encodedSize(MetricFamily value) {
      return MetricDescriptor.ADAPTER.encodedSizeWithTag(1, value.metric_descriptor)
          + Metric.ADAPTER.asRepeated().encodedSizeWithTag(2, value.metrics)
          + value.unknownFields().size();
    }

    @Override
    public void encode(ProtoWriter writer, MetricFamily value) throws IOException {
      MetricDescriptor.ADAPTER.encodeWithTag(writer, 1, value.metric_descriptor);
      Metric.ADAPTER.asRepeated().encodeWithTag(writer, 2, value.metrics);
      writer.writeBytes(value.unknownFields());
    }

    @Override
    public MetricFamily decode(ProtoReader reader) throws IOException {
      Builder builder = new Builder();
      long token = reader.beginMessage();
      for (int tag; (tag = reader.nextTag()) != -1;) {
        switch (tag) {
          case 1: builder.metric_descriptor(MetricDescriptor.ADAPTER.decode(reader)); break;
          case 2: builder.metrics.add(Metric.ADAPTER.decode(reader)); break;
          default: {
            FieldEncoding fieldEncoding = reader.peekFieldEncoding();
            Object value = fieldEncoding.rawProtoAdapter().decode(reader);
            builder.addUnknownField(tag, fieldEncoding, value);
          }
        }
      }
      reader.endMessage(token);
      return builder.build();
    }

    @Override
    public MetricFamily redact(MetricFamily value) {
      Builder builder = value.newBuilder();
      if (builder.metric_descriptor != null) builder.metric_descriptor = MetricDescriptor.ADAPTER.redact(builder.metric_descriptor);
      Internal.redactElements(builder.metrics, Metric.ADAPTER);
      builder.clearUnknownFields();
      return builder.build();
    }
  }
}
